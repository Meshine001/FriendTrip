package com.xjtu.friendtrip.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ming on 2016/6/5.
 */
public class Discovery implements Serializable {
    private List<Image> pictures;
    private String summary;
    private List<Comment> commentses;
    private String location;
    private Integer userid;
    private Integer starCount;
    private String userName;
    private Double longitude;
    private Double latitude;
    private Integer scenicid;
    private Integer commentCount;
    private String datetime;
    private String scenicname;
    private List<Star> starses;

    public Discovery() {
    }

    public Discovery(List<Image> pictures, String summary, List<Comment> commentses, String location, Integer userid, Integer starCount, String userName, Double longitude, Double latitude, Integer scenicid, Integer commentCount, String datetime, String scenicname, List<Star> starses) {
        this.pictures = pictures;
        this.summary = summary;
        this.commentses = commentses;
        this.location = location;
        this.userid = userid;
        this.starCount = starCount;
        this.userName = userName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.scenicid = scenicid;
        this.commentCount = commentCount;
        this.datetime = datetime;
        this.scenicname = scenicname;
        this.starses = starses;
    }

    public List<Image> getPictures() {
        return pictures;
    }

    public void setPictures(List<Image> pictures) {
        this.pictures = pictures;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<Comment> getCommentses() {
        return commentses;
    }

    public void setCommentses(List<Comment> commentses) {
        this.commentses = commentses;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getStarCount() {
        return starCount;
    }

    public void setStarCount(Integer starCount) {
        this.starCount = starCount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Integer getScenicid() {
        return scenicid;
    }

    public void setScenicid(Integer scenicid) {
        this.scenicid = scenicid;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getScenicname() {
        return scenicname;
    }

    public void setScenicname(String scenicname) {
        this.scenicname = scenicname;
    }

    public List<Star> getStarses() {
        return starses;
    }

    public void setStarses(List<Star> starses) {
        this.starses = starses;
    }

    @Override
    public String toString() {
        return "Discovery{" +
                "pictures=" + pictures +
                ", summary='" + summary + '\'' +
                ", commentses=" + commentses +
                ", location='" + location + '\'' +
                ", userid=" + userid +
                ", starCount=" + starCount +
                ", userName='" + userName + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", scenicid=" + scenicid +
                ", commentCount=" + commentCount +
                ", datetime='" + datetime + '\'' +
                ", scenicname='" + scenicname + '\'' +
                ", starses=" + starses +
                '}';
    }
}
