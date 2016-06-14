package com.xjtu.friendtrip.Net;

import java.io.Serializable;

/**
 * Created by Meshine on 16/6/12.
 */
public class UserJson implements Serializable {
    private Integer id;
    private String password;
    private String nickname;
    private String profilephoto;
    private String email;
    private String region;
    private String gender;
    private String birthday;
    private String signature;


    public UserJson() {
    }

    public UserJson(Integer id, String password, String nickname, String profilephoto, String email, String region, String gender, String birthday, String signature) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
        this.profilephoto = profilephoto;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProfilephoto() {
        return profilephoto;
    }

    public void setProfilephoto(String profilephoto) {
        this.profilephoto = profilephoto;
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
        return "UserJson{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", profilephoto='" + profilephoto + '\'' +
                ", email='" + email + '\'' +
                ", region='" + region + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday='" + birthday + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}
