package com.ipzoe.light.bean.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ipzoe.light.bean.response.mqttResponse.Brightness;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.time.LocalTime;

/**
 * Created by cxs on 2017/3/23.
 */
@ApiModel(value = "StageTime", description = "阶段表")
@Entity
@Table(name = "stage_time")
public class StageTime extends BaseEntity{

    @ApiModelProperty("阶段表ID")
    private Long stageId;

    @ApiModelProperty("开始时间")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @ApiModelProperty("结束时间")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;

    public Long getStageId() {
        return stageId;
    }

    public void setStageId(Long stageId) {
        this.stageId = stageId;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
