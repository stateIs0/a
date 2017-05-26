package com.ipzoe.light.bean.request;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Created by cxs on 2017/4/12.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LightGroupRequest {

    // 灯组ID
    private Long id;

    // 灯组名
    private String name;

    //  灯组下所有灯具ID
    private List<Long> lightIdList;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getLightIdList() {
        return lightIdList;
    }

    public void setLightIdList(List<Long> lightIdList) {
        this.lightIdList = lightIdList;
    }
}
