package com.ipzoe.light.test.entity;

import com.ipzoe.light.bean.response.PageBean;

/**
 * Created by cxs on 2017/4/14.
 */
public class PostResponse {

    Post post;

    PageBean pageBean;


    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public PageBean getPageBean() {
        return pageBean;
    }

    public void setPageBean(PageBean pageBean) {
        this.pageBean = pageBean;
    }
}