package com.xjtu.friendtrip.bean;

/**
 * Created by Meshine on 16/6/2.
 */
public class User {

    private Integer id;
    private String name;
    private String tagId;
    private String password;
    private Integer focusCount;
    private Integer isFocusCount;
    private Integer friendsCount;
    private String profilePhoto;
    private String nickname;



    public User(Integer id, String name, String tagId, String password, Integer focusCount, Integer isFocusCount, Integer friendsCount, String profilePhoto, String nickname) {
        this.id = id;
        this.name = name;
        this.tagId = tagId;
        this.password = password;
        this.focusCount = focusCount;
        this.isFocusCount = isFocusCount;
        this.friendsCount = friendsCount;
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

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
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

    public Integer getFriendsCount() {
        return friendsCount;
    }

    public void setFriendsCount(Integer friendsCount) {
        this.friendsCount = friendsCount;
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
}
