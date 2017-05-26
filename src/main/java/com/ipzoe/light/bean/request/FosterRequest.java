package com.ipzoe.light.bean.request;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Created by cxs on 2017/4/11.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FosterRequest {

    // 培养名
    private String name;

    // 培养ID
    private Long fosterId;

    // 灯组ID集合
    private List<Long> lightGroupId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getFosterId() {
        return fosterId;
    }

    public void setFosterId(Long fosterId) {
        this.fosterId = fosterId;
    }

    public List<Long> getLightGroupId() {
        return lightGroupId;
    }

    public void setLightGroupId(List<Long> lightGroupId) {
        this.lightGroupId = lightGroupId;
    }
}
