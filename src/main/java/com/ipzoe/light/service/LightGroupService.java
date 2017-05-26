package com.ipzoe.light.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ipzoe.light.bean.entity.*;
import com.ipzoe.light.bean.request.LightGroupRequest;
import com.ipzoe.light.bean.response.Code;
import com.ipzoe.light.bean.response.Response;
import com.ipzoe.light.bean.response.mqttResponse.Brightness;
import com.ipzoe.light.bean.response.mqttResponse.Data;
import com.ipzoe.light.bean.response.mqttResponse.MqttResponse;
import com.ipzoe.light.bean.response.mqttResponse.SetTimeObj;
import com.ipzoe.light.component.mqtt.Mqtt;
import com.ipzoe.light.repository.FosterLightGroupRepository;
import com.ipzoe.light.repository.LightGroupRepository;
import com.ipzoe.light.repository.LightRepository;
import com.ipzoe.light.repository.StageTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wzx on 2017/3/24.
 */
@Service
@Transactional
public class LightGroupService {

    @Autowired
    private LightGroupRepository lightGroupRepository;

    @Autowired
    private StageTimeRepository stageTimeRepository;

    @Autowired
    private Mqtt mqtt;

    @Autowired
    private LightRepository lightRepository;

    @Autowired
    private FosterLightGroupRepository fosterLightGroupRepository;

    /**
     * 新增灯组
     *
     * @return
     */
    public Response insert(LightGroupRequest lightGroupRequest, Account account) {
        String name = lightGroupRequest.getName();
        List<Long> lightIdList = lightGroupRequest.getLightIdList();

        //灯组名称重复校验
        LightGroup lightGroup = lightGroupRepository.findByGroupName(name, account.getId());
        if (lightGroup != null) {
            return Response.ok(Code.API_LIGHT_GROUP_ALREADY_EXIST);
        }
        // 校验灯具是否在其他灯组
        for (Long id : lightIdList) {
            if (lightRepository.selectByPrimaryKey(id).getGroupId() != null) {
                return Response.ok(Code.API_LIGHT_ALREADY_USE);
            }
        }


        lightGroup = new LightGroup();
        lightGroup.setName(name);
        lightGroup.setAccount_id(account.getId());
        // 插入灯组
        lightGroupRepository.insertSelective(lightGroup);
        // 更新灯具中所属灯组ID
        for (Long id : lightIdList) {
            lightRepository.updateGroupIdById(id,lightGroup.getId());
        }

        return Response.ok();
    }

    /**
     * 修改灯组
     *
     * @param account
     * @return
     */
    public Response update(LightGroupRequest lightGroupRequest, Account account) {

        //灯组启用校验
        LightGroup lightGroup = lightGroupRepository.selectStatus(lightGroupRequest.getId());
        if (lightGroup == null || lightGroup.getStatus() == LightGroup.ON || checkLightGroupStatus(lightGroup.getId())) {
            return Response.ok(Code.API_LIGHT_GROUP_ALREADY_USE);
        }
        //灯组名称重复校验
        if (!lightGroup.getName().equals(lightGroupRequest.getName())) {
            LightGroup lightGroupDb = lightGroupRepository.findByGroupName(lightGroupRequest.getName(), account.getId());
            if (lightGroupDb != null) {
                return Response.ok(Code.API_LIGHT_GROUP_ALREADY_EXIST);
            }
        }

        // 校验灯具ID是否更改
        List<Long> lightIdList = lightRepository.selectLightIdByLightGroupId(lightGroupRequest.getId());
        boolean flag = false;
        label:
        for (Long idOld : lightIdList) {
            for (Long idNew : lightGroupRequest.getLightIdList()) {
                if (!idNew.equals(idOld)) {
                    flag = true;
                    break label;
                }
            }
        }
        if (flag) {
            // 将此灯组ID下的灯具所属灯组ID设置为null
            lightRepository.updateIsDeletedByGroupId(lightGroupRequest.getId());
            for (Long id : lightGroupRequest.getLightIdList()) {
                //  插入新的灯组ID：更新灯具
                lightRepository.updateLightGroupId(id, lightGroupRequest.getId());
            }
        }
        lightGroup.setName(lightGroupRequest.getName());
        // 更新灯组
        lightGroupRepository.updateByPrimaryKey(lightGroup);
        return Response.ok();
    }

    /**
     * 删除灯组
     *
     * @param lightGroupId
     * @return
     */
    public Response delete(Long lightGroupId) {

        //灯组启用校验
        LightGroup lightGroup = lightGroupRepository.selectStatus(lightGroupId);
        if (lightGroup == null || lightGroup.getStatus() == LightGroup.ON || checkLightGroupStatus(lightGroupId)) {
            return Response.ok(Code.API_LIGHT_GROUP_ALREADY_USE);
        }
        // 删除灯组
        lightGroupRepository.updateIsDeleted(lightGroupId);
        // 删除灯具
        lightGroupRepository.updateLightIsNull(lightGroupId);
        return Response.ok();
    }

    /**
     * 检查灯组是否在培养中：即是否空闲，空闲返回false
     *
     * @param lightGroupId
     * @return
     */
    private boolean checkLightGroupStatus(Long lightGroupId) {
        List<FosterLightGroup> fosterLightGroupList = fosterLightGroupRepository.selectByLightGroupId(lightGroupId);

        if (fosterLightGroupList.isEmpty()) {
            // 灯组空闲中
            return false;
        }
        return true;
    }


