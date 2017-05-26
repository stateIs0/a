package com.ipzoe.light.repository.provider;

import java.util.Map;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

/**
 * Created by wzx on 2017/3/28.
 */
public class AdminProvider {

    public String admin(Map map) {
        BEGIN();
        SELECT("*");
        FROM("account");

        WHERE("is_deleted = 0");

        if (map.containsKey("key")) {
            WHERE("username like '" + map.get("key") + "'");
        }

        ORDER_BY("create_time desc");

        return SQL();
    }




}
