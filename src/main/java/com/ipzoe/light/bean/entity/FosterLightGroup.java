package com.ipzoe.light.bean.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by cxs on 2017/4/10.
 */
@ApiModel(description = "培养灯组关系表", value = "FosterLightGroup")
public class FosterLightGroup extends BaseEntity{

    @ApiModelProperty("培养ID")
    private Long FosterId;

    @ApiModelProperty("灯组ID")
    private Long LightGroupId;


    public Long getFosterId() {
        return FosterId;
    }

    public void setFosterId(Long fosterId) {
        FosterId = fosterId;
    }

    public Long getLightGroupId() {
        return LightGroupId;
    }

    public void setLightGroupId(Long lightGroupId) {
        LightGroupId = lightGroupId;
    }
}
