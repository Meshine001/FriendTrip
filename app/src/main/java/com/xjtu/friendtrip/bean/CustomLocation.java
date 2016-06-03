package com.xjtu.friendtrip.bean;

import com.baidu.mapapi.model.LatLng;

import java.io.Serializable;

/**
 * Created by Meshine on 16/6/3.
 */
public class CustomLocation implements Serializable {
    private String name;
    private Double lat;
    private Double lon;

    public CustomLocation() {
    }

    public CustomLocation(String name, Double lat, Double lon) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "CustomLocation{" +
                "name='" + name + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}
