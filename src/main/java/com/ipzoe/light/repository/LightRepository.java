package com.ipzoe.light.repository;


import com.ipzoe.light.bean.entity.Light;
import com.ipzoe.light.bean.entity.LightGroup;
import com.ipzoe.light.bean.response.Response;
import com.ipzoe.light.repository.provider.LightProvider;
import com.ipzoe.light.util.DefaultMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by cxs on 2017/3/23.
 */
@Mapper
public interface LightRepository extends DefaultMapper<Light> {

    @Select("select * from  light where is_deleted = 0 and group_id is not null and id = #{0} ")
    Light findInUse(Long lightId);

    @Select("update light set is_deleted = 1 where id = #{0}")
    Light deleteLight(Long lightId);

    @SelectProvider(type = LightProvider.class, method = "queryLight")
    @Results({
            @Result(property = "groupId", column = "group_id"),
            @Result(property = "formula", column = "group_id", one = @One(select = "com.ipzoe.light.repository.FormulaRepository.selectByGroupId")),
            @Result(property = "stageNum",column = "group_id", one = @One(select = "com.ipzoe.light.repository.FormulaStageRepository.selectNum"))
    })
    List<Light> selectPlatform(Map<String, Object> map);


    @SelectProvider(type = LightProvider.class, method = "selectByGroupId")
    List<Light>  selectLight(Long groupId, Long accountId);


    @SelectProvider(type = LightProvider.class, method = "setGroup")
    List<Light> setGroup(Long groupId, Long id);

    @Select(" update light set group_id = null where account_id = #{0} and is_deleted=0 and group_id=#{1} ")
    void setGroupNull(Long accountId,Long groupId);

    @Select("select * from light where group_id = #{0} and is_deleted = 0")
    List<Light> selectByLightGroupId(Long groupId);

    @Select("select id from light where light_group_id = #{0}")
    List<Long> selectLightIdByLightGroupId(Long id);

    @Update("update light group_id = null where group_id = #{0} and is_deleted = 0")
    void updateIsDeletedByGroupId(Long id);

    @Update("update light set group_id = #{1} where id = #{0} and is_deleted = 0 ")
    void updateLightGroupId(Long id, Long lightGroupId);

    @Select("select * from light where account_id = #{0} and is_deleted = 0")
    List<Light> selectByAccountId(Long id);

    @Select("select * from light where account_id = #{0} and is_deleted = 0 and group_id is null")
    List<Light> selectLeisureByAccountId(Long id);

    @Update("update light set group_id = #{1} where id = #{0}  ")
    void updateGroupIdById(Long id, Long groupId);
}
