package com.xjtu.friendtrip.util;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ming on 2016/6/4.
 */
public class LocationUtil {

    public static void drawLine(LatLng start,LatLng end,BaiduMap map){
        List<LatLng> pts = new ArrayList<>();
        pts.add(start);
        pts.add(end);

        OverlayOptions ploylineOptions = new PolylineOptions()
                .color(Color.parseColor("#f39c12"))
                .points(pts)
                .width(3);
        map.addOverlay(ploylineOptions);
    }

    public static void setMyLocationData(BDLocation location,BaiduMap map){
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(100).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();

        map.setMyLocationData(locData);
    }

    /**
     *
     * @param bitmap
     * @param lat
     * @param lon
     * @param map
     * @return
     */
    public static Marker addMarker(BitmapDescriptor bitmap, Double lat, Double lon, BaiduMap map) {
        //定义Maker坐标点
        LatLng point = new LatLng(lat,
                lon);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        Marker marker = (Marker) map.addOverlay(option);
        return marker;
    }

    /**
     * @param lat
     * @param lon
     * @param map
     * @param zoom
     */
    public static void changeMapCenter(Double lat, Double lon, BaiduMap map, Float zoom) {
        LatLng ll = new LatLng(lat,
                lon);
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(zoom);
        map.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    public static void initLocationOptions(LocationClient locationClient) {
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


}
