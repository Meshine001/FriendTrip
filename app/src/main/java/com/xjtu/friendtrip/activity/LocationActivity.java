package com.xjtu.friendtrip.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.platform.comapi.map.L;
import com.google.gson.Gson;
import com.xjtu.friendtrip.R;
import com.xjtu.friendtrip.bean.CustomLocation;
import com.xjtu.friendtrip.bean.Image;
import com.xjtu.friendtrip.bean.Text;
import com.xjtu.friendtrip.bean.TimeLineModel;
import com.xjtu.friendtrip.listener.Locationlistener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Meshine on 16/5/27.
 */
public class LocationActivity extends BaseActivity {

    private static final int DRAG_COMPLETE = 1;
    private static final String TAG = LocationActivity.class.getName();
    public static final int GET_LOCATION = 2;

    @BindView(R.id.poi_locations)
    LinearLayout poiLocation;
    @BindView(R.id.custom_location)
    LinearLayout customLocation;

    @BindView(R.id.my_custom_location)
    TextView myCustomLocation;

    @BindView(R.id.location_switch_text)
    TextView locationSwitchText;

    @BindView(R.id.custom_location_edit)
    EditText customLocationEdit;

    @BindView(R.id.location_list)
    ListView locationList;
    List<String> locations = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @BindView(R.id.my_location_marker)
    ImageView myLocationMarker;

    @BindView(R.id.map)
    MapView mapView;
    BaiduMap map;
    public LocationClient locationClient = null;
    public BDLocationListener locationListener = null;

    boolean isPOI = true;

    CustomLocation customLoc;
    LatLng ll;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Locationlistener.LOCATION_SUCCESS:
                    locationClient.stop();
                    BDLocation location = (BDLocation) msg.obj;
                    ll = new LatLng(location.getLatitude(), location.getLongitude());
                    if (isPOI) {
                        updatePOI(location);
                    } else {
                        drawMyLocation(location);
                    }
                    break;
                case DRAG_COMPLETE:
                    ll = (LatLng) msg.obj;
                    Log.i(TAG,"获取新位置:"+ ll.toString());
                    break;
            }
        }
    };

    private void updatePOI(BDLocation location) {
        List<Poi> pois = location.getPoiList();
        for (Poi poi : pois) {
            String name = poi.getName();
            Log.i(TAG, name);
            locations.add(name);
        }
        arrayAdapter.notifyDataSetChanged();
    }

    private void drawMyLocation(BDLocation location) {
//        //定义Maker坐标点
        LatLng point = new LatLng(location.getLatitude(), location.getLongitude());
//        //构建Marker图标
//        BitmapDescriptor bitmap = BitmapDescriptorFactory
//                .fromResource(R.drawable.ic_map_pin);
//        //构建MarkerOption，用于在地图上添加Marker
//        OverlayOptions option = new MarkerOptions()
//                .position(point)
//                .icon(bitmap)
//                .zIndex(9)
//                .draggable(true);
//        //在地图上添加Marker，并显示
//        map.addOverlay(option);

        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(point)
                .zoom(17)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化

        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        map.setMapStatus(mMapStatusUpdate);
    }

    @OnClick({R.id.location_switch, R.id.my_location_icon})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.location_switch:
                isPOI = !isPOI;
                locationSwitch(isPOI);
                break;
            case R.id.my_location_icon:
                locationClient.start();
                break;
        }
    }

    void locationSwitch(boolean flag) {
        if (flag) {
            locationSwitchText.setText("自定义位置");
            poiLocation.setVisibility(View.VISIBLE);
            customLocation.setVisibility(View.GONE);

        } else {
            locationSwitchText.setText("使用已有位置");
            poiLocation.setVisibility(View.GONE);
            customLocation.setVisibility(View.VISIBLE);
            locationClient.start();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_location);
        ButterKnife.bind(this);
        initToolbar("你的位置");
        initToolbarRight();
        initMap();
        initPOIList();
    }

    @BindDrawable(R.drawable.ic_ok)
    Drawable dOk;


    private void initToolbarRight() {
        ImageView iv = new ImageView(this);
        iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        iv.setImageDrawable(dOk);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                customLoc = new CustomLocation();
                customLoc.setName(myCustomLocation.getText().toString());
                customLoc.setLat(ll.latitude);
                customLoc.setLon(ll.longitude);
                data.putExtra("data", customLoc);
                setResult(GET_LOCATION, data);
                finish();
            }
        });
        setActionBarRightView(iv);
    }

    private void initPOIList() {
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, locations);
        locationList.setAdapter(arrayAdapter);
        locationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                myCustomLocation.setText(locations.get(position));
            }
        });
    }


    private void initMap() {
        map = mapView.getMap();
        map.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                Message msg = new Message();
                msg.what = DRAG_COMPLETE;
                msg.obj = mapStatus.target;
                handler.sendMessage(msg);
            }
        });
        locationClient = new LocationClient(this);     //声明LocationClient类
        locationListener = new Locationlistener(handler);
        locationClient.registerLocationListener(locationListener);    //注册监听函数
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mapView.onPause();
    }

}
