package com.ipzoe.light.repository;

import com.ipzoe.light.bean.entity.Account;
import com.ipzoe.light.util.DefaultMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created by cxs on 2017/3/23.
 */
@Mapper
public interface AccountRepository extends DefaultMapper<Account>{

    @Select("SELECT * FROM account WHERE username = #{0} and is_deleted=0 ")
    Account findByUsername(String username);

    @Select("select * from account where username = #{0} and is_deleted = 0")
    Account selectByUsername(String phone);
}
