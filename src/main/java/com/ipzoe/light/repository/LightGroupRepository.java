package com.ipzoe.light.repository;

import com.ipzoe.light.bean.entity.FormulaStage;
import com.ipzoe.light.bean.entity.LightGroup;
import com.ipzoe.light.util.DefaultMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by cxs on 2017/3/28.
 */
@Mapper
public interface LightGroupRepository extends DefaultMapper<LightGroup> {

    @Select("SELECT * FROM light_group WHERE is_deleted = 0  and  name = #{0} and account_id= #{1}  ")
    LightGroup findByGroupName(String groupName,Long accountId);

    @Select("SELECT * FROM light_group WHERE id= #{0} and is_deleted = 0 ")
    LightGroup selectStatus(Long lightGroupId);

    @Select("update light_group set is_deleted=1 where id = #{0} ")
    LightGroup updateIsDeleted(Long lightGroupId);

    @Select("update light set group_id=null where group_id = #{0} ")
    LightGroup updateLightIsNull(Long lightGroupId);


    @Select("select * from light_group where id = #{0} and formula_id is not null ")
    LightGroup findFormula(Long groupId);

    @Select("update light_group set status= #{0} where id = #{1} ")
    LightGroup setStatus(Integer status,Long groupId);

    @Update("update light_group set status = #{0} from light_group l1 join light l2 where l2.publish = #{1} " +
            "and l1.is_deleted = 0 " +
            "and l2.is_deleted = 0 ")
    void updateStatusByMqtt(Integer onOff, String topic);

    @Select("select s1.subscribe from light s1 " +
            "join light_group s2 on s1.group_id = s2.id " +
            "where s2.id = #{0} " +
            "and s1.is_deleted = 0 " +
            "and s2.is_deleted = 0 ")
    List<String> selectSubscribeByLightGroupId(Long lightGroupId);

    @Select("select * from light_group where id = #{0} and is_deleted = 0")
    LightGroup selectById(Long id);

    @Select("SELECT " +
            "sum(days) daySum " +
            "FROM " +
            "formula_stage f1 " +
            "JOIN formula f2 ON f1.formula_id = f2.id " +
            "JOIN light_group f3 on f2.id = f3.formula_id " +
            "where f3.id = #{0} " +
            "AND f1.is_deleted = 0 " +
            "AND f2.is_deleted = 0 " +
            "AND f3.is_deleted = 0 " )
    Integer selectDaysSumById(Long id);

    @Select("SELECT " +
            " f1.* " +
            "FROM " +
            "formula_stage f1 " +
            "JOIN formula f2 ON f1.formula_id = f2.id " +
            "JOIN light_group f3 ON f2.id = f3.formula_id " +
            "WHERE " +
            "f3.id = #{0} " +
            "AND f1.is_deleted = 0 " +
            "AND f2.is_deleted = 0 " +
            "AND f3.is_deleted = 0 ")
    List<FormulaStage> selectFormulaStageStageById(Long lightGroupId);

    @Select("SELECT " +
            "* " +
            "FROM " +
            "light_group  " +
            "WHERE " +
            "account_id = #{0} " +
            "and status = 1 " +
            "AND is_deleted = 0 " )
    List<LightGroup> selectLightGroupByAccountId(Long id);

    @Select("select * from light_group where account_id = #{0} and status = #{1} and is_deleted = 0")
    List<LightGroup> selectOnOffByAccountId(Long id, int type);

    @Update("update light_group set formula_id = null where formula_id = #{0} and is_deleted = 0 ")
    void updateIsNullByFormulaId(Long id);

    @Update("update light_group set status = 0 where id = #{0} and is_deleted = 0")
    void updateStatusByFoster(Long id);
}
