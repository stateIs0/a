package com.ipzoe.light.repository;

import com.ipzoe.light.bean.entity.Formula;
import com.ipzoe.light.repository.provider.AdminFormulaProvider;
import com.ipzoe.light.repository.provider.FormulaProvider;
import com.ipzoe.light.util.DefaultMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.core.annotation.Order;

import java.util.List;
import java.util.Map;

/**
 * Created by cxs on 2017/3/23.
 */
@Mapper
public interface FormulaRepository extends DefaultMapper<Formula> {


    @Select("select * from formula " +
            "where account_id = #{0} " +
            "and name = #{formula.name} " +
            "and is_deleted = 0")
    Formula selectByName(Long accountId, @Param("formula") Formula formula);

    @SelectProvider(type = FormulaProvider.class, method = "queryFormula")
    @Results({
            @Result(property ="id", column = "id"),
            @Result(property = "stageNum" , column = "id" ,one = @One(select = "com.ipzoe.light.repository.FormulaStageRepository.selectNum"))
    })
    List<Formula> selectByAccountId(Map<String, Object> map);

    @Select("select * from formula f1 " +
            "join light_group f2 on f1.id = f2.formula_id " +
            "where f1.id = #{formula.id} " +
            "and f2.status = 1 " +
            "and f1.is_deleted = 0 " +
            "and f2.is_deleted = 0")
    List<Formula>   checkFormulaStatus(@Param("formula") Formula formula);


    @SelectProvider(type = AdminFormulaProvider.class, method = "queryList")
    List<Formula> adminSelectByAccountId(Map map);

    @Select("select s1.* from formula s1 join light_group s2 on s1.id = s2.formula_id where s2.id = #{0} and s1.is_deleted = 0 and s2.is_deleted = 0")
    Formula selectByGroupId(Long id);

    @Select("select * from formula where id = #{0}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "formulaStageList", column = "id", one = @One(select = "com.ipzoe.light.repository.FormulaStageRepository.queryByFormulaId"))
    })
    Formula selectById(Long id);
}
