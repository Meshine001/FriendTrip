package com.xjtu.friendtrip.Net;

import java.io.Serializable;

/**
 * Created by Meshine on 16/6/2.
 */
public class StarJson implements Serializable{
    /**
     * {
     "userId":1203,
     "type":1,    // 0 1 2 0是新景点  1 是心情 2 是游记
     "travelnotesId":1 代表上面三个的ID
     }
     */
    private int userId;
    private int type;
    private int id;

    public StarJson(int userId, int type, int id) {
        this.userId = userId;
        this.type = type;
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
