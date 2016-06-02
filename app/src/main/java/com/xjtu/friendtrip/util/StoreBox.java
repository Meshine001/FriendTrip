package com.xjtu.friendtrip.util;

import android.content.Context;

import com.xjtu.friendtrip.bean.User;

/**
 * Created by Meshine on 16/5/31.
 */
public class StoreBox {

    public static void saveUserInfo(Context context,User user){
        PrefUtils.setIntegerPreference(context,"id",user.getId());
        PrefUtils.setStringPreference(context,"username",user.getName());
        PrefUtils.setStringPreference(context,"tagId",user.getTagId());
        PrefUtils.setStringPreference(context,"password",user.getPassword());
        PrefUtils.setIntegerPreference(context,"focusCount",user.getFocusCount());
        PrefUtils.setIntegerPreference(context,"isFocusCount",user.getIsFocusCount());
        PrefUtils.setIntegerPreference(context,"friendsCount",user.getFriendsCount());
        PrefUtils.setStringPreference(context,"profilePhoto",user.getProfilePhoto());
        PrefUtils.setStringPreference(context,"nickname",user.getNickname());
    }

    public static User getUserInfo(Context context){
        User u  = new User(
                PrefUtils.getIntegerPreference(context,"id",0),
                PrefUtils.getStringPreference(context,"username"),
                PrefUtils.getStringPreference(context,"tagId"),
                PrefUtils.getStringPreference(context,"password"),
                PrefUtils.getIntegerPreference(context,"focusCount",0),
                PrefUtils.getIntegerPreference(context,"isFocusCount",0),
                PrefUtils.getIntegerPreference(context,"friendsCount",0),
                PrefUtils.getStringPreference(context,"profilePhoto"),
                PrefUtils.getStringPreference(context,"nickname"));
        return u;

    }

    public static void setUserNameAndPsd(Context context, String username, String password){
        PrefUtils.setStringPreference(context,"username",username);
        PrefUtils.setStringPreference(context,"password",password);
    }

    public static void setUserNick(Context context, String nick){
        PrefUtils.setStringPreference(context,"nick",nick);
    }



    public static void clearUserInfo(Context context){
        saveUserInfo(context,new User(null,null,null,null,null,null,null,null,null));
    }
}
