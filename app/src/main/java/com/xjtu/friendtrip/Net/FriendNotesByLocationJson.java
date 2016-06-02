package com.xjtu.friendtrip.Net;

import java.io.Serializable;

/**
 * Created by Meshine on 16/6/2.
 */
public class FriendNotesByLocationJson implements Serializable {
    private Double longitude;
    private Double latitude;
    private int userid;
    private int count;

    public FriendNotesByLocationJson(Double longitude, Double latitude, int userid, int count) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.userid = userid;
        this.count = count;
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

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
