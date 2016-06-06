package com.xjtu.friendtrip.bean;

import java.io.Serializable;

/**
 * Created by Meshine on 16/5/27.
 */
public class StoryFile implements Serializable{
    public static final int TYPE_IMEG = 0;
    public static final int TYPE_RECORD = 1;
    public static final int TYPE_VIDEO = 2;
    public static final int TYPE_TEXT = 2;


    private String fileUrl;
    private String summary;
    private Integer type;

    public StoryFile() {
    }

    public StoryFile(String fileUrl, String summary, Integer type) {
        this.fileUrl = fileUrl;
        this.summary = summary;
        this.type = type;
    }

    public static int getTypeImeg() {
        return TYPE_IMEG;
    }

    public static int getTypeRecord() {
        return TYPE_RECORD;
    }

    public static int getTypeVideo() {
        return TYPE_VIDEO;
    }

    public static int getTypeText() {
        return TYPE_TEXT;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
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
                "fileUrl='" + fileUrl + '\'' +
                ", summary='" + summary + '\'' +
                ", type=" + type +
                '}';
    }
}
