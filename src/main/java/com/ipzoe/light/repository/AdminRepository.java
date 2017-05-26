package com.ipzoe.light.repository;

import com.ipzoe.light.bean.entity.Account;
import com.ipzoe.light.bean.entity.Admin;
import com.ipzoe.light.repository.provider.AdminProvider;
import com.ipzoe.light.util.DefaultMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * Created by wzx on 2017/3/28.
 */
@Mapper
public interface AdminRepository extends DefaultMapper<Admin> {

    @SelectProvider(type = AdminProvider.class, method = "admin")
    List<Account> selectPlatform(Map<String, Object> map);

}
