package com.ipzoe.light.bean.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * Created by cxs on 2017/3/23.
 */
@ApiModel(value = "formula", description = "配方表")
@Entity
@Table(name = "formula")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Formula extends BaseEntity  {

    @ApiModelProperty("配方所属账户ID. 如果为0, 表示为平台配方")
    private Long accountId;

    @ApiModelProperty("配方名称")
    private String name;

    @ApiModelProperty("阶段数量")
    @Transient
    private Integer stageNum;

    @ApiModelProperty("阶段详细")
    @Transient
    private List<FormulaStage> formulaStageList;

    public List<FormulaStage> getFormulaStageList() {
        return formulaStageList;
    }

    public void setFormulaStageList(List<FormulaStage> formulaStageList) {
        this.formulaStageList = formulaStageList;
    }

    public Integer getStageNum() {
        return stageNum;
    }

    public void setStageNum(Integer stageNum) {
        this.stageNum = stageNum;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
