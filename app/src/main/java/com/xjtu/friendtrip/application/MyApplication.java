package com.xjtu.friendtrip.application;

import android.app.Application;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.xjtu.friendtrip.Net.Config;
import com.xjtu.friendtrip.Net.Tencent;
import com.xjtu.friendtrip.activity.MainActivity;
import com.xjtu.friendtrip.util.CommonUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Meshine on 16/5/25.
 */
public class MyApplication extends Application {
    private static final String TAG = MyApplication.class.getName();

    private static MainActivity mainActivity = null;

    private static MyApplication mInstance;

    public static MyApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        //百度地图初始化
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
      //  Log.i(TAG,"初始化百度地图");
    //    SDKInitializer.initialize(this);

        //万象优图auth
        //getPicUploadAuth();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        exit();
    }

    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    public static void setMainActivity(MainActivity mainActivity) {
        MyApplication.mainActivity = mainActivity;
    }

    public void exit() {
        if (mainActivity != null) {
            mainActivity.finish();
        }
        System.exit(0);
    }

    void getPicUploadAuth(){
        String url = Config.QPicAuth+"?type=upload";
        CommonUtil.printRequest("上传图片授权",url);
        Ion.with(getApplicationContext())
                .load(url)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        try {
                            Log.i("GetAuth",result);
                            JSONObject jo = new JSONObject(result);
                            Tencent.picAuth = jo.getString("sign");
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
    }

}
