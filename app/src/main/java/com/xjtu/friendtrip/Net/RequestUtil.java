package com.xjtu.friendtrip.Net;

import com.baidu.platform.comapi.map.L;
import com.xjtu.friendtrip.bean.Comment;
import com.xjtu.friendtrip.bean.Discovery;
import com.xjtu.friendtrip.bean.Image;
import com.xjtu.friendtrip.bean.Star;
import com.xjtu.friendtrip.bean.User;
import com.xjtu.friendtrip.util.JSONUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meshine on 16/6/2.
 */
public class RequestUtil {

    public static List<Discovery> requestToDiscoveries(String result){
        try {
            List<Discovery> discoveries = new ArrayList<>();
            JSONArray ja = new JSONArray(result);
            for (int i=0;i<ja.length();i++){
                JSONObject jo = ja.getJSONObject(i);
                Discovery discovery = new Discovery();

                List<Image> pictures = new ArrayList<>();
                JSONArray imgs =  jo.getJSONArray("picture");
                for (int j=0;j<imgs.length();j++){
                    JSONObject imgJo = imgs.getJSONObject(j);
                    Image img = new Image(imgJo.getString("url"),imgJo.getString("summary"));
                    pictures.add(img);
                }
                discovery.setPictures(pictures);

                discovery.setSummary(jo.getString("summary"));

                List<Comment> commentses = new ArrayList<>();
                JSONArray comments = jo.getJSONArray("commentses");
                for (int j=0;j<comments.length();j++){
                    JSONObject cJo = comments.getJSONObject(j);
                    Comment c = new Comment(
                            cJo.getInt("commentId"),
                            cJo.getString("content"),
                            cJo.getInt("fromUserId"),
                            cJo.getInt("toUserId"),
                            cJo.getString("datetime")
                    );
                    commentses.add(c);
                }

                discovery.setLocation(jo.getString("location"));
                discovery.setUserid(jo.getInt("userid"));
                discovery.setStarCount(jo.getInt("starCount"));
                discovery.setUserName(jo.getString("userName"));
                discovery.setLongitude(jo.getDouble("longitude"));
                discovery.setLatitude(jo.getDouble("latitude"));
                discovery.setScenicid(jo.getInt("scenicid"));
                discovery.setCommentCount(jo.getInt("commentCount"));
                discovery.setDatetime(jo.getString("datetime"));
                discovery.setScenicname(jo.getString("scenicname"));

                List<Star> starses = new ArrayList<>();
                JSONArray stars = jo.getJSONArray("starses");
                for (int j=0;j<stars.length();j++){
                    JSONObject sJ = stars.getJSONObject(j);
                    Star s = new Star(
                            sJ.getInt("id"),
                            sJ.getString("name"),
                            sJ.getString("nickname"),
                            sJ.getInt("focusCount"),
                            sJ.getInt("isFocusCount"),
                            sJ.getString("profilePhoto")
                    );
                    starses.add(s);
                }
            }
            return discoveries;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

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
