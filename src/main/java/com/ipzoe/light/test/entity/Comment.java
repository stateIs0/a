package com.ipzoe.light.test.entity;

import javax.persistence.Transient;
import java.util.List;

/**
 * Created by cxs on 2017/4/14.
 */
public class Comment extends Base{


    String content;

    Long accountId;

    Long postId;


    @Transient
    List<Follow> followList;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public List<Follow> getFollowList() {
        return followList;
    }

    public void setFollowList(List<Follow> followList) {
        this.followList = followList;
    }
}
