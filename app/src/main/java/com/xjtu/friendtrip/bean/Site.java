package com.xjtu.friendtrip.bean;

/**
 * Created by Meshine on 16/5/25.
 * 景点
 */
public class Site {
    private String coverUrl;
    private String name;
    private String summary;

    public Site(String coverUrl, String name, String summary) {
        this.coverUrl = coverUrl;
        this.name = name;
        this.summary = summary;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
