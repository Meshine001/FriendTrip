package com.xjtu.friendtrip.Net;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.xjtu.friendtrip.bean.Comment;
import com.xjtu.friendtrip.bean.Discovery;
import com.xjtu.friendtrip.bean.Image;
import com.xjtu.friendtrip.bean.Star;
import com.xjtu.friendtrip.bean.Story;
import com.xjtu.friendtrip.bean.User;
import com.xjtu.friendtrip.util.JSONUtil;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meshine on 16/6/2.
 */
public class RequestUtil {

    private static final String TAG = RequestUtil.class.getName();


    public static List<Story> requestToStories(String result) {
        List<Story> stories = new ArrayList<>();
        if (result == null) return stories;
        try {
            JSONArray ja = JSON.parseArray(result);
            for (int i = 0; i < ja.size(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                Story story = new Story();
                /**
                 * travlenotespictures
                 */
                List<Image> travlenotespictures = new ArrayList<>();
                JSONArray imgs = jo.getJSONArray("travlenotespictures");
                for (int j = 0; j < imgs.size(); j++) {
                    JSONObject imgJo = imgs.getJSONObject(j);
                    Image img = new Image(imgJo.getString("url"), imgJo.getString("summary"));
                    travlenotespictures.add(img);
                }
                story.setTravlenotespictures(travlenotespictures);

                /**
                 * commentses
                 */
                List<Comment> commentses = new ArrayList<>();
                JSONArray comments = jo.getJSONArray("commentses");
                for (int j = 0; j < comments.size(); j++) {
                    JSONObject cJo = comments.getJSONObject(j);
                    Comment c = new Comment(
                            cJo.getInteger("commentId"),
                            cJo.getString("content"),
                            cJo.getInteger("fromUserId"),
                            cJo.getInteger("toUserId"),
                            cJo.getString("datetime")
                    );
                    commentses.add(c);
                }
                story.setCommentses(commentses);

                /**
                 * location
                 */
                story.setLocation(jo.getString("location"));
                /**
                 * userId
                 */
                story.setUserId(jo.getInteger("userId"));
                /**
                 * travelNotesid
                 */
                story.setTravelNotesid(jo.getInteger("travelNotesid"));
                /**
                 * starCount
                 */
                story.setStarCount(jo.getInteger("starCount"));
                /**
                 * discription
                 */
                story.setDiscription(jo.getString("discription"));
                /**
                 * latitude
                 */
                story.setLatitude(jo.getDouble("latitude"));
                /**
                 * longitude
                 */
                story.setLongitude(jo.getDouble("longitude"));
                /**
                 * commentCount
                 */
                story.setCommentCount(jo.getInteger("commentCount"));
                /**
                 * datetime
                 */
                story.setDatetime(jo.getString("datetime"));
                stories.add(story);
            }
            return stories;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stories;
    }


    public static List<Discovery> requestToDiscoveries(String result) {
        List<Discovery> discoveries = new ArrayList<>();
        if (result == null) return discoveries;
        try {
            JSONArray ja = JSON.parseArray(result);
            for (int i = 0; i < ja.size(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                Discovery discovery = new Discovery();

                /**
                 * travlenotespictures
                 */
                List<Image> travlenotespictures = new ArrayList<>();
                JSONArray imgs = jo.getJSONArray("travlenotespictures");
                for (int j = 0; j < imgs.size(); j++) {
                    JSONObject imgJo = imgs.getJSONObject(j);
                    Image img = new Image(imgJo.getString("url"), imgJo.getString("summary"));
                    travlenotespictures.add(img);
                }
                discovery.setTravlenotespictures(travlenotespictures);

                /**
                 * summary
                 */
                discovery.setSummary(jo.getString("summary"));

                /**
                 * commentses
                 */
                List<Comment> commentses = new ArrayList<>();
                JSONArray comments = jo.getJSONArray("commentses");
                for (int j = 0; j < comments.size(); j++) {
                    JSONObject cJo = comments.getJSONObject(j);
                    Integer toUserId;
                    try {
                        toUserId = cJo.getInteger("toUserId");
                    } catch (Exception e) {
                        e.printStackTrace();
                        toUserId = -1;
                    }
                    Comment c = new Comment(
                            cJo.getInteger("commentId"),
                            cJo.getString("content"),
                            cJo.getInteger("fromUserId"),
                            toUserId
                            ,
                            cJo.getString("datetime")
                    );
                    commentses.add(c);
                }
                discovery.setCommentses(commentses);

                /**
                 *
                 */
                discovery.setLocation(jo.getString("location"));
                discovery.setUserid(jo.getInteger("userid"));
                discovery.setStarCount(jo.getInteger("starCount"));
                discovery.setUserName(jo.getString("userName"));
                discovery.setLongitude(jo.getDouble("longitude"));
                discovery.setLatitude(jo.getDouble("latitude"));
                discovery.setScenicid(jo.getInteger("scenicid"));
                discovery.setCommentCount(jo.getInteger("commentCount"));
                discovery.setDatetime(jo.getString("datetime"));
                discovery.setScenicname(jo.getString("scenicname"));

                /**
                 * starses
                 */
                List<Star> starses = new ArrayList<>();
                JSONArray stars = jo.getJSONArray("starses");
                for (int j = 0; j < stars.size(); j++) {
                    JSONObject sJ = stars.getJSONObject(j);
                    Star s = new Star(
                            sJ.getInteger("id"),
                            sJ.getString("name"),
                            sJ.getString("nickname"),
                            sJ.getInteger("focusCount"),
                            sJ.getInteger("isFocusCount"),
                            sJ.getString("profilePhoto")
                    );
                    starses.add(s);
                }
                discovery.setStarses(starses);

                discoveries.add(discovery);
            }
            return discoveries;
        } catch (Exception e) {
            Log.i(TAG, e.toString());
        }
        return discoveries;
    }

    public static User requestToUser(String result) {
        if (result == null) return null;
            JSONObject jo = JSON.parseObject(result);
            Integer id = jo.getInteger("id");
            String name = jo.getString("name");
            Integer focusCount = jo.getInteger("focusCount");
        if (focusCount == null)focusCount = 0;
            Integer isFocusCount = jo.getInteger("isFocusCount");
        if (isFocusCount == null)isFocusCount = 0;
         String profilePhoto = jo.getString("profilePhoto");
        String nickname = jo.getString("nickname");
            User u = new User(
                    id,
                    name,
                    focusCount,
                    isFocusCount,
                    profilePhoto ,
                    nickname
            );
            return u;

    }

    public static boolean isRequestSuccess(String result) {
        if (result == null) return false;
        try {
            JSONObject jo = JSON.parseObject(result);
            if (1 == jo.getInteger("code")) {
                return true;
            } else {
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String handRequestErr(String result) {
        if (result == null) return "请求无数据";
        try {
            JSONObject jo = JSON.parseObject(result);
            return jo.getString("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
