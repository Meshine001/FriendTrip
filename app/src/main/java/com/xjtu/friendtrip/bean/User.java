package com.xjtu.friendtrip.bean;

import java.io.Serializable;

/**
 * Created by Meshine on 16/6/2.
 */
public class User implements Serializable{

    private Integer id;
    private String name;
    private Integer focusCount;
    private Integer isFocusCount;
    private String profilePhoto;
    private String nickname;
    private String email;
    private String region;
    private String gender;
    private String birthday;
    private String signature;


    public User(Integer id, String name, Integer focusCount, Integer isFocusCount, String profilePhoto, String nickname, String email, String region, String gender, String birthday, String signature) {
        this.id = id;
        this.name = name;
        this.focusCount = focusCount;
        this.isFocusCount = isFocusCount;
        this.profilePhoto = profilePhoto;
        this.nickname = nickname;
        this.email = email;
        this.region = region;
        this.gender = gender;
        this.birthday = birthday;
        this.signature = signature;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", focusCount=" + focusCount +
                ", isFocusCount=" + isFocusCount +
                ", profilePhoto='" + profilePhoto + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", region='" + region + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday='" + birthday + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}
