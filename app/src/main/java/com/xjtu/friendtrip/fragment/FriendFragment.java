package com.xjtu.friendtrip.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.xjtu.friendtrip.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ming on 2016/5/25.
 */
public class FriendFragment extends Fragment {

    private static final String TAG = FriendFragment.class.getName();
    @BindView(R.id.baidu_map)
    MapView mapView;
    BaiduMap map;

    //定位相关
    LocationClient locationClient;
    public MyLocationListenner locationListenner;
    private MyLocationConfiguration.LocationMode mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
    BitmapDescriptor mCurrentMarker;
    boolean requestLocation = true; // 是否首次定位
    private static final int accuracyCircleFillColor = 0xAAFFFF88;
    private static final int accuracyCircleStrokeColor = 0xAA00FF00;

    List<Marker> markers = new ArrayList<>();


    @OnClick({R.id.request_location})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.request_location:
                requestLocation = true;
                locationClient.stop();
                locationClient.start();
                break;
        }
    }

    /**
     * 获取朋友们对位置
     */
    private void getFriendLocations(BDLocation location) {
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.ic_flag);

        // 通过marker的icons设置一组图片，再通过period设置多少帧刷新一次图片资源
        ArrayList<BitmapDescriptor> giflist = new ArrayList<BitmapDescriptor>();
        giflist.add(bitmap);
        giflist.add(bitmap);
        giflist.add(bitmap);
        giflist.add(bitmap);


        for (Marker m : markers) {
            m.remove();
        }
        markers.clear();


        List<OverlayOptions> options = new ArrayList<>();


        //TODO
        for (int i = 0; i < 10; i++) {
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


            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(point)
                    .icons(giflist)
                    .zIndex(9).period(10);
            //在地图上添加Marker，并显示
            options.add(option);
        }

        for (OverlayOptions o : options) {
            Marker marker = (Marker) map.addOverlay(o);
            marker.setTitle("HAHA");
            markers.add(marker);
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


        map = mapView.getMap();
        // 修改为自定义marker
//        mCurrentMarker = BitmapDescriptorFactory
//                .fromResource(R.drawable.ic_my_location);
//        map.setMyLocationConfigeration(new MyLocationConfiguration(
//                mCurrentMode, true, mCurrentMarker,
//                accuracyCircleFillColor, accuracyCircleStrokeColor));
        // 开启定位图层
        map.setMyLocationEnabled(true);
        //定位初始化
        locationClient = new LocationClient(getActivity().getApplicationContext());
        locationListenner = new MyLocationListenner();
        locationClient.registerLocationListener(locationListenner);
        initLocation();
        locationClient.start();

        map.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(getContext(), marker.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
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
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            Log.i(TAG, "获得新位置:[" + location.getLatitude() + "," + location.getLongitude() + "]");
            // map view 销毁后不在处理新接收的位置
            if (location == null || mapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            map.setMyLocationData(locData);
            if (requestLocation) {
                requestLocation = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus status = new MapStatus.Builder()
                        .target(ll).zoom(18).build();
                MapStatusUpdate update = MapStatusUpdateFactory.newMapStatus(status);
                map.setMapStatus(update);
                //getFriendLocations(location);
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {

        }
    }


    @Override
    public void onDestroy() {
        locationClient.stop();
        map.setMyLocationEnabled(false);
        mapView.onDestroy();
        mapView = null;
        super.onDestroy();
    }

    @Override
    public void onResume() {
        requestLocation = true;
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        requestLocation = true;
        mapView.onPause();
        super.onPause();
    }
}
