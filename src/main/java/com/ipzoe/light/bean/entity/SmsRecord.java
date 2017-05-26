package com.ipzoe.light.bean.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

/**
 * Created by wzx on 2017/3/30.
 */
@ApiModel(value = "SmsRecord", description = "手机短信")
@Entity
@Table(name = "sms_record")
public class SmsRecord {

    // 有效
    public static final int STATUS_NOT_USE = 0;
    // 过期
    public static final int STATUS_USED = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("手机号码")
    private String phone;

    @ApiModelProperty("验证码")
    private String code;

    @ApiModelProperty("状态")
    private int status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
