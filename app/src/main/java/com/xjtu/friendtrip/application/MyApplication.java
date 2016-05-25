package com.xjtu.friendtrip.application;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by Meshine on 16/5/25.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //百度地图初始化
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());

    }
}
