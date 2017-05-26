package com.ipzoe.light.bean.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalTime;

/**
 * Created by cxs on 2017/4/10.
 */
@ApiModel(description = "与灯组绑定的手工定时表",value = "timing")
public class Timing extends BaseEntity {

    @ApiModelProperty("用户ID")
    private Long accountId;

    @ApiModelProperty("定时名")
    private String name;

    @ApiModelProperty("开始时间")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @ApiModelProperty("结束时间")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
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
