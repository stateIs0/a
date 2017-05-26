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
@ApiModel(value = "FormulaStage", description = "配方阶段表")
@Entity
@Table(name = "formula_stage")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FormulaStage extends BaseEntity {


    @ApiModelProperty("配方ID")
    private Long formulaId;

    @ApiModelProperty("阶段编号")
    private Integer serialNumber;

    @ApiModelProperty("持续天数")
    private Integer days;

    @ApiModelProperty("三原色红色R")
    private Integer red;

    @ApiModelProperty("三原色绿色G")
    private Integer green;

    @ApiModelProperty("三原色蓝色B")
    private Integer blue;

    @ApiModelProperty("色温")
    private Integer colorTem;

    @ApiModelProperty("阶段时间")
    @Transient
    private List<StageTime> stageTimeList;

    public List<StageTime> getStageTimeList() {
        return stageTimeList;
    }

    public void setStageTimeList(List<StageTime> stageTimeList) {
        this.stageTimeList = stageTimeList;
    }

    public Long getFormulaId() {
        return formulaId;
    }

    public void setFormulaId(Long formulaId) {
        this.formulaId = formulaId;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }
    public Integer getRed() {
        return red;
    }

    public void setRed(Integer red) {
        this.red = red;
    }

    public Integer getGreen() {
        return green;
    }

    public void setGreen(Integer green) {
        this.green = green;
    }

    public Integer getBlue() {
        return blue;
    }

    public void setBlue(Integer blue) {
        this.blue = blue;
    }

    public Integer getColorTem() {
        return colorTem;
    }

    public void setColorTem(Integer colorTem) {
        this.colorTem = colorTem;
    }
}
