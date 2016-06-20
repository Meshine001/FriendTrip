package com.xjtu.friendtrip.Net;

import com.xjtu.friendtrip.bean.Story;
import com.xjtu.friendtrip.util.PrefUtils;

/**
 * Created by Meshine on 16/6/2.
 */
public class Config {
//    public static final String BASE_URL = "http://192.168.0.146:8080/friendTrip";
//    public static final String BASE_URL = "http://192.168.1.101:8080/friendTrip";
//    public static final String BASE_URL = "http://192.168.0.105:8080/friendTrip";
    public static final String BASE_URL = "http://192.168.0.134:8080/friendTrip";



    /**
     * 用户相关
     */
    //检查账户是否存在
    public static final String CHECK_EXSIT = BASE_URL +"/user";//!!!
    //注册
    public static final String REGIST = BASE_URL+"/user/register";
    //登录
    public static final String LOGIN = BASE_URL+"/user/login";
    //获取个人信息
    public static final String USER_INFO = BASE_URL + "/user/getInfo/";
    //更新个人资料
    public static final String UPDATE_USER_INFO = BASE_URL + "/user/update";

    /**
     * 心情
     */
    public static final String STORY = "/travelnotes";
    //添加新心情
    public static final String ADD_NEW_STORY = BASE_URL + STORY + "/addNotes";
    //获取心情详情
    public static final String GET_NOTES_BY_ID = "/getNotesById";
    public static final String REQUEST_STORY_DETAILS_BY_ID = BASE_URL+STORY+"/";
    //获取心情集
    public static final String GET_NOTES_BY_USER_ID = "/getNotesByUserId";
    public static final String REQUEST_STORIES_BY_USER = BASE_URL+STORY+"/";
    //删除心情
    public static final String REQUEST_DELETE_STOYR = BASE_URL+STORY+"/deleteNotes/";
    //获取心情列表,按点赞排
    public static final String FIND_TOP_STAR_NOTES = "/findTopStarNotes";
    public static final String REQUEST_TOP_STORIES = BASE_URL + STORY+"/";

    /**
     * 印记
     */
    public static final String TRACE = "/detailnotes";
    public static final String ADD_NEW_TRACE = BASE_URL + TRACE + "/addDetailNotes";
    public static final String GET_DETAILS_NOTES_BY_USER_ID = "/getDetailNotesByUserId";
    public static final String REQUEST_TRACES_BY_USER = BASE_URL+TRACE+"/";

    /**
     * 关注
     */
    //关注用户
     public static final String FOLLOW_TA = BASE_URL + "/relation/focus";
    //关注我的用户
     public static final String MY_FOCUS = "/myFocus";
     public static final String REQUEST_MY_FOLLOWS = BASE_URL+"/relation";

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
     * 通过 新发现id获取新发现详细信息
     */
    public static final String GET_SPOTS_BY_ID = "/getSpotsById";
    public static final String REQUEST_GET_SPOTS_BY_ID = BASE_URL + "/newscenicspots/";

    /**
     * 通过 新发现id获取心情详细信息
     */
    public static final String GET_STORY_BY_ID = "/getNotesById";
    public static final String REQUEST_GET_STORY_BY_ID = BASE_URL + "/travelnotes/";

    /**
     * 主页 好友
     */


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


//    /**
//     * 我的心情集
//     */
//    public static final String GET_NOTES_BY_USER_ID = "/getNotesByUserId";
//    public static final String REQUEST_STORIES_BY_USER = BASE_URL + "/travelnotes/";

    public static final String ME_STORIES_BG_URL = "http://pic29.nipic.com/20130523/9908282_160306298113_2.jpg";
    public static final String ME_TRACES_BG_URL = "http://e.hiphotos.baidu.com/zhidao/pic/item/0b7b02087bf40ad1b660bcb9552c11dfa8eccee0.jpg";


    /**
     * 获取万象优图上传照片的权限
     */
    //TODO
//    public static final String QPicAuth = "http://192.168.0.105:8888/qcloud/qPicAuth";
    public static final String QPicAuth = BASE_URL + "/auth/qPicAuth";

}
