package com.xjtu.friendtrip.bean;

import java.io.Serializable;

/**
 * Created by Ming on 2016/6/5.
 */
public class Star implements Serializable {
    private Integer id;
    private String name;
    private String nickname;
    private Integer focusCount;
    private Integer isFocusCount;
    private String profilePhoto;

    public Star() {
    }

    public Star(Integer id, String name, String nickname, Integer focusCount, Integer isFocusCount, String profilePhoto) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.focusCount = focusCount;
        this.isFocusCount = isFocusCount;
        this.profilePhoto = profilePhoto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getFocusCount() {
        return focusCount;
    }

    public void setFocusCount(Integer focusCount) {
        this.focusCount = focusCount;
    }

    public Integer getIsFocusCount() {
        return isFocusCount;
    }

    public void setIsFocusCount(Integer isFocusCount) {
        this.isFocusCount = isFocusCount;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    @Override
    public String toString() {
        return "Star{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", focusCount=" + focusCount +
                ", isFocusCount=" + isFocusCount +
                ", profilePhoto='" + profilePhoto + '\'' +
                '}';
    }
}
