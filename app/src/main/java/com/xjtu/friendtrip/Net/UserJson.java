package com.xjtu.friendtrip.Net;

import java.io.Serializable;

/**
 * Created by Meshine on 16/6/12.
 */
public class UserJson implements Serializable {
    private Integer id;
    private String name;
    private String password;
    private String profilephoto;
    private String nickname;

    public UserJson() {
    }

    public UserJson(Integer id, String name, String password, String profilephoto, String nickname) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.profilephoto = profilephoto;
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

    public String getProfilephoto() {
        return profilephoto;
    }

    public void setProfilephoto(String profilephoto) {
        this.profilephoto = profilephoto;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "UserJson{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", profilephoto='" + profilephoto + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
