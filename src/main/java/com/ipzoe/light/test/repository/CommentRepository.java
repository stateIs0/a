package com.ipzoe.light.test.repository;

import com.ipzoe.light.test.entity.Comment;
import com.ipzoe.light.util.DefaultMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by cxs on 2017/4/14.
 */
public interface CommentRepository extends DefaultMapper<Comment>{

    @Select("select * from comment where post_id = #{0} and is_deleted = 0 order by create_time desc")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "followList", column = "id",one = @One(select = "FollowRepository.selectByCommentId"))
    })
     List<Comment> selectByPostId(Long PostId);
}
