package com.ipzoe.light.bean.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by cxs on 2017/3/23.
 */
@ApiModel(value = "LightGroup", description = "灯组表")
@Entity
@Table(name = "light_group")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LightGroup extends BaseEntity {


    // 开
    public static final int ON = 0;

    // 关
    public static final int OFF = 1;


    @ApiModelProperty("灯组名称")
    private String name;

    @ApiModelProperty("使用的配方ID")
    private Long formulaId;

    @ApiModelProperty("执行天数")
    private Integer duration;

    @ApiModelProperty("账户ID")
    private Long account_id;

    @ApiModelProperty("灯组开关： 0:开 1:关  ")
    private Long status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getFormulaId() {
        return formulaId;
    }

    public void setFormulaId(Long formulaId) {
        this.formulaId = formulaId;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Long getAccount_id() {
        return account_id;
    }

    public void setAccount_id(Long account_id) {
        this.account_id = account_id;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

}
