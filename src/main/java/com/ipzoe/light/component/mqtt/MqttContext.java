package com.ipzoe.light.component.mqtt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 专门用于启动mqtt客户端监听线程
 * Created by cxs on 2017/4/1.
 */
@Component
public class MqttContext {

    @Autowired
    private Mqtt mqtt;

    // 此注解在spring生成bean的时候会立即执行此方法
    @PostConstruct
    public void mqtt() {
        // 启动监听程序 测试增删改查尚未启用
//        new Thread(() -> {
//            for (; ; ) {
//                mqtt.subscribe();
//            }
//        }).start();
    }
}
