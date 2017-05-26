package com.ipzoe.light.repository;

import com.ipzoe.light.bean.entity.FosterLightGroup;
import com.ipzoe.light.bean.entity.LightGroup;
import com.ipzoe.light.bean.response.Response;
import com.ipzoe.light.util.DefaultMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by cxs on 2017/4/10.
 */
@Mapper
public interface FosterLightGroupRepository extends DefaultMapper<FosterLightGroup> {


    @Select("select * from foster_light_group where light_group_id = #{0} and is_deleted = 0")
    List<FosterLightGroup> selectByLightGroupId(Long lightGroupId);

    @Select("select light_group_id from foster_light_group where foster_id = #{0} and is_deleted = 0")
    List<Long> selectLightGroupIdByFosterId(Long fosterId);

    @Update("update foster_light_group set is_deleted = 1 where foster_id = #{0}")
    void updateIsDeletedByFosterId(Long id);


    @Select("select * from light_group l1 join foster_light_group l2 on l1.id = l2.light_group_id where l2.foster_id = #{1} " +
            "and l1.status = #{0} " +
            "and l1.is_deleted = 0 " +
            "and l2.is_deleted = 0 ")
    List<LightGroup> selectLightGroupByFosterIdAndOnOff(Integer type, Long id);
}
