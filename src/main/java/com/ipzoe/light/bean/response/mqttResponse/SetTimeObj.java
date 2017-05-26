package com.ipzoe.light.bean.response.mqttResponse;

import io.swagger.annotations.ApiModelProperty;

/**
 * 约定的数据格式setTimeobj
 *  * 封装返回值（后期需求更改，返回值数据结构尚未更改）
 * Created by cxs on 2017/4/7.
 */
public class SetTimeObj {

    @ApiModelProperty("开始时间")
//    @JsonFormat(pattern = "HH:mm")
    private Integer startTime;

    @ApiModelProperty("结束时间")
//    @JsonFormat(pattern = "HH:mm")
    private Integer endTime;


//    public String getStartTime() {
//        return startTime;
//    }
//
//    public void setStartTime(LocalTime startTime) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
//        this.startTime = startTime.format(formatter);
//    }
//
//    public String getEndTime() {
//        return endTime;
//    }
//
//    public void setEndTime(LocalTime endTime) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
//        this.endTime = endTime.format(formatter);
//    }


    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }
}
