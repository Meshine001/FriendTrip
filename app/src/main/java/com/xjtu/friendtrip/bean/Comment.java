package com.xjtu.friendtrip.bean;

import java.io.Serializable;

/**
 * Created by Ming on 2016/6/5.
 */
public class Comment implements Serializable {
    private Integer commentId;
    private String content;
    private Integer fromUserId;
    private Integer toUserId;
    private String datetime;

    public Comment() {
    }

    public Comment(Integer commentId, String content, Integer fromUserId, Integer toUserId, String datetime) {
        this.commentId = commentId;
        this.content = content;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.datetime = datetime;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", content='" + content + '\'' +
                ", fromUserId=" + fromUserId +
                ", toUserId=" + toUserId +
                ", datetime='" + datetime + '\'' +
                '}';
    }
}
