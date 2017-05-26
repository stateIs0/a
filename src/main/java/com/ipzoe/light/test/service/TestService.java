package com.ipzoe.light.test.service;

import com.github.pagehelper.PageHelper;
import com.ipzoe.light.bean.response.PageBean;
import com.ipzoe.light.bean.response.Response;
import com.ipzoe.light.test.entity.Comment;
import com.ipzoe.light.test.entity.Post;
import com.ipzoe.light.test.entity.PostResponse;
import com.ipzoe.light.test.repository.CommentRepository;
import com.ipzoe.light.test.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cxs on 2017/4/14.
 */

public class TestService {

    @Autowired
    private Repository repository;

    @Autowired
    private CommentRepository commentRepository;

    // 查询所有帖子
    public Response select(String key,int page,int size) {
        Map<String,Object> map = new HashMap<>();
        if (key != null) {
            map.put("key",key);
        }
//        PageHelper.startPage(page,size);
        List<Post> postList = repository.selectAllByPage(map);
        return Response.ok(new PageBean<>(postList,repository.selectCount(null)));
    }

    // 根据帖子ID查询该帖子下所有评论和评论下的跟帖
    public Response selectContent(Long id, int page,int size) {

        PageHelper.startPage(page,size);
        List<Comment> list = commentRepository.selectByPostId(id);
        Post post = repository.selectByPrimaryKey(id);
        post.setCommentList(list);

        PostResponse postRequest = new PostResponse();
        postRequest.setPost(post);
        postRequest.setPageBean(new PageBean<>(list,repository.selectCount(null)));
        return Response.ok(postRequest);
    }
}
