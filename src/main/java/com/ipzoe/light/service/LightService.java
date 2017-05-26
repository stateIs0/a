package com.ipzoe.light.service;

import com.ipzoe.light.bean.entity.Account;
import com.ipzoe.light.bean.entity.Light;
import com.ipzoe.light.bean.request.LightRequest;
import com.ipzoe.light.bean.response.Code;
import com.ipzoe.light.bean.response.Response;
import com.ipzoe.light.repository.LightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wzx on 2017/3/24.
 */
@Service
@Transactional
public class LightService {

    @Autowired
    private LightRepository lightRepository;

    /**
     * 新增灯具
     *
     * @param account,groupId,mac
     * @return
     */
    public Response insert(Account account, List<LightRequest> lightList) {
        for (LightRequest lightRequest : lightList) {
            Light light = new Light();
            light.setCode(lightRequest.getCode());
            light.setMac(lightRequest.getMac());
            light.setAccountId(account.getId());
            lightRepository.insertSelective(light);
        }
        return Response.ok();
    }


    /**
     * 更新灯具信息
     *
     * @param lightId
     * @return
     */
    public Response update(Long lightId, String code) {
        Light light = lightRepository.selectByPrimaryKey(lightId);
        light.setCode(code);
        lightRepository.updateByPrimaryKeySelective(light);
        return Response.ok();

    }

    /**
     * 删除灯具
     *
     * @param lightId
     * @return
     */
    public Response delete(Long lightId) {

        //校验灯具是否在灯组中
        Light lightInUse = lightRepository.findInUse(lightId);
        if (lightInUse != null)
            return Response.ok(Code.API_LIGHT_ALREADY_USE);
        lightRepository.deleteLight(lightId);
        return Response.ok();

    }


    /**
     * 获取指定灯组ID下的 + 空闲的灯具
     *
     * @param account
     * @param groupId 灯组ID：非必传 为空时获取空闲灯具
     * @return
     */
    public Response queryLight(Account account, Long groupId) {
        List<Light> platformList = lightRepository.selectLight(groupId, account.getId());
        // 如果是空闲的设置为true，否则为false
        platformList.forEach(list ->{
            if (list.getGroupId() == null) {
                list.setLeisure(true);
            } else {
                list.setLeisure(false);
            }
        });
        return Response.ok(platformList);
    }


    /**
     * 根据灯组ID获取灯组
     *
     * @param groupId
     * @return
     */
    public Response selectLight(Long groupId) {
        return new Response<>(lightRepository.selectByLightGroupId(groupId));
    }

    /**
     * 检测灯具
     *
     * @param account
     * @return
     */
    public Response detectionLight(Account account, Long lightId) {

        Light light = lightRepository.selectByPrimaryKey(lightId);
        // 校验灯具是否属于用户
        if (!account.getId().equals(light.getAccountId())) {
            return Response.ok(Code.API_LIGHT_NOT_OF_ACCOUNT);
        }
        //// TODO: 2017/4/12 开灯10秒
        return Response.ok();
    }

    /**
     * 根据type获取所有的灯具／空闲灯具
     *
     * @param account
     * @param type    0:所有，1:空闲
     * @return
     */
    public Response selectAllLight(Account account, Long type) {
        if (type == 0) {
            return new Response<>(lightRepository.selectByAccountId(account.getId()));
        }
        if (type == 1) {
            return new Response<>(lightRepository.selectLeisureByAccountId(account.getId()));
        }
        return Response.ok(Code.API_IN_PUT_FAILED);
    }
}
