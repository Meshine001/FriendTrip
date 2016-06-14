package com.xjtu.friendtrip.Net;

import java.io.Serializable;

/**
 * Created by Meshine on 16/6/14.
 */
public class FollowTAJson implements Serializable {
    private Integer userid;
    private Integer attention_userid;
    private String createDate;

    public FollowTAJson() {
    }

    public FollowTAJson(Integer userid, Integer attention_userid, String createDate) {
        this.userid = userid;
        this.attention_userid = attention_userid;
        this.createDate = createDate;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getAttention_userid() {
        return attention_userid;
    }

    public void setAttention_userid(Integer attention_userid) {
        this.attention_userid = attention_userid;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "FollowTAJson{" +
                "userid=" + userid +
                ", attention_userid=" + attention_userid +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
