package com.xjtu.friendtrip.activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.google.gson.Gson;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.xjtu.friendtrip.Net.Config;
import com.xjtu.friendtrip.Net.FriendNotesByLocationJson;
import com.xjtu.friendtrip.R;
import com.xjtu.friendtrip.bean.Image;
import com.xjtu.friendtrip.fragment.FriendFragment;
import com.xjtu.friendtrip.fragment.HomeFragment;
import com.xjtu.friendtrip.fragment.MeFragment;
import com.xjtu.friendtrip.fragment.ShareFragment;
import com.xjtu.friendtrip.listener.Locationlistener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.majiajie.pagerbottomtabstrip.Controller;
import me.majiajie.pagerbottomtabstrip.PagerBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectListener;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getName();

    public static final int INIT_MAP = 0x1000;
    /**
     * 底部菜单
     */
    @BindView(R.id.bottom)
    PagerBottomTabLayout bottomTabLayout;
    Controller controller;


    List<Fragment> fragments;
    MapView mapView;
    BaiduMap map;
    @BindView(R.id.request_location)
    ImageView requestLoction;
    private static final int accuracyCircleFillColor = 0xAAFFFF88;
    private static final int accuracyCircleStrokeColor = 0xAA00FF00;

    LocationClient locationClient;
    BDLocationListener locationListener;

    List<Marker> markers = new ArrayList<>();

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Locationlistener.LOCATION_SUCCESS:
                    BDLocation location = (BDLocation) msg.obj;
                    updateMyLocation(location);
                    break;
                case Locationlistener.LOCATION_FAILD:
                    Toast.makeText(getBaseContext(), "获取位置失败", Toast.LENGTH_SHORT).show();
                    locationClient.stop();
                    break;
                case INIT_MAP:
                    initBaiduMap();
                    break;
            }
        }
    };

    /**
     * 更新位置
     *
     * @param location
     */
    private void updateMyLocation(BDLocation location) {

        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(100).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
        map.setMyLocationData(locData);

        LatLng ll = new LatLng(location.getLatitude(),
                location.getLongitude());
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(18.0f);
        map.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        locationClient.stop();
        getFriendLocations(location);
    }

    String[] urls = {
            "http://hdn.xnimg.cn/photos/hdn321/20130612/2235/h_main_NNN4_e80a000007df111a.jpg",
            "http://h.hiphotos.baidu.com/image/h%3D200/sign=71cd4229be014a909e3e41bd99763971/472309f7905298221dd4c458d0ca7bcb0b46d442.jpg",
            "http://img4.duitang.com/uploads/item/201511/08/20151108131440_HvuEB.thumb.700_0.jpeg",
            "http://img4q.duitang.com/uploads/item/201404/14/20140414004026_H4Q8R.jpeg",
            "http://img5.imgtn.bdimg.com/it/u=2593135614,3870081477&fm=21&gp=0.jpg"
    };
    /**
     * 获取朋友的位置
     */
    private void getFriendLocations(BDLocation location) {
        String body = new Gson().toJson(new FriendNotesByLocationJson(
                109.007894, 34.229714, 1203, 10
        ));
        Log.i(TAG, "请求周边朋友:" + body);
        Ion.with(this).load("POST", Config.REQUEST_FRIENDS_NOTES_BY_LOCATION).setStringBody(body).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                Log.i(TAG, "请求周边朋友结果:" + result);
            }
        });


        for (Marker m : markers) {
            m.remove();
        }
        markers.clear();


        final List<OverlayOptions> options = new ArrayList<>();


        //TODO
        for (int i = 0; i < 5; i++) {
            Double x = Math.random() % 10;
            while (x == 0) {
                x = Math.random() % 10;
            }

            x = x / 100;


            LatLng point = null;
            if (i % 4 == 0)
                //定义Maker坐标点
                point = new LatLng(location.getLatitude() + x, location.getLongitude() + x);
            else if (i % 4 == 1)
                point = new LatLng(location.getLatitude() + x, location.getLongitude() - x);
            else if (i % 4 == 2)
                point = new LatLng(location.getLatitude() - x, location.getLongitude() - x);
            else if (i % 4 == 3)
                point = new LatLng(location.getLatitude() - x, location.getLongitude() + x);


            final LatLng finalPoint = point;

            Glide.with(this).load(urls[i]).asBitmap().into(new SimpleTarget<Bitmap>(60,60) {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    Log.i(TAG,"图片下载成功");
                    //构建Marker图标
                    BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(resource);
                    //构建MarkerOption，用于在地图上添加Marker
                    OverlayOptions option = new MarkerOptions()
                            .position(finalPoint)
                            .icon(icon)
                            .zIndex(9).period(10);
                    //在地图上添加Marker，并显示
                    options.add(option);
                    Marker marker = (Marker) map.addOverlay(option);
                    marker.setTitle("HAHA");
                    markers.add(marker);
                }
            });

        }

