package com.ipzoe.light.repository;

import com.ipzoe.light.bean.entity.StageTime;
import com.ipzoe.light.repository.provider.StageTimeProvider;
import com.ipzoe.light.util.DefaultMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;


/**
 * Created by cxs on 2017/3/23.
 */
@Mapper
public interface StageTimeRepository extends DefaultMapper<StageTime> {


    @SelectProvider(type = StageTimeProvider.class, method = "queryStageTime")
    List<StageTime> selectByStageId(Map<String, Object> map);

    @Select("select * from stage_time " +
            "where stage_id = #{0} " +
            "and is_deleted = 0 " +
            "order by end_time")
    List<StageTime> selectAllByStagId(Long stageId);

    @Select("select s1.* from stage_time s1 " +
            "join formula_stage s2 on s1.stage_id = s2.id " +
            "join formula s3 on s2.formula_id = s3.id " +
            "join light_group s4 on s3.id = s4.formula_id " +
            "where s1.id = #{stageTime.id} " +
            "and s4.status = 1 " +
            "and s4.account_id = #{0} " +
            "and s1.is_deleted = 0 " +
            "and s2.is_deleted = 0 " +
            "and s3.is_deleted = 0 " +
            "and s4.is_deleted = 0 ")
    List<StageTime> selectIsUse(Long accountId, @Param("stageTime") StageTime stageTime);

    @Select("select s1.* from stage_time s1 " +
            "join formula_stage s2 on s1.stage_id = s2.id " +
            "join formula s3 on s2.formula_id = s3.id " +
            "where s1.id = #{1} " +
            "and s3.account_id = #{0} " +
            "and s1.is_deleted = 0 " +
            "and s2.is_deleted = 0 " +
            "and s3.is_deleted = 0 " )
    List<StageTime> checkAccount(Long accountId, Long stageTimeId);

    @Update("update stage_time set is_deleted = 1 where stage_id = #{0} and is_deleted = 0")
    void setIsDeleted(Long id);

    // 根据灯组ID查询阶段编号第一条下的时间段（limit 可改为递增条件）
    @Select("SELECT\n" +
            "	s1.*\n" +
            "FROM\n" +
            "	stage_time s1\n" +
            "JOIN formula_stage s2 ON s1.stage_id = s2.id\n" +
            "JOIN formula s3 ON s2.formula_id = s3.id\n" +
            "JOIN light_group s4 ON s3.id = s4.formula_id\n" +
            "WHERE\n" +
            "	s4.id = 1\n" +
            "AND s1.is_deleted = 0\n" +
            "AND s2.is_deleted = 0\n" +
            "AND s3.is_deleted = 0\n" +
            "AND s4.is_deleted = 0\n" +
            "AND s2.serial_number =(select s5.* from(\n" +
            "	SELECT\n" +
            "		s2.serial_number\n" +
            "	FROM\n" +
            "		stage_time s1\n" +
            "	JOIN formula_stage s2 ON s1.stage_id = s2.id\n" +
            "	JOIN formula s3 ON s2.formula_id = s3.id\n" +
            "	JOIN light_group s4 ON s3.id = s4.formula_id\n" +
            "	WHERE\n" +
            "		s4.id = 1\n" +
            "	AND s1.is_deleted = 0\n" +
            "	AND s2.is_deleted = 0\n" +
            "	AND s3.is_deleted = 0\n" +
            "	AND s4.is_deleted = 0\n" +
            "	ORDER BY s2.serial_number\n" +
            "	\n" +
            ") s5 LIMIT 1) ")
    List<StageTime> selectStageTimeByLightGroupId(Long lightGroupId);

    @Update("UPDATE stage_time s1 " +
            "JOIN formula_stage s2 ON s1.stage_id = s2.id " +
            "SET s1.is_deleted = 1 " +
            "WHERE " +
            "s2.formula_id = #{0} ")
    void updateIsDeletedByFormulaId(Long id);

}
