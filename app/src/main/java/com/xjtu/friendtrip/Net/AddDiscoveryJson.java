package com.xjtu.friendtrip.Net;

import java.io.Serializable;

/**
 * Created by Meshine on 16/6/2.
 */
public class AddDiscoveryJson implements Serializable{
    /**
     * {
     "scenicname":"小楼",
     "summary":"小楼的确很美啊！",
     "userid":"1203",
     "longitude":"34.2131314",
     "latitude":"104.21121",
     "picture":"http://7xui79.com1.z0.glb.clouddn.com/QQ%E6%88%AA%E5%9B%BE20160524092514.jpg,http://7xui79.com1.z0.glb.clouddn.com/QQ%E6%88%AA%E5%9B%BE20160524092848.jpg",
     "location":"灞桥啊"
     }
     */

    private String scenicname;
    private String summary;
    private int userid;
    private Double  longitude;
    private Double  latitude;
    private String picture;
    private String location;

    public String getScenicname() {
        return scenicname;
    }

    public void setScenicname(String scenicname) {
        this.scenicname = scenicname;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public AddDiscoveryJson(String scenicname, String summary, int userid, Double longitude, Double latitude, String picture, String location) {

        this.scenicname = scenicname;
        this.summary = summary;
        this.userid = userid;
        this.longitude = longitude;
        this.latitude = latitude;
        this.picture = picture;
        this.location = location;
    }
}