    /**
     * 空闲灯组查询
     *
     * @param account
     * @return
     */
    public Response query(Account account) {
        // 获取灯光关闭的灯组
        List<LightGroup> list = lightGroupRepository.selectLightGroupByAccountId(account.getId());
        List<LightGroup> lightGroupNew = new ArrayList<>();
        // 筛选是否为空闲灯组。
        for (LightGroup lightGroup : list) {
            if (fosterLightGroupRepository.selectByLightGroupId(lightGroup.getId()).isEmpty()) {
                lightGroupNew.add(lightGroup);
            }
        }
        return Response.ok(lightGroupNew);
    }

    /**
     * 设置灯组状态
     *
     * @param account
     * @param groupId
     * @param status
     * @return
     */
    public Response setStatus(Account account, Long groupId, Integer status) {

        LightGroup lightGroup;

        //启用时 判断是否已配置光配方
        if (status == 1) {
            lightGroup = lightGroupRepository.findFormula(groupId);

            if (lightGroup == null)
                return Response.ok(Code.API_LIGHT_GROUP_FORMULA_NOT_EXIST);
        }

        lightGroupRepository.setStatus(status, groupId);
        return Response.ok();
    }


    /**
     * 根据灯组ID开灯/关灯
     *
     * @param lightGroupId 灯组ID
     * @param status       是否开关 1开 0关
     * @return
     */
    public Response setOnOff(Long lightGroupId, Integer status, Integer isValid) {
        // // TODO: 2017/4/10 尚未设置定时 
        List<FormulaStage> formulaStageList = lightGroupRepository.selectFormulaStageStageById(lightGroupId);
        // 判断灯组是否拥有光配方
        if (formulaStageList.isEmpty()) {
            return Response.ok(Code.API_FORMULA_NOT_EXIST);
        }
        FormulaStage formulaStage = formulaStageList.get(0);
        // 设置亮度
        Brightness brightness = new Brightness();
        brightness.setRed(formulaStage.getRed());
        brightness.setGreen(formulaStage.getGreen());
        brightness.setBlue(formulaStage.getBlue());
        // 获取定时集合
        List<StageTime> stageTimeList = stageTimeRepository.selectStageTimeByLightGroupId(lightGroupId);
        // 生成json
        String comment = null;
        // 开灯
        if (status == Light.OPEN_ONOFF) {
            // 定时有效
            if (isValid == Light.VALID_ON_OFFSTATE_TIME) {
                comment = generateJson(Light.PUBLISH_CMD, Light.OPEN_ONOFF, brightness, Light.VALID_ON_OFFSTATE_TIME, stageTimeList);
                // 定时无效
            } else if (isValid == Light.NOT_VALID_ON_OFFSTATE_TIME) {
                comment = generateJson(Light.PUBLISH_CMD, Light.OPEN_ONOFF, brightness, Light.NOT_VALID_ON_OFFSTATE_TIME, stageTimeList);
            }
            // 关灯
        } else if (status == Light.SHUT_ONOFF) {
            comment = generateJson(Light.PUBLISH_CMD, Light.SHUT_ONOFF, brightness, Light.VALID_ON_OFFSTATE_TIME, stageTimeList);
        } else {
            Response.ok(Code.API_INPUT_FAILED);
        }
        // 循环发布到以终端接收的主题
        List<String> subscribeList = lightGroupRepository.selectSubscribeByLightGroupId(lightGroupId);
        for (String subscribe : subscribeList) {
            mqtt.publish(subscribe, comment);
        }
        return Response.ok();
    }


    /**
     * 根据给定的信息生成json
     *
     * @param cmd           指令  1001-> 发送; 2001-> 接收
     * @param onOff         0—代表关灯，1-代表开灯
     * @param brightnessOut 设置亮度
     * @param onOffSetTime  0—定时有效，1-代表定时无效
     * @param stageTimeList 包含开始和结束时间段，亮度。
     * @return 约定的json格式
     */
    private String generateJson(Integer cmd,
                                Integer onOff,
                                Brightness brightnessOut,
                                Integer onOffSetTime,
                                List<StageTime> stageTimeList) {
        MqttResponse mqttResponse = new MqttResponse();
        Data data = new Data();
        List<SetTimeObj> setTimeObjList = new ArrayList<>();

        // 逐个添加setTimeobj数据
        stageTimeList.forEach(stageTime -> {
            SetTimeObj setTimeObj = new SetTimeObj();
            setTimeObj.setStartTime(stageTime.getStartTime().getHour() * 60 + stageTime.getStartTime().getMinute());
            setTimeObj.setEndTime(stageTime.getEndTime().getHour() * 60 + stageTime.getEndTime().getMinute());
            setTimeObjList.add(setTimeObj);
        });
        // 设置是否开灯 0代表关灯 1代表开灯
        data.setOnOff(onOff);
        // 设置定时是否有效，0代表定时有效，1代表定时无效
        data.setOnOffSetTime(onOffSetTime);
        // 设置亮度
        data.setBrightness(brightnessOut);
        data.setSetTimeObj(setTimeObjList);
        // 设置指令 1001即发送
        mqttResponse.setCmd(cmd);
        // 设置所有数据
        mqttResponse.setData(data);
        try {
            return new ObjectMapper().writeValueAsString(mqttResponse);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "error 生成json错误";
        }
    }


    /**
     * 根据监听器传来的主题更新数据库
     *
     * @param onOff
     * @param topic
     * @return
     */
    public void updateStatusByMqtt(Integer onOff, String topic) {
        lightGroupRepository.updateStatusByMqtt(onOff, topic);
    }


    /**
     * 获取打开灯组／获取关闭灯组
     *
     * @param account
     * @param type
     * @return
     */
    public Response selectLightGroup(Account account, int type) {
        if (type != 1 && type != 2) {
            return Response.ok(Code.API_IN_PUT_FAILED);
        }
        return new Response<>(lightGroupRepository.selectOnOffByAccountId(account.getId(), type));
    }
}
