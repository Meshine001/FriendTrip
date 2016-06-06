package com.xjtu.friendtrip.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;
import com.ecloud.pulltozoomview.PullToZoomScrollViewEx;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Types.BoomType;
import com.nightonke.boommenu.Types.ButtonType;
import com.nightonke.boommenu.Types.OrderType;
import com.nightonke.boommenu.Types.PlaceType;
import com.nightonke.boommenu.Util;
import com.xjtu.friendtrip.R;
import com.xjtu.friendtrip.adapter.TimeLineAdapter;
import com.xjtu.friendtrip.bean.CustomLocation;
import com.xjtu.friendtrip.bean.Text;
import com.xjtu.friendtrip.bean.TimeLineModel;
import com.xjtu.friendtrip.util.CommonUtil;
import com.xjtu.friendtrip.util.LocationUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Meshine on 16/5/29.
 */
public class TraceActivity extends BaseActivity {


    private static final String TAG = TraceActivity.class.getName();


    ImageView topRight;
    ImageView topSubRight;

    @BindView(R.id.map_frame)
    FrameLayout mapFrame;
    SupportMapFragment mapFragment;
    BaiduMap map;
    List<LatLng> pts = new ArrayList<>();

    @BindView(R.id.time_line_recycler_view)
    RecyclerView timeLineRecyclerView;
    TimeLineAdapter timeLineAdapter;
    List<TimeLineModel> timeLineItems = new ArrayList<>();

    @BindView(R.id.boom)
    BoomMenuButton boom;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trace);
        ButterKnife.bind(this);
        initUI();
        initTimeLine();
        initBoom();
    }

    private void initUI() {
        initTop();
        initMap();
    }

    private void initTop() {
        initToolbar("");
        topRight = new ImageView(this);
        topRight.setImageResource(R.drawable.ic_share);
        topRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        setActionBarRightView(topRight);

        topSubRight = new ImageView(this);
        topSubRight.setImageResource(R.drawable.ic_cloud);
        topSubRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        setActionBarSubRightView(topSubRight);
    }

    private void initMap() {
        mapFragment = new SupportMapFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.map_frame, mapFragment).commit();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                //execute the task
                map = mapFragment.getBaiduMap();
            }
        }, 2000);


    }

    private void initTimeLine() {
        timeLineRecyclerView = (RecyclerView) findViewById(R.id.time_line_recycler_view);

        timeLineRecyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        timeLineRecyclerView.setLayoutManager(linearLayoutManager);

        timeLineAdapter = new TimeLineAdapter(this, timeLineItems);
        timeLineRecyclerView.setAdapter(timeLineAdapter);
    }

    boolean isBoomInit = false;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (!isBoomInit) {
            initBoom();
        }
        isBoomInit = true;
    }

    private void initBoom() {
        // boom = (BoomMenuButton) ptzScrollView.getRootView().findViewById(R.id.boom);
        Drawable[] subButtonDrawables = {getResources().getDrawable(R.drawable.ic_text), getResources().getDrawable(R.drawable.ic_mic), getResources().getDrawable(R.drawable.ic_image)};
        String[] subButtonTexts = {"文字", "语音", "照片"};
        int[] colors = {Color.parseColor("#f39c12"), Color.parseColor("#3498db"), Color.parseColor("#2ecc71")};
        int[][] subButtonColors = new int[3][2];
        for (int i = 0; i < 3; i++) {
//            subButtonColors[i][1] = ContextCompat.getColor(this, R.color.colorPrimary);
            subButtonColors[i][1] = colors[i];
            subButtonColors[i][0] = Util.getInstance().getPressedColor(subButtonColors[i][1]);
        }
        // Now with Builder, you can init BMB more convenient
        new BoomMenuButton.Builder()
                .subButtons(subButtonDrawables, subButtonColors, subButtonTexts)
                .onSubButtonClick(new BoomMenuButton.OnSubButtonClickListener() {
                    @Override
                    public void onClick(int buttonIndex) {
                        Intent intent = new Intent(TraceActivity.this, TraceItemActivity.class);
                        switch (buttonIndex) {
                            case 0:
                                intent.putExtra("type", TimeLineModel.TYPE_TEXT);
                                break;
                            case 1:
                                break;
                            case 2:
                                intent.putExtra("type", TimeLineModel.TYPE_IMAGE);
                                break;
                        }
                        startActivityForResult(intent, TraceItemActivity.TIME_LINE_ITEM_EDIT);
                    }
                })
                .button(ButtonType.HAM)
                .boom(BoomType.PARABOLA_2)
                .place(PlaceType.HAM_3_1)
                .shareStyle(3f, CommonUtil.getRandomColor(), CommonUtil.getRandomColor())
                .init(boom);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TraceItemActivity.TIME_LINE_ITEM_EDIT) {
            TimeLineModel model = (TimeLineModel) data.getSerializableExtra("data");
            Log.i(TAG, model.toString());
            timeLineItems.add(model);
            timeLineAdapter.notifyDataSetChanged();

            int type = model.getType();
            CustomLocation location = model.getLocation();
            drawMarker(location, type);
        }
    }

    private void drawMarker(CustomLocation location, int type) {

        //构建Marker图标
        BitmapDescriptor bitmap = null;
        switch (type) {
            case TimeLineModel.TYPE_TEXT:
                bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.ic_trace_text);
                break;
            case TimeLineModel.TYPE_RECORD:
                bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.ic_trace_recorder);
                break;
            case TimeLineModel.TYPE_IMAGE:
                bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.ic_trace_image);
                break;
        }
        drawTrace(location, map);
        LocationUtil.addMarker(bitmap, location.getLat(), location.getLon(), map);
        if (pts.size() > 2) {
            LocationUtil.changeMapCenter(location.getLat(), location.getLon(), map, 16f);
        } else if (pts.size() > 1) {
            LocationUtil.changeMapCenter(location.getLat(), location.getLon(), map, 17f);
        } else {
            LocationUtil.changeMapCenter(location.getLat(), location.getLon(), map, 18f);
        }

    }

    private void drawTrace(CustomLocation location, BaiduMap map) {
        LatLng end = new LatLng(location.getLat(), location.getLon());
        if (pts.size() > 0) {
            LatLng start = pts.get(pts.size() - 1);
            LocationUtil.drawLine(start, end, map);
        }
        pts.add(end);
    }
}
