package com.ipzoe.light.repository;

import com.ipzoe.light.bean.entity.Timing;
import com.ipzoe.light.util.DefaultMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by cxs on 2017/4/14.
 */
@Mapper
public interface TimingRepository extends DefaultMapper<Timing> {


    @Select("select * form timing where account_id = #{0} and is_deleted = 0 ")
    List<Timing> selectByAccountId(Long id);




}
