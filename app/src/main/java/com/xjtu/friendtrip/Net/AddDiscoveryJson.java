package com.xjtu.friendtrip.Net;

import com.xjtu.friendtrip.bean.Image;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Meshine on 16/6/2.
 */
public class AddDiscoveryJson implements Serializable{


    private String scenicname;
    private String summary;
    private int userid;
    private Double  longitude;
    private Double  latitude;
    private List<Image>travlenotespictures;
    private String location;
    private String datetime;

    public AddDiscoveryJson() {
    }

    public AddDiscoveryJson(String scenicname, String summary, int userid,Double latitude, Double longitude,  List<Image> travlenotespictures, String location,String datetime) {
        this.scenicname = scenicname;
        this.summary = summary;
        this.userid = userid;
        this.longitude = longitude;
        this.latitude = latitude;
        this.travlenotespictures = travlenotespictures;
        this.location = location;
        this.datetime = datetime;
    }

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

    public List<Image> getTravlenotespictures() {
        return travlenotespictures;
    }

    public void setTravlenotespictures(List<Image> travlenotespictures) {
        this.travlenotespictures = travlenotespictures;
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

    @Override
    public String toString() {
        return "AddDiscoveryJson{" +
                "scenicname='" + scenicname + '\'' +
                ", summary='" + summary + '\'' +
                ", userid=" + userid +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", travlenotespictures=" + travlenotespictures +
                ", location='" + location + '\'' +
                ", datetime='" + datetime + '\'' +
                '}';
    }
}
