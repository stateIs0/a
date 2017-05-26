package com.ipzoe.light.bean.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.fusesource.mqtt.codec.PUBLISH;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import static javafx.scene.input.KeyCode.R;

/**
 * Created by cxs on 2017/3/23.
 */
@ApiModel(value = "light", description = "灯")
@Entity
@Table(name = "light")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Light extends BaseEntity{
    // app 推送请求
    public static final int PUBLISH_CMD = 1001;
    // 终端设备响应
    public static final int SUBSCRIBE_CMD = 2001;
    // 关灯
    public static final int SHUT_ONOFF = 0;
    // 开灯
    public static final int OPEN_ONOFF = 1;
    // 定时无效
    public static final int NOT_VALID_ON_OFFSTATE_TIME = 1;
    // 定时有效
    public static final int VALID_ON_OFFSTATE_TIME= 0;
    // 遗嘱
    public static final int LAST_WILL = 2002;


    @ApiModelProperty("账户ID")
    private Long accountId;

    @ApiModelProperty("编号" )
    private String code;

    @ApiModelProperty("灯的唯一标识. 这里为灯上的mac地址")
    private String mac;

    @ApiModelProperty("所在灯组ID")
    private Long groupId;

    @ApiModelProperty("终端的发布主题out")
    private String publish;

    @ApiModelProperty("终端的订阅主题in")
    private String subscribe;

    @Transient
    private Formula formula;

    @Transient
    private Integer stageNum;

    @ApiModelProperty("是否空闲：空闲true，不空闲false")
    @Transient
    private boolean leisure;

    public boolean isLeisure() {
        return leisure;
    }

    public void setLeisure(boolean leisure) {
        this.leisure = leisure;
    }


    public Integer getStageNum() {
        return stageNum;
    }

    public void setStageNum(Integer stageNum) {
        this.stageNum = stageNum;
    }

    public Formula getFormula() {
        return formula;
    }

    public void setFormula(Formula formula) {
        this.formula = formula;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public String getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(String subscribe) {
        this.subscribe = subscribe;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
