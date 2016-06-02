package com.xjtu.friendtrip.Net;

import com.xjtu.friendtrip.bean.User;
import com.xjtu.friendtrip.util.JSONUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Meshine on 16/6/2.
 */
public class RequestUtil {


    public static User requestToUser(String result){
        try {
            JSONObject jo = new JSONObject(result);
            User u = new User(
                    JSONUtil.getInt(jo,"id"),
                    JSONUtil.getString(jo,"name"),
                    JSONUtil.getString(jo,"tagId"),
                    JSONUtil.getString(jo,"password"),
                    JSONUtil.getInt(jo,"focusCount"),
                    JSONUtil.getInt(jo,"isFocusCount"),
                    JSONUtil.getInt(jo,"friendsCount"),
                    JSONUtil.getString(jo,"profilePhoto"),
                    JSONUtil.getString(jo,"nickname")
            );
            return u;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isRequestSuccess(String result){
        try {
            JSONObject jo = new JSONObject(result);
            if (1 == jo.getInt("code")){
                return true;
            }
            else{
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }
}
