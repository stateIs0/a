package com.ipzoe.light.test.repository.provider;

import java.util.Map;

/**
 * Created by cxs on 2017/4/14.
 */
public class Provider {

    public String query(Map<String,Object> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("select * from post ");
        if (map.containsKey("key")) {
            sb.append("where title like '" + map.get("key") + "'");

        }
        sb.append(" order by update_time desc");
        return sb.toString();
    }
}
