package com.xjtu.friendtrip.bean;

/**
 * Created by Meshine on 16/5/25.
 */
public class Cover {
    private String coverUrl;
    private String summary;
    private String avatarUrl;
    private String nick;
    private String location;

    public Cover() {
    }

    public Cover(String coverUrl, String summary, String avatarUrl, String nick, String location) {
        this.coverUrl = coverUrl;
        this.summary = summary;
        this.avatarUrl = avatarUrl;
        this.nick = nick;
        this.location = location;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Cover{" +
                "coverUrl='" + coverUrl + '\'' +
                ", summary='" + summary + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", nick='" + nick + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
