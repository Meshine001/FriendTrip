package com.xjtu.friendtrip.util;

import android.content.Context;

/**
 * Created by Meshine on 16/5/31.
 */
public class StoreBox {

    public static void setUserNameAndPsd(Context context, String username, String password){
        PrefUtils.setStringPreference(context,"username",username);
        PrefUtils.setStringPreference(context,"password",password);
    }

    public static void setUserNick(Context context, String nick){
        PrefUtils.setStringPreference(context,"nick",nick);
    }



    public static void clearUserInfo(Context context){
        setUserNameAndPsd(context,null,null);
        setUserNick(context,null);
    }
}
