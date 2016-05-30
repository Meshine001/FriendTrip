package com.xjtu.friendtrip.bean;

import java.io.Serializable;

/**
 * Created by Meshine on 16/5/29.
 */
public class TimeLineModel implements Serializable{
    public static final int TYPE_TEXT = 0;
    public static final int TYPE_RECORD = 1;
    public static final int TYPE_IMAGE= 2;

    private Object content;
    private String time;
    private String location;
    private int type;

    public TimeLineModel() {
    }

    public TimeLineModel(Object content, String location, String time, int type) {
        this.content = content;
        this.location = location;
        this.time = time;
        this.type = type;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TimeLineModel{" +
                "content=" + content +
                ", time='" + time + '\'' +
                ", location='" + location + '\'' +
                ", type=" + type +
                '}';
    }
}
