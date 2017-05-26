package com.ipzoe.light.bean.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ipzoe.light.bean.entity.FormulaStage;

import java.util.List;

/**
 * API新增／更新 光配方对象
 * Created by cxs on 2017/4/12.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FormulaRequest {

    private Long formulaId;

    private String name;

    private List<FormulaStage> formulaStageList;

    public Long getFormulaId() {
        return formulaId;
    }

    public void setFormulaId(Long formulaId) {
        this.formulaId = formulaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FormulaStage> getFormulaStageList() {
        return formulaStageList;
    }

    public void setFormulaStageList(List<FormulaStage> formulaStageList) {
        this.formulaStageList = formulaStageList;
    }
}
