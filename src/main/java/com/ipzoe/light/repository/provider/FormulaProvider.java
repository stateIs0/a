package com.ipzoe.light.repository.provider;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

import static org.apache.ibatis.jdbc.SelectBuilder.BEGIN;

/**
 * Created by cxs on 2017/3/24.
 */
public class FormulaProvider {

    public String queryFormula(Map map) {
        return new SQL() {
            {
                SELECT("* ");
                FROM("formula ");
                if (map.containsKey("accountPlatform ")) {
                    WHERE("account_id = " + map.get("accountPlatform"));
                } else if (map.containsKey("myself")) {
                    WHERE("account_id = " + map.get("myself") + " ");
                }

                if (map.containsKey("key")) {
                    WHERE("name like '" + map.get("key") + "'");
                }
                WHERE("is_deleted = 0");
            }
        }.toString();
    }
}
