package com.xjtu.friendtrip.bean;

/**
 * Created by Meshine on 16/5/27.
 */
public class Image {
    private String imagePath;
    private String summary;

    public Image() {
    }

    public Image(String imagePath) {
        this.imagePath = imagePath;
    }

    public Image(String imagePath, String summary) {
        this.imagePath = imagePath;
        this.summary = summary;
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

}