//        for (OverlayOptions o : options) {
//            Marker marker = (Marker) map.addOverlay(o);
//            marker.setTitle("HAHA");
//            markers.add(marker);
//        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initBottom();
    }

    @OnClick({R.id.request_location})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.request_location:
                locationClient.start();
                break;
        }
    }

    private void initBaiduMap() {

        requestLoction.setVisibility(View.VISIBLE);

        SupportMapFragment mapFragment = (SupportMapFragment) fragments.get(1);

        mapView = mapFragment.getMapView();
        //不显示缩放控件
        mapView.showZoomControls(false);

        map = mapFragment.getBaiduMap();
        // 开启定位图层
        map.setMyLocationEnabled(true);
//        map.setMyLocationConfigeration(new MyLocationConfiguration(
//                MyLocationConfiguration.LocationMode.FOLLOWING,
//                true,BitmapDescriptorFactory.fromResource(R.drawable.ic_my_location)
//        ,accuracyCircleFillColor,accuracyCircleStrokeColor));

        locationListener = new Locationlistener(handler);
        locationClient = new LocationClient(this);
        locationClient.registerLocationListener(locationListener);
        initLocation();
        locationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        locationClient.setLocOption(option);
    }


    /**
     * 初始化底部TAB
     */
    private void initBottom() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(SupportMapFragment.newInstance());
        fragments.add(new ShareFragment());
        fragments.add(new MeFragment());

        showTab(0);

        controller = bottomTabLayout.builder()
                .addTabItem(R.drawable.ic_home, "首页")
                .addTabItem(R.drawable.ic_earth, "驴圈")
                .addTabItem(R.drawable.ic_share_50, "分享")
                .addTabItem(R.drawable.ic_user_50, "我")
                .build();
        controller.addTabItemClickListener(tabItemListener);
    }

    Class[] shareItems = {DiscoveryActivity.class, StoryActivity.class, TraceActivity.class};

    void showShareDialog() {

        final String[] stringItems = {"新发现", "新心情", "新游记"};

        final ActionSheetDialog dialog = new ActionSheetDialog(this, stringItems, null);
        dialog.isTitleShow(false).show();


        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "position:" + position);
                Intent intent = new Intent(MainActivity.this, shareItems[position]);
                startActivity(intent);
                dialog.dismiss();
            }
        });


    }


    OnTabItemSelectListener tabItemListener = new OnTabItemSelectListener() {
        @Override
        public void onSelected(int index, Object tag) {
            Log.i(TAG, "onSelected:" + index + "   TAG: " + tag.toString());
            if (index == 2) {
                showShareDialog();
            } else {
                showTab(index);
            }


        }

        @Override
        public void onRepeatClick(int index, Object tag) {
            Log.i(TAG, "onRepeatClick:" + index + "   TAG: " + tag.toString());
            if (index == 2) {
                showShareDialog();
            }
        }
    };

    void showTab(int postion) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.push_up_in, R.anim.push_up_out);

        if (1 == postion) {
            requestLoction.setVisibility(View.VISIBLE);
        } else {
            requestLoction.setVisibility(View.GONE);
        }

        for (int i = 0; i < fragments.size(); i++) {
            if (i != postion)
                transaction.hide(fragments.get(i));
        }
        if (fragments.get(postion).isAdded()) {
            transaction.show(fragments.get(postion)).commit();
        } else {
            transaction.add(R.id.frameLayout, fragments.get(postion)).show(fragments.get(postion)).commit();

            if (postion == 1) {
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        msg.what = INIT_MAP;
                        handler.sendMessage(msg);
                    }
                }, 2000);
            }
        }


    }


}
