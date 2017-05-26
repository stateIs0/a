package com.ipzoe.light.bean.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;

/**
 * Created by cxs on 2017/4/10.
 */
@ApiModel(description = "培养表" ,value = "foster")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Foster extends BaseEntity {

    // 未开始
    public static final int NOT_START = 1;
    // 培养中
    public static final int FOSTERING = 2;
    // 已完成
    public static final int FINISH = 3;
    // 延续培养
    public static final int CONTINUE_FOSTER = 4;
    // 关闭
    public static final int OFF = 1;
    // 打开
    public static final int ON = 0;

    @ApiModelProperty("培养名")
    private String name;

    @ApiModelProperty("用户ID")
    private Long accountId;

    @ApiModelProperty("开始时间")
    private LocalDate startTime;

    @ApiModelProperty("结束时间")
    private LocalDate endTime;

    @ApiModelProperty("培养开关")
    private Integer onOff;

    @ApiModelProperty("培养状态1:未开始 2:培养中 3:完成 4:延续培养")
    private Integer status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Integer getOnOff() {
        return onOff;
    }

    public void setOnOff(Integer onOff) {
        this.onOff = onOff;
    }
}

