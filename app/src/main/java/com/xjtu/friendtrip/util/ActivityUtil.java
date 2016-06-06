package com.xjtu.friendtrip.util;

import android.content.Context;
import android.content.Intent;

import com.xjtu.friendtrip.activity.UserInfoActivity;

/**
 * Created by Ming on 2016/6/6.
 */
public class ActivityUtil {

    public static void startUserInfoActivity(Context packageContext,Integer userId){
        Intent userInfoIntent = new Intent(packageContext,UserInfoActivity.class);
        userInfoIntent.putExtra("userId",userId);
        packageContext.startActivity(userInfoIntent);
    }
}
