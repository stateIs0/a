package com.ipzoe.light.test.repository;

import com.ipzoe.light.test.entity.Follow;
import com.ipzoe.light.util.DefaultMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by cxs on 2017/4/14.
 */
public interface FollowRepository extends DefaultMapper<Follow>{

    @Select("select * form follow where comment_id = #{0} and is_deleted = 0 and order by create_time desc")
    List<Follow> selectByCommentId(Long commentId);
}
