package com.ipzoe.light.bean.response.mqttResponse;

/**
 * 封装返回值（后期需求更改，返回值数据结构尚未更改） *
 * Created by cxs on 2017/4/7.
 */
public class MqttResponse {

    // 1001 app推送； 2001 灯具推送
    private Integer cmd;

    private Data data;

    public Integer getCmd() {
        return cmd;
    }

    public void setCmd(Integer cmd) {
        this.cmd = cmd;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
