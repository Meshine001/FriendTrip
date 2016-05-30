package com.xjtu.friendtrip.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.MapView;
import com.ecloud.pulltozoomview.PullToZoomScrollViewEx;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Types.BoomType;
import com.nightonke.boommenu.Types.ButtonType;
import com.nightonke.boommenu.Types.OrderType;
import com.nightonke.boommenu.Types.PlaceType;
import com.nightonke.boommenu.Util;
import com.xjtu.friendtrip.R;
import com.xjtu.friendtrip.adapter.TimeLineAdapter;
import com.xjtu.friendtrip.bean.Text;
import com.xjtu.friendtrip.bean.TimeLineModel;
import com.xjtu.friendtrip.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Meshine on 16/5/29.
 */
public class TraceActivity extends Activity {


    private static final String TAG = TraceActivity.class.getName() ;

    @BindView(R.id.pull_zoom_scrollview)
    PullToZoomScrollViewEx ptzScrollView;

    @BindView(R.id.boom)
    BoomMenuButton boom;

    MapView mapView;

    RecyclerView timeLineRecyclerView;
    TimeLineAdapter timeLineAdapter;
    List<TimeLineModel> timeLineItems = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trace);
        ButterKnife.bind(this);

        initPullToZoomScrollView();
        initBoom();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    private void initPullToZoomScrollView() {
        View headView = LayoutInflater.from(this).inflate(R.layout.trace_head_view, null, false);
        View zoomView = LayoutInflater.from(this).inflate(R.layout.trace_zoom_view, null, false);
        View contentView = LayoutInflater.from(this).inflate(R.layout.trace_content_view, null, false);
        ptzScrollView.setHeaderView(headView);
        ptzScrollView.setZoomView(zoomView);
        ptzScrollView.setScrollContentView(contentView);

        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int mScreenHeight = localDisplayMetrics.heightPixels;
        int mScreenWidth = localDisplayMetrics.widthPixels;
        LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(mScreenWidth, (int) (9.0F * (mScreenWidth / 16.0F)));
        ptzScrollView.setHeaderLayoutParams(localObject);

        ptzScrollView.setZoomEnabled(true);


        initTimeLine();

        initBaiduMap();
    }

    private void initBaiduMap() {
        mapView = (MapView) ptzScrollView.getRootView().findViewById(R.id.map);
    }

    private void initTimeLine() {
        timeLineRecyclerView = (RecyclerView) ptzScrollView.getRootView().findViewById(R.id.time_line_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        timeLineRecyclerView.setLayoutManager(linearLayoutManager);

        timeLineAdapter = new TimeLineAdapter(this,timeLineItems);
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
                        Intent intent = new Intent(TraceActivity.this,TraceItemActivity.class);
                        switch (buttonIndex){
                            case 0:
                                intent.putExtra("type",TimeLineModel.TYPE_TEXT);
                                break;
                            case 1:
                                break;
                            case 2:
                                intent.putExtra("type",TimeLineModel.TYPE_IMAGE);
                                break;
                        }
                        startActivityForResult(intent,TraceItemActivity.TIME_LINE_ITEM_EDIT);
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
        if (requestCode == TraceItemActivity.TIME_LINE_ITEM_EDIT){
            TimeLineModel model = (TimeLineModel) data.getSerializableExtra("data");
            Log.i(TAG,model.toString());
            timeLineItems.add(model);
            timeLineAdapter.notifyDataSetChanged();
        }
    }
}
