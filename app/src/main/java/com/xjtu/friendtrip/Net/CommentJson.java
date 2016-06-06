package com.xjtu.friendtrip.Net;

import java.io.Serializable;

/**
 * Created by Meshine on 16/6/2.
 */
public class CommentJson implements Serializable {
    /**
     *
     "userId":1203,
     "toUserId":1204,
     "content":"这个风景真是美！",
     "type":1,    // 0 1 2 0是新景点  1 是心情 2 是游记
     "travelnotesId":1 代表上面三个的ID
     */

    public static final int DISCOVERY = 0;
    public static final int STORY = 1;
    public static final int TRACE = 2;

    private Integer userId;
    private Integer toUserId;
    private String content;
    private Integer type;
    private Integer id;
    private String datetime;

    public CommentJson() {
    }

    public CommentJson(Integer userId, Integer toUserId, String content, Integer type, Integer id, String datetime) {
        this.userId = userId;
        this.toUserId = toUserId;
        this.content = content;
        this.type = type;
        this.id = id;
        this.datetime = datetime;
    }

    public static int getDISCOVERY() {
        return DISCOVERY;
    }

    public static int getSTORY() {
        return STORY;
    }

    public static int getTRACE() {
        return TRACE;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return "CommentJson{" +
                "userId=" + userId +
                ", toUserId=" + toUserId +
                ", content='" + content + '\'' +
                ", type=" + type +
                ", id=" + id +
                ", datetime='" + datetime + '\'' +
                '}';
    }
}
