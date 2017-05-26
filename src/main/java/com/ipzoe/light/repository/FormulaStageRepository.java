package com.ipzoe.light.repository;

import com.ipzoe.light.bean.entity.FormulaStage;
import com.ipzoe.light.repository.provider.FormulaStageProvider;
import com.ipzoe.light.util.DefaultMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by cxs on 2017/3/23.
 */
@Mapper
public interface FormulaStageRepository extends DefaultMapper<FormulaStage>{

    @Select("select * from formula_stage " +
            "where formula_id = #{formulaStage.formulaId} " +
            "and serial_number = #{formulaStage.serialNumber} " +
            "and is_deleted = 0")
    FormulaStage selectByFormulaId(@Param("formulaStage") FormulaStage formulaStage);

    @SelectProvider(type = FormulaStageProvider.class, method = "queryFormulaStage")
    List<FormulaStage> selectFormulaStage(Map<String, Object> map);

    @Select("select * from formula_stage f1 " +
            "join formula f2 on f1.formula_id = f2.id " +
            "join light_group f3 on f2.id = f3.formula_id " +
            "where f1.id = #{formulaStage.id} " +
            "and f3.status = 1 " +
            "and f1.is_deleted = 0 " +
            "and f2.is_deleted = 0 " +
            "and f3.is_deleted = 0 ")
    FormulaStage checkIsUseing(@Param("formulaStage") FormulaStage formulaStage);


    @Select("select * from formula_stage f1 " +
            "join formula f2 on f1.formula_id = f2.id " +
            "and f1.id = #{formulaStageId} " +
            "and f2.account_id = #{accountId} " +
            "and f1.is_deleted = 0 " +
            "and f2.is_deleted = 0")
    FormulaStage selectByIdAndAccount(@Param("accountId") Long accountId, @Param("formulaStageId") Long formulaStageId);


    @Update("update formula_stage set is_deleted = 1 where formula_id = #{0} and is_deleted = 0")
    void setIsDeleted(Long id);

    @Select("select * from formula_stage where formula_id = #{0} and is_deleted = 0")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "stageTimeList", column = "id", one = @One(select = "com.ipzoe.light.repository.StageTimeRepository.selectAllByStagId"))
    })
    List<FormulaStage> queryByFormulaId(Long id);



    @Select("select count(*) from formula_stage s1 join formula s2 on s1.formula_id = s2.id where s2.id = #{0} " +
            "and s1.is_deleted = 0 " +
            "and s2.is_deleted = 0")
    Integer selectNum (Long id);

    @Update("update formula_stage set is_deleted = 1 where formula_id = #{0} and is_deleted = 0")
    void updateIsDeletedByFormulaId(Long id);
}


