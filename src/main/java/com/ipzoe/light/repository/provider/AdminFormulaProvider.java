package com.ipzoe.light.repository.provider;

import java.util.Map;

import static org.apache.ibatis.jdbc.SelectBuilder.*;

/**
 * Created by cxs on 2017/3/28.
 */
public class AdminFormulaProvider {

    public String queryList(Map map) {

        StringBuilder sb = new StringBuilder();
        sb.append("select * from formula where account_id = ");
        sb.append(map.get("accountId"));
        sb.append(" and is_deleted = 0");
        if (map.containsKey("key")) {
            sb.append(" and name like '" + map.get("key") + "'");
        }
        return sb.toString();
    }



}
