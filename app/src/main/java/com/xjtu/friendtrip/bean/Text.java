package com.xjtu.friendtrip.bean;

import java.io.Serializable;

/**
 * Created by Ming on 2016/5/30.
 */
public class Text implements Serializable{
    private String textContent;

    public Text(String textContent) {
        this.textContent = textContent;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    @Override
    public String toString() {
        return "Text{" +
                "textContent='" + textContent + '\'' +
                '}';
    }
}
