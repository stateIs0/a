package com.ipzoe.light.repository.provider;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

import static org.apache.ibatis.jdbc.SelectBuilder.BEGIN;

/**
 * Created by cxs on 2017/3/24.
 */
public class StageTimeProvider {

    public String queryStageTime(Map map) {
        return new SQL() {
            {
                SELECT("*");
                FROM("stage_time s1");
                JOIN("formula_stage s2 on s1.stage_id = s2.id ");
                JOIN("formula s3 on s2.formula_id = s3.id ");
                WHERE("s1.stage_id = " + map.get("stageId"));
                WHERE("s1.is_deleted = 0");
                WHERE("s2.is_deleted = 0");
                WHERE("s3.is_deleted = 0");
                ORDER_BY("start_time ");
            }
        }.toString();
    }
}
