package com.ipzoe.light.repository.provider;

import java.util.Map;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

/**
 * Created by wzx on 2017/3/28.
 */
public class AdminLightProvider {

    public String adminLight(Map map) {
        BEGIN();
        SELECT("*");
        FROM("light a left join account b on a.account_id=b.id ");

        WHERE("a.is_deleted = 0");

        if (map.containsKey("key"))
            WHERE("b.username like '" + map.get("key") + "'");

        ORDER_BY("a.create_time desc");
        /*FROM("light");
        WHERE("is_deleted = 0");*/
        return SQL();
    }

}

