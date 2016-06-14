package com.xjtu.friendtrip.bean;

import java.io.Serializable;

/**
 * Created by Meshine on 16/5/27.
 */
public class StoryFile implements Serializable{
    public static final int TYPE_IMAGE = 0;
    public static final int TYPE_RECORD = 1;
    public static final int TYPE_VIDEO = 2;
    public static final int TYPE_TEXT = 3;


    private String url;
    private String summary;
    private Integer type;
    private Double latitude;
    private Double longitude;
    private String datetime;
    private Integer position;

    public StoryFile() {
    }

    public StoryFile(String url, String summary, Integer type) {
        this.url = url;
        this.summary = summary;
        this.type = type;
    }

    public StoryFile(String url, String summary, Integer type, Double latitude, Double longitude, String datetime,Integer position) {
        this.url = url;
        this.summary = summary;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.datetime = datetime;
        this.position = position;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "StoryFile{" +
                "url='" + url + '\'' +
                ", summary='" + summary + '\'' +
                ", type=" + type +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", datetime='" + datetime + '\'' +
                ", position=" + position +
                '}';
    }
}
