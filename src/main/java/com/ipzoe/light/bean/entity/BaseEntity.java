package com.ipzoe.light.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by cxs on 2017/3/23.
 */
@ApiModel(value = "BaseEntity", description = "基础字段")
public class BaseEntity implements Serializable {

    // 未删除
    public static final int DELETE_NO = 0;
    // 已删除
    public static final int DELETE_YES = 1;


    @ApiModelProperty(value = "ID主键")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "Mysql")
    protected Long id;

    @ApiModelProperty("创建时间")
    @JsonIgnore
    protected LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonIgnore
    protected LocalDateTime updateTime;

    @ApiModelProperty("是否删除 0=未删除 1=已删除")
    @JsonIgnore
    protected Integer isDeleted;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}
