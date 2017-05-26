package com.ipzoe.light.bean.response.mqttResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * 封装返回值（后期需求更改，返回值数据结构尚未更改）
 * Created by cxs on 2017/4/7.
 */
public class Data {

    // (0—代表关灯，1-代表开灯 整型数据)
    private Integer onOff;

    // 亮度
    private Brightness brightness;

    // (0—定时有效，1-代表定时无效  整型数据)
    @JsonProperty("onOff_setTime")
    private Integer onOffSetTime;

    // 定时时间数组
    @JsonProperty("setTimeobj")
    private List<SetTimeObj> setTimeObj;




    public Integer getOnOff() {
        return onOff;
    }

    public void setOnOff(Integer onOff) {
        this.onOff = onOff;
    }

    public Brightness getBrightness() {
        return brightness;
    }

    public void setBrightness(Brightness brightness) {
        this.brightness = brightness;
    }

    public Integer getOnOffSetTime() {
        return onOffSetTime;
    }

    public void setOnOffSetTime(Integer onOffSetTime) {
        this.onOffSetTime = onOffSetTime;
    }

    public List<SetTimeObj> getSetTimeObj() {
        return setTimeObj;
    }

    public void setSetTimeObj(List<SetTimeObj> setTimeObj) {
        this.setTimeObj = setTimeObj;
    }
}
