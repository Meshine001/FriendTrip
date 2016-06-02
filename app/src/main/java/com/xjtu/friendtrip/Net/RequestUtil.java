package com.xjtu.friendtrip.Net;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Meshine on 16/6/2.
 */
public class RequestUtil {
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
