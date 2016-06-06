package com.xjtu.friendtrip.bean;

import java.io.Serializable;

/**
 * Created by Meshine on 16/6/2.
 */
public class User implements Serializable{

    private Integer id;
    private String name;
    private String password;
    private Integer focusCount;
    private Integer isFocusCount;
    private String profilePhoto;
    private String nickname;



    public User(Integer id, String name,String password, Integer focusCount, Integer isFocusCount, String profilePhoto, String nickname) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.focusCount = focusCount;
        this.isFocusCount = isFocusCount;
        this.profilePhoto = profilePhoto;
        this.nickname = nickname;
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



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", focusCount=" + focusCount +
                ", isFocusCount=" + isFocusCount +
                ", profilePhoto='" + profilePhoto + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
