package com.ipzoe.light.repository.provider;

import java.util.List;
import java.util.Map;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

/**
 * Created by wzx on 2017/3/28.
 */
public class LightProvider {

    public String queryLight(Map map) {
        BEGIN();
        SELECT("*");
        FROM("light ");

        WHERE("is_deleted = 0 ");
        WHERE("account_id = " + map.get("accountId") + " ");
        if(map.containsKey("groupId")) {
            WHERE("group_id = " + map.get("groupId") + " ");

        } else {
            WHERE("group_id is null ");
        }
        ORDER_BY("create_time desc");


        return SQL();
    }


    public String setGroup(Long groupId,Long id) {

        StringBuffer sql = new StringBuffer();

            sql.append(" update light set  ");
            sql.append(" group_id = ").append(groupId);
            sql.append(" where id = ").append(id);

        return sql.toString();
    }


    public String selectByGroupId(Long groupId, Long accoutId) {
        StringBuilder sb = new StringBuilder();
        sb.append("select * from light where account_id = ").
                append(accoutId.toString()).
                append(" and is_deleted = 0 ").append(" and group_id is null");
        if (groupId != null) {
            sb.append(" or group_id = ").append(groupId);
        }
        return sb.toString();
    }

}
