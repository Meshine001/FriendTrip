package com.xjtu.friendtrip.Net;

import com.xjtu.friendtrip.util.PrefUtils;

/**
 * Created by Meshine on 16/6/2.
 */
public class Config {
    public static final String BASE_URL = "http://192.168.0.146:8080/friendTrip";

    /**
     * Login and regist
     */
    public static final String CHECK_EXSIT = BASE_URL +"/user";//!!!
    public static final String REGIST = BASE_URL+"/user/register";
    public static final String LOGIN = BASE_URL+"/user/login";

    /**
     * UserInfo
     */
     public static final String USER_INFO = BASE_URL + "/user/getInfo/";

    /**
     * Follow TA
     */
     public static final String FOLLOW_TA = BASE_URL + "";

    /**
     * Home tab
     */
    /**
     *  按点赞量  /newscenicspots/{offset}/{limit}/findTopStarSpots
     *  Ex. offset=0 , limit = 3; offset = 4 ,limit = 3
     */
    public static final String FIND_TOP_STAR_SPOTS = "/findTopStarSpots";
    public static final String REQUEST_TOP_LIKE_DISCOVERIES = BASE_URL+"/newscenicspots/";
    /**
     * 按评论量 /newscenicspots/{offset}/{limit}/findTopCommentSpots
     */
    public static final String FIND_TOP_COMMENT_SPOTS = "/findTopCommentSpots";
    public static final String REQUEST_TOP_COMMENT_DISCOVERIES = BASE_URL+"/newscenicspots/";

    /**
     * 主页 好友
     */
    public static final String FIND_TOP_STAR_NOTES = "/findTopStarNotes";
    public static final String REQUEST_TOP_LIKE_FRIENDS = BASE_URL + "/travelnotes/";

    /**
     * 点赞
     */
    public static final String REQUEST_STAR= BASE_URL + "/commentOrStar/star";

    /**
     * 评论
     */
    public static final String REQUEST_COMMENT = BASE_URL + "/commentOrStar/comment";

    /**
     * 周边好友心情,游记
     */
    public static final String REQUEST_FRIENDS_NOTES_BY_LOCATION = BASE_URL+"/travelnotes/getMyFriendsNotesByLocation";

    /**
     * Discovery
     */
    public static final String ADD_DISCOVERY = BASE_URL +"/newscenicspots/addSpots";

    /**
     * 我的心情集
     */
    public static final String GET_NOTES_BY_USER_ID = "/getNotesByUserId";
    public static final String REQUEST_STORIES_BY_USER = BASE_URL + "/travelnotes/";

//    /**
//     * 我的心情集
//     */
//    public static final String GET_NOTES_BY_USER_ID = "/getNotesByUserId";
//    public static final String REQUEST_STORIES_BY_USER = BASE_URL + "/travelnotes/";

    public static final String ME_STORIES_BG_URL = "http://pic29.nipic.com/20130523/9908282_160306298113_2.jpg";
    public static final String ME_TRACES_BG_URL = "http://e.hiphotos.baidu.com/zhidao/pic/item/0b7b02087bf40ad1b660bcb9552c11dfa8eccee0.jpg";

}
