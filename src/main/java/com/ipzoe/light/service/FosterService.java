package com.ipzoe.light.service;

import com.ipzoe.light.bean.entity.Account;
import com.ipzoe.light.bean.entity.Foster;
import com.ipzoe.light.bean.entity.FosterLightGroup;
import com.ipzoe.light.bean.response.Code;
import com.ipzoe.light.bean.response.Response;
import com.ipzoe.light.repository.FosterLightGroupRepository;
import com.ipzoe.light.repository.FosterRepository;
import com.ipzoe.light.repository.LightGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by cxs on 2017/4/10.
 */
@Service
@Transactional
public class FosterService {

    @Autowired
    private FosterRepository fosterRepository;

    @Autowired
    private FosterLightGroupRepository fosterLightGroupRepository;

    @Autowired
    private LightGroupRepository lightGroupRepository;

    /**
     * 新增培养
     *
     * @param name
     * @param lightGroupIds
     * @return
     */
    public Response add(Account account, String name, List<Long> lightGroupIds) {
        // 校验名称是否重复
        for (Long id : lightGroupIds) {
            // 校验灯组是否存在
            if (lightGroupRepository.selectById(id) == null) {
                return Response.ok(Code.API_LIGHT_GROUP_NOT_EXIST);
            }
            // 校验灯组是否空闲
            if (!fosterLightGroupRepository.selectByLightGroupId(id).isEmpty()) {
                return Response.ok(Code.API_LIGHT_GROUP_USEING);
            }
        }
        // insert 培养表
        Foster fosterNew = new Foster();
        fosterNew.setName(name);
        fosterNew.setAccountId(account.getId());
        fosterRepository.insertSelective(fosterNew);
        // insert 培养关系表
        for (Long id : lightGroupIds) {
            FosterLightGroup fosterLightGroup = new FosterLightGroup();
            fosterLightGroup.setFosterId(fosterNew.getId());
            fosterLightGroup.setLightGroupId(id);
            fosterLightGroupRepository.insertSelective(fosterLightGroup);
        }
        return Response.ok();
    }

    /**
     * 更新培养
     *
     * @param account
     * @param name
     * @param lightGroupIds
     * @param fosterId
     * @return
     */
    public Response updateFoster(Account account, String name, List<Long> lightGroupIds, Long fosterId) {
        Foster fosterDb = fosterRepository.selectById(fosterId);
        // 清空原来的关系数据
        fosterLightGroupRepository.updateIsDeletedByFosterId(fosterDb.getId());
        fosterDb.setAccountId(account.getId());
        fosterDb.setName(name);
        fosterRepository.updateByPrimaryKey(fosterDb);
        // insert 培养关系表
        for (Long id : lightGroupIds) {
            FosterLightGroup fosterLightGroup = new FosterLightGroup();
            fosterLightGroup.setFosterId(fosterId);
            fosterLightGroup.setLightGroupId(id);
            fosterLightGroupRepository.insertSelective(fosterLightGroup);
        }
        return Response.ok();
    }

    /**
     * 删除培养
     *
     * @param id
     * @return
     */
    public Response deletedFoster(Long id) {
        Foster foster = fosterRepository.selectById(id);
        if (foster == null) {
            return Response.ok(Code.API_FOSTER_NOT_EXIST);
        }
        //状态为培养中和延续培养不可删除
        if (foster.getStatus().equals(Foster.FOSTERING)) {
            return Response.ok(Code.API_FOSTER_IS_FOSTING);
        }
        if (foster.getStatus().equals(Foster.CONTINUE_FOSTER)) {
            return Response.ok(Code.API_FOSTER_IS_CONTINUE);
        }

        fosterRepository.updateIsDeleted(id);
        fosterLightGroupRepository.updateIsDeletedByFosterId(foster.getId());

        return Response.ok();
    }

    /**
     * 根据用户查询所有的培养
     *
     * @param account
     * @return
     */
    public Response selectFoster(Account account, Long fosterId) {
        if (fosterId != null) {
            return new Response<>(fosterRepository.selectByPrimaryKey(fosterId));
        }
        return new Response<>(fosterRepository.selectAllByAccountId(account.getId()));

    }

    /**
     * 获取所有培养的状态
     *
     * @param account
     * @return
     */
    public Response getStatus(Account account) {
        if (account == null) {
            return Response.ok(Code.API_ACCOUNT_NOT_EXIST);
        }
        List<Integer> statusList = fosterRepository.selectStatusByAccountId(account.getId());
        return Response.ok(statusList);
    }

    /**
     * 一键开关所有的培养，含培养中和延续培养
     *
     * @param account
     * @param type     开／关，0：开，1：关
     * @param fosterId 培养ID，非必传
     * @return
     */
    public Response setAllOnOff(Account account, Integer type, Long fosterId) {
        // 开／关 ，排除已完成的培养
        if (fosterId == null) {
            List<Foster> fosterList = fosterRepository.selectByAccountId(account.getId());
            for (Foster foster : fosterList) {
                // 校验培养下有没有灯组，灯组下有没有配方,满足条件则开启，否则不做处理。
                checkFosterIsHaveLightGroup(foster);
            }
            return Response.ok();
        }

        Foster foster = fosterRepository.selectByPrimaryKey(fosterId);
        // 如果培养状态是未开始，则插入新的开始时间和结束时间
        checkFosterIsHaveLightGroup(foster);
        return Response.ok();
    }

    /**
     * 校验培养下是否有灯组，有则更新时间，无则不开灯不处理
     *
     * @param foster
     * @return
     */
    private void checkFosterIsHaveLightGroup(Foster foster) {
        if (foster.getStatus() == Foster.NOT_START) {
            List<Long> lightGroupIds = fosterLightGroupRepository.selectLightGroupIdByFosterId(foster.getId());
            // 如果培养有灯组
            if (!lightGroupIds.isEmpty()) {
                for (Long id : lightGroupIds) {
                    // 如果灯组下的formula_id 为null，则不做处理。
                    if (lightGroupRepository.selectByPrimaryKey(id).getFormulaId() == null) {
                        continue;
                    }
                    foster.setStartTime(LocalDate.now());
                    foster.setEndTime(getEndTime(lightGroupIds, LocalDate.now()));
                    foster.setOnOff(Foster.ON);
                    foster.setStatus(Foster.FOSTERING);
                    // 开启培养
                    fosterRepository.updateByPrimaryKeySelective(foster);
                    // 开启灯组
                    lightGroupRepository.updateStatusByFoster(id);
                }
            }
        }
    }


    /**
     * 获取该培养下最晚结束的灯组时间，以确定培养的结束时间。
     *
     * @param lightGroupIdList
     * @return
     */
    private LocalDate getEndTime(List<Long> lightGroupIdList, LocalDate startTime) {
        List<Integer> list = new ArrayList<>();
        for (Long id : lightGroupIdList) {
            list.add(lightGroupRepository.selectDaysSumById(id));
        }
        Collections.sort(list);
        Collections.reverse(list);
        // 查找最晚的时间
        return startTime.plusDays(list.get(0));
    }


    /**
     * 根据培养ID和开关状态查询灯组集合
     *
     * @param type    0：开   1：关
     * @param id
     * @return
     */
    public Response getLightGroup( Integer type, Long id) {
        return new Response<>(fosterLightGroupRepository.selectLightGroupByFosterIdAndOnOff(type, id));
    }

}
