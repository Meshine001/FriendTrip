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
    private int userId;
    private int toUserId;
    private String content;
    private int type;
    private int id;

    public CommentJson(int userId, int toUserId, String content, int type, int id) {
        this.userId = userId;
        this.toUserId = toUserId;
        this.content = content;
        this.type = type;
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
