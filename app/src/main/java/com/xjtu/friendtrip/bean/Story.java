package com.xjtu.friendtrip.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Meshine on 16/5/27.
 */
public class Story implements Serializable {

    public static final int AUTH_WORLD = 0;
    public static final int AUTH_FRIENDS = 1;
    public static final int AUTH_SELF = 2;


    private List<Image> travlenotespictures;
    private List<Comment> commentses;
    private String location;
    private List<Star> starses;
    private Integer userId;
    private Integer travelNotesid;
    private Integer starCount;
    private String discription;
    private Double latitude;
    private Double longitude;
    private Integer commentCount;
    private String datetime;
    private Integer rightType;
    private Integer scanCount;

    public Story() {
    }

    public Story(List<Image> travlenotespictures, List<Comment> commentses, String location, List<Star> starses, Integer userId, Integer travelNotesid, Integer starCount, String discription, Double latitude, Double longitude, Integer commentCount, String datetime,Integer rightType,Integer scanCount) {
        this.travlenotespictures = travlenotespictures;
        this.commentses = commentses;
        this.location = location;
        this.starses = starses;
        this.userId = userId;
        this.travelNotesid = travelNotesid;
        this.starCount = starCount;
        this.discription = discription;
        this.latitude = latitude;
        this.longitude = longitude;
        this.commentCount = commentCount;
        this.datetime = datetime;
        this.rightType = rightType;
        this.scanCount = scanCount;
    }

    public List<Image> getTravlenotespictures() {
        return travlenotespictures;
    }

    public void setTravlenotespictures(List<Image> travlenotespictures) {
        this.travlenotespictures = travlenotespictures;
    }

    public Integer getScanCount() {
        return scanCount;
    }

    public void setScanCount(Integer scanCount) {
        this.scanCount = scanCount;
    }

    public Integer getRightType() {
        return rightType;
    }

    public void setRightType(Integer rightType) {
        this.rightType = rightType;
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

    public List<Star> getStarses() {
        return starses;
    }

    public void setStarses(List<Star> starses) {
        this.starses = starses;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTravelNotesid() {
        return travelNotesid;
    }

    public void setTravelNotesid(Integer travelNotesid) {
        this.travelNotesid = travelNotesid;
    }

    public Integer getStarCount() {
        return starCount;
    }

    public void setStarCount(Integer starCount) {
        this.starCount = starCount;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
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

    @Override
    public String toString() {
        return "Story{" +
                "travlenotespictures=" + travlenotespictures +
                ", commentses=" + commentses +
                ", location='" + location + '\'' +
                ", starses=" + starses +
                ", userId=" + userId +
                ", travelNotesid=" + travelNotesid +
                ", starCount=" + starCount +
                ", discription='" + discription + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", commentCount=" + commentCount +
                ", datetime='" + datetime + '\'' +
                ", rightType=" + rightType +
                ", scanCount=" + scanCount +
                '}';
    }
}
