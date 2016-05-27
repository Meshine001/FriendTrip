package com.xjtu.friendtrip.application;

import android.app.Application;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by Meshine on 16/5/25.
 */
public class MyApplication extends Application {
    private static final String TAG = MyApplication.class.getName();

    @Override
    public void onCreate() {
        super.onCreate();
        //百度地图初始化
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        Log.i(TAG,"初始化百度地图");
        SDKInitializer.initialize(this);

    }
}
