package com.ipzoe.light.repository.provider;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

/**
 * Created by cxs on 2017/3/24.
 */
public class FormulaStageProvider {

    public String queryFormulaStage(Map map) {
        return new SQL() {
            {
                SELECT("*");
                FROM("formula_stage f1 ");
                JOIN("formula f2 on f1.formula_id = f2.id ");
                WHERE("f1.formula_id = " + map.get("formulaId"));
                WHERE(" f1.is_deleted = 0 ");
                WHERE(" f2.is_deleted = 0 ");
                ORDER_BY("serial_number");
            }
        }.toString();
    }
}
