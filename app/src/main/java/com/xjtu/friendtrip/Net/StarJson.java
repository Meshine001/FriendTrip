package com.xjtu.friendtrip.Net;

import java.io.Serializable;

/**
 * Created by Meshine on 16/6/2.
 */
public class StarJson implements Serializable{

    public static final int DISCOVERY = 0;
    public static final int STORY = 1;
    public static final int TRACE = 2;

    public static final int TAG_LIKE = 0;
    public static final int TAG_UN_LIKE = 1;



    private Integer userId;
    private Integer type;
    private Integer id;
    private Integer tag;

    public StarJson() {
    }

    public StarJson(Integer userId, Integer type, Integer id, Integer tag) {
        this.userId = userId;
        this.type = type;
        this.id = id;
        this.tag = tag;
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

    public static int getTagLike() {
        return TAG_LIKE;
    }

    public static int getTagUnLike() {
        return TAG_UN_LIKE;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "StarJson{" +
                "userId=" + userId +
                ", type=" + type +
                ", id=" + id +
                ", tag=" + tag +
                '}';
    }
}
