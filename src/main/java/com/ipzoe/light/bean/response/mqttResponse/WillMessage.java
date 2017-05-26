package com.ipzoe.light.bean.response.mqttResponse;

import java.util.HashMap;

/**
 * 灯具遗嘱消息
 *
 * Created by cxs on 2017/4/7.
 */
public class WillMessage {

    private Integer cmd;

    private HashMap data;

    public Integer getCmd() {
        return cmd;
    }

    public void setCmd(Integer cmd) {
        this.cmd = cmd;
    }

    public HashMap getData() {
        return data;
    }

    public void setData(HashMap data) {
        this.data = data;
    }
}
