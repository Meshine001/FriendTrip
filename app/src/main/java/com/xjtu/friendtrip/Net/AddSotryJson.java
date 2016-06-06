package com.xjtu.friendtrip.Net;

import com.xjtu.friendtrip.bean.StoryFile;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Meshine on 16/6/6.
 */
public class AddSotryJson implements Serializable {
    private String discription;
    private String location;
    private String datetime;
    private Integer userid;
    private Double latitude;
    private Double longitude;
    private List<StoryFile> travlenotespictures;

    public AddSotryJson() {
    }

    public AddSotryJson(String discription, String location, String datetime, Integer userid, Double latitude, Double longitude, List<StoryFile> travlenotespictures) {
        this.discription = discription;
        this.location = location;
        this.datetime = datetime;
        this.userid = userid;
        this.latitude = latitude;
        this.longitude = longitude;
        this.travlenotespictures = travlenotespictures;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
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

    public List<StoryFile> getTravlenotespictures() {
        return travlenotespictures;
    }

    public void setTravlenotespictures(List<StoryFile> travlenotespictures) {
        this.travlenotespictures = travlenotespictures;
    }

    @Override
    public String toString() {
        return "AddSotryJson{" +
                "discription='" + discription + '\'' +
                ", location='" + location + '\'' +
                ", datetime='" + datetime + '\'' +
                ", userid=" + userid +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", travlenotespictures=" + travlenotespictures +
                '}';
    }
}
