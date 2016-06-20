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
        PrefUtils.setIntegerPreference(context,"focusCount",user.getFocusCount());
        PrefUtils.setIntegerPreference(context,"isFocusCount",user.getIsFocusCount());
        PrefUtils.setStringPreference(context,"profilePhoto",user.getProfilePhoto());
        PrefUtils.setStringPreference(context,"nickname",user.getNickname());
        PrefUtils.setStringPreference(context,"email",user.getEmail());
        PrefUtils.setStringPreference(context,"region",user.getRegion());
        PrefUtils.setStringPreference(context,"gender",user.getGender());
        PrefUtils.setStringPreference(context,"birthday",user.getBirthday());
        PrefUtils.setStringPreference(context,"signature",user.getSignature());

    }

    public static boolean isSomeOneHere(Context context){
        String username = PrefUtils.getStringPreference(context,"username");
        if (username != null)return true;
        return false;
    }

    public static User getUserInfo(Context context){
        User u  = new User(
                PrefUtils.getIntegerPreference(context,"id",0),
                PrefUtils.getStringPreference(context,"username"),
                PrefUtils.getIntegerPreference(context,"focusCount",0),
                PrefUtils.getIntegerPreference(context,"isFocusCount",0),
                PrefUtils.getStringPreference(context,"profilePhoto"),
                PrefUtils.getStringPreference(context,"nickname"),
                PrefUtils.getStringPreference(context,"email"),
                PrefUtils.getStringPreference(context,"region"),
                PrefUtils.getStringPreference(context,"gender"),
                PrefUtils.getStringPreference(context,"birthday"),
                PrefUtils.getStringPreference(context,"signature"));
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
        PrefUtils.setIntegerPreference(context,"id",0);
        PrefUtils.setStringPreference(context,"username",null);
        PrefUtils.setStringPreference(context,"tagId",null);
        PrefUtils.setStringPreference(context,"password",null);
        PrefUtils.setIntegerPreference(context,"focusCount",0);
        PrefUtils.setIntegerPreference(context,"isFocusCount",0);
        PrefUtils.setIntegerPreference(context,"friendsCount",0);
        PrefUtils.setStringPreference(context,"profilePhoto",null);
        PrefUtils.setStringPreference(context,"nickname",null);
        PrefUtils.setStringPreference(context,"email",null);
        PrefUtils.setStringPreference(context,"region",null);
        PrefUtils.setStringPreference(context,"gender",null);
        PrefUtils.setStringPreference(context,"birthday",null);
        PrefUtils.setStringPreference(context,"signature",null);
    }
}
