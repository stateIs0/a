package com.ipzoe.light.repository;

import com.ipzoe.light.bean.entity.Admin;
import com.ipzoe.light.util.DefaultMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by wzx on 2017/3/29.
 */
@Mapper
public interface AdminLoginRepository extends DefaultMapper<Admin> {

    @Select("select * from admin where username = #{0} and is_deleted=0")
    Admin findByUsername(String username);
}
