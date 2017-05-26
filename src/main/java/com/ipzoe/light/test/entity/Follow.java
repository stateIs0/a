package com.ipzoe.light.test.entity;

/**
 * Created by cxs on 2017/4/14.
 */
public class Follow extends Base {

    Long accountId;

    Long commentId;

    String content;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
