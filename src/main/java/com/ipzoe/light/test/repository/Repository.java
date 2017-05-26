package com.ipzoe.light.test.repository;

import com.ipzoe.light.test.entity.Post;
import com.ipzoe.light.test.repository.provider.Provider;
import com.ipzoe.light.util.DefaultMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by cxs on 2017/4/14.
 */
public interface Repository extends DefaultMapper<Post>{

    @SelectProvider(type = Provider.class,method = "query")
    List<Post> selectAllByPage(Map<String, Object> map);

}
