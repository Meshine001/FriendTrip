package com.xjtu.friendtrip.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.LogoPosition;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.Point;
import com.bumptech.glide.Glide;
import com.xjtu.friendtrip.R;
import com.xjtu.friendtrip.baidu.MyLocationListener;
import com.xjtu.friendtrip.widget.AdBannerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ming on 2016/5/25.
 */
public class FriendFragment extends Fragment {

    @BindView(R.id.baidu_map)
    MapView baiduMapView;
    BaiduMap baiduMap;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = null;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MyLocationListener.REQUEST_LOCATION_OK:

                    BDLocation location = (BDLocation) msg.obj;
                    pointMyLocation(location);
                    mLocationClient.stop();
                    break;
            }
        }
    };
    List<LatLng> friendLocations = new ArrayList<>();


    @OnClick({R.id.request_location})
    void onClick(View view){
        switch (view.getId()){
            case R.id.request_location:
                mLocationClient.start();
                break;
        }
    }

    private void pointMyLocation(BDLocation location) {
        baiduMap.clear();
        //定义Maker坐标点
        LatLng point = new LatLng(location.getLatitude(), location.getLongitude());
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.ic_my_location);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        baiduMap.addOverlay(option);
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(point)
                .zoom(17)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化

        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        baiduMap.setMapStatus(mMapStatusUpdate);

        getFriendLocations(location);
    }

    /**
     * 获取朋友们对位置
     */
    private void getFriendLocations(BDLocation location) {
    //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.ic_flag);

        friendLocations.clear();

        //TODO
        for (int i=0;i<10;i++){
            Double x = Math.random()%10;
            while (x == 0){
                x = Math.random()%10;
            }

            x = x/100;

            LatLng point = null;
            if (i%4 == 0)
            //定义Maker坐标点
             point = new LatLng(location.getLatitude()+x, location.getLongitude()+x);
            else if (i%4 == 1)point = new LatLng(location.getLatitude()+x, location.getLongitude()-x);
            else if (i%4 == 2)point = new LatLng(location.getLatitude()-x, location.getLongitude()-x);
            else if (i%4 == 3)point = new LatLng(location.getLatitude()-x, location.getLongitude()+x);

            friendLocations.add(point);
        }

        for (LatLng l:friendLocations){
            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(l)
                    .icon(bitmap);
            //在地图上添加Marker，并显示
            baiduMap.addOverlay(option);
        }



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend, container, false);
        ButterKnife.bind(this, view);

        initBaiduMap();

        return view;
    }

    private void initBaiduMap() {


        baiduMap = baiduMapView.getMap();

        mLocationClient = new LocationClient(getContext());     //声明LocationClient类
        myListener = new MyLocationListener(handler);
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        initLocation();
        mLocationClient.start();
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
        mLocationClient.setLocOption(option);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        baiduMapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        baiduMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        baiduMapView.onPause();
    }
}
