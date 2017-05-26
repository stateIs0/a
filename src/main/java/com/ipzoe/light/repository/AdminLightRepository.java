package com.ipzoe.light.repository;

import com.ipzoe.light.bean.entity.Light;
import com.ipzoe.light.repository.provider.AdminLightProvider;
import com.ipzoe.light.util.DefaultMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * Created by wzx on 2017/3/28.
 */
@Mapper
public interface AdminLightRepository extends DefaultMapper<Light> {

    @SelectProvider(type = AdminLightProvider.class, method = "adminLight")
    List<Light> selectPlatform(Map<String, Object> map);
}
