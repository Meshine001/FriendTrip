package com.xjtu.friendtrip.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;

/**
 * Created by Meshine on 16/5/27.
 */
public class Image implements Serializable{
    private String imagePath;
    private String summary;
    private String url;

    public Image() {
    }

    public Image(String imagePath) {
        this.imagePath = imagePath;
    }

    public Image(String imagePath, String summary) {
        this.imagePath = imagePath;
        this.summary = summary;
    }

    public Image(String imagePath, String summary, String url) {
        this.imagePath = imagePath;
        this.summary = summary;
        this.url = url;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Image{" +
                "imagePath='" + imagePath + '\'' +
                ", summary='" + summary + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
