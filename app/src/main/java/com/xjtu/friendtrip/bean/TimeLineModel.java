package com.xjtu.friendtrip.bean;

import java.io.Serializable;

/**
 * Created by Meshine on 16/5/29.
 */
public class TimeLineModel implements Serializable{
    public static final int TYPE_IMAGE = 0;
    public static final int TYPE_RECORD = 1;
    public static final int TYPE_VIDEO = 2;
    public static final int TYPE_TEXT = 3;


    private Object content;
    private String time;
    private CustomLocation location;
    private int type;

    public TimeLineModel() {
    }

    public TimeLineModel(Object content, String time, CustomLocation location, int type) {
        this.content = content;
        this.time = time;
        this.location = location;
        this.type = type;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public CustomLocation getLocation() {
        return location;
    }

    public void setLocation(CustomLocation location) {
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


}
