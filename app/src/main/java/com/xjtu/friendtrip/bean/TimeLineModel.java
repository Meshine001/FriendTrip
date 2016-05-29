package com.xjtu.friendtrip.bean;

import java.io.Serializable;

/**
 * Created by Meshine on 16/5/29.
 */
public class TimeLineModel implements Serializable{

   private String textContent;
    private String time;
    private String location;

    public TimeLineModel() {
    }

    public TimeLineModel(String location, String textContent, String time) {
        this.location = location;
        this.textContent = textContent;
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
