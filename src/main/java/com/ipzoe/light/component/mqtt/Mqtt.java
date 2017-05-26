package com.ipzoe.light.component.mqtt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ipzoe.light.bean.response.mqttResponse.MqttResponse;
import com.ipzoe.light.bean.response.mqttResponse.WillMessage;
import com.ipzoe.light.service.LightGroupService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fusesource.mqtt.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;

/**
 * MQTT 客户端
 * Created by cxs on 2017/4/1.
 */
@Component
public class Mqtt {
    private static final Log log = LogFactory.getLog(Mqtt.class);


    // mqtt broker的ip和端口
    private final static String CONNECTION_STRING = "tcp://182.254.129.70:1883";
    // 连接前清空会话信息
    private final static boolean CLEAN_START = true;
    // 低耗网络，但是又需要及时获取数据，心跳30s
    private final static short KEEP_ALIVE = 30;
    private final static String CLIENT_ID = "clientApi";
    // 只订阅来自己终端输出的消息主题
    private static Topic[] topics = {
            new Topic("$client/out/#", QoS.EXACTLY_ONCE)};
    // 重新连接的次数
    private final static long RECONNECTION_ATTEMPT_MAX = 6;
    // 重连的间隔时间
    private final static long RECONNECTION_DELAY = 2000;
    // 发送最大缓冲为2M
    private final static int SEND_BUFFER_SIZE = 2 * 1024 * 1024;
    // 创建MQTT对象
    private MQTT mqtt = new MQTT();
    // mqtt的连接对象
    private FutureConnection connection;


    @Autowired
    private LightGroupService lightGroupService;

    public Mqtt() throws URISyntaxException {
        // 设置mqtt broker的ip和端口
        mqtt.setHost(CONNECTION_STRING);
        // 连接前清空会话信息
        mqtt.setCleanSession(CLEAN_START);
        // 设置重新连接的次数
        mqtt.setReconnectAttemptsMax(RECONNECTION_ATTEMPT_MAX);
        // 设置重连的间隔时间
        mqtt.setReconnectDelay(RECONNECTION_DELAY);
        // 设置心跳时间
        mqtt.setKeepAlive(KEEP_ALIVE);
        // 设置缓冲的大小
        mqtt.setSendBufferSize(SEND_BUFFER_SIZE);
        //设置客户端id
        mqtt.setClientId(CLIENT_ID);
        // 获取mqtt的连接对象futureConnection
        connection = mqtt.futureConnection();
    }


    /**
     * 发布消息
     *
     * @param topic   终端订阅的主题
     * @param comment json格式的内容
     */
    public void publish(String topic, String comment) {
        connection.connect();
        connection.publish(topic, comment.getBytes(), QoS.AT_MOST_ONCE, false);
    }


    /**
     * 监听终端的响应消息包括遗嘱消息
     */
    public void subscribe() {
        try {
            connection.connect();
            connection.subscribe(topics);

            Future<Message> futureMessage = connection.receive();
            Message message = futureMessage.await();
            // 判断是否来自终端的响应
            if (message.getTopic().startsWith("$client/out/")) {
                WillMessage willMessage = new ObjectMapper().readValue(
                        String.valueOf(message.getPayloadBuffer()).substring(6), WillMessage.class);

                MqttResponse mqttResponse = new ObjectMapper().readValue(
                        String.valueOf(message.getPayloadBuffer()).substring(6), MqttResponse.class);

                // 处理遗嘱消息
                if (willMessage != null && willMessage.getData().get("online") != null && willMessage.getData().get("online").equals(1)) {
                    log.info(message.getTopic() + "离线了");
                }

                // 如果得到的消息是终端发布的回执
                if (mqttResponse != null && mqttResponse.getCmd().equals(2001)) {
                    log.info("更新数据库");
                    Integer onOff = mqttResponse.getData().getOnOff();
                    lightGroupService.updateStatusByMqtt(onOff, message.getTopic());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
        }
    }


}
