package com.ipzoe.light.repository;

import com.ipzoe.light.bean.entity.Foster;
import com.ipzoe.light.util.DefaultMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by cxs on 2017/4/10.
 */
@Mapper
public interface FosterRepository extends DefaultMapper<Foster> {

    @Select("select * from foster where name = #{0} and is_deleted = 0")
    Foster selectByName(String name);

    @Select("select * from foster where account_id = #{0} and is_deleted = 0 and status <> 3")
    List<Foster> selectByAccountId(Long id);

    @Select("select * from foster where id = #{0} and is_deleted = 0 ")
    Foster selectById(Long id);

    @Select("update foster set is_deleted = 1 where id = #{0} ")
    void updateIsDeleted(Long id);

    @Select("select status from foster where account_id = #{0} and is_deleted = 0")
    List<Integer> selectStatusByAccountId(Long id);

    @Update("update foster set on_off = #{1} where account_id = #{0} and status != 3 and is_deleted = 0 ")
    void updateStatusOnOff(Long accountId, int onOff);

    @Select("select * from foster where account_id = #{0} and is_deleted =0 ")
    List<Foster> selectAllByAccountId(Long id);

    @Update("update foster set on_off = #{1} where account_id = #{0} and id = #{2} and is_deleted = 0 and status != 3 ")
    void updateStatusOnOffByFosterId(Long id, Integer type, Long fosterId);

    @Select("SELECT\n" +
            "\tt4.*\n" +
            "FROM\n" +
            "\ttiming t1\n" +
            "JOIN timing_light_group t2 ON t1.id = t2.timing_id\n" +
            "JOIN foster_light_group t3 ON t2.light_group_id = t3.light_group_id\n" +
            "JOIN foster t4 ON t3.foster_id = t4.id\n" +
            "WHERE\n" +
            "\tt1.id = 1\n" +
            "AND t4.on_off = 0\n" +
            "AND(t4.`status` <> 3 OR t4.`status` <> 1)\n" +
            "AND t1.is_deleted = 0\n" +
            "AND t2.is_deleted = 0\n" +
            "AND t3.is_deleted = 0\n" +
            "AND t4.is_deleted = 0")
    List<Foster> checkIsUseById(Long timingId);
}
