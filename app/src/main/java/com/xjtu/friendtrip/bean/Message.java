package com.xjtu.friendtrip.bean;

import java.io.Serializable;

/**
 * Created by Meshine on 16/6/3.
 */
public class Message implements Serializable {
    private String avatar;
    private String tile;
    private String conent;
    private String time;

    public Message() {

    }

    public Message(String tile) {
        this.tile = tile;
    }

    public Message(String tile, String avatar) {
        this.tile = tile;
        this.avatar = avatar;
    }

    public Message(String avatar, String tile, String conent, String time) {
        this.avatar = avatar;
        this.tile = tile;
        this.conent = conent;
        this.time = time;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTile() {
        return tile;
    }

    public void setTile(String tile) {
        this.tile = tile;
    }

    public String getConent() {
        return conent;
    }

    public void setConent(String conent) {
        this.conent = conent;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Message{" +
                "avatar='" + avatar + '\'' +
                ", tile='" + tile + '\'' +
                ", conent='" + conent + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
