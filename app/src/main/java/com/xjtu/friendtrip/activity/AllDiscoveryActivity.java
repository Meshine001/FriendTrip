
package com.xjtu.friendtrip.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.xjtu.friendtrip.Net.Config;
import com.xjtu.friendtrip.Net.RequestUtil;
import com.xjtu.friendtrip.R;
import com.xjtu.friendtrip.adapter.DiscoveryGridAdapter;
import com.xjtu.friendtrip.bean.Cover;
import com.xjtu.friendtrip.bean.Discovery;
import com.xjtu.friendtrip.util.CommonUtil;
import com.xjtu.friendtrip.widget.ExpandGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class AllDiscoveryActivity extends BaseActivity {

    @BindView(R.id.ptr_frame)
    PtrFrameLayout ptrFrame;


    @BindView(R.id.discovery_grid)
    GridView discoveryGrid;
    List<Discovery> discoveryCovers = new ArrayList<>();
    DiscoveryGridAdapter discoveryGridAdaper;

    int offset = 0;
    int limit = 8;

    @BindView(R.id.pull_up_bottom)
    RelativeLayout pullUpBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_discovery);
        ButterKnife.bind(this);
        initToolbar("新探索.新发现");
        initPrtFrame();

        initDiscoveryGrid();
    }


    /**
     * 初始化 新发现,新景点
     */
    private void initDiscoveryGrid() {
        discoveryGrid.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    // 当不滚动时
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        // 判断滚动到底部
                        if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
                            appendData();
                        }
                        break;
                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        discoveryGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AllDiscoveryActivity.this, DiscoveryDetailsActivity.class);
                intent.putExtra("discovery",discoveryCovers.get(position));
                startActivity(intent);
            }
        });
        discoveryGridAdaper = new DiscoveryGridAdapter(discoveryCovers, this);
        discoveryGrid.setAdapter(discoveryGridAdaper);
        initDiscoveryGridData();
    }

    private void appendData() {
        pullUpBottom.setVisibility(View.VISIBLE);
        getDataFromCloud();
        discoveryGridAdaper.notifyDataSetChanged();
        discoveryGrid.setSelection(discoveryCovers.size()-1);
        pullUpBottom.setVisibility(View.GONE);
    }

    private void getDataFromCloud(){
        String url = Config.REQUEST_TOP_LIKE_DISCOVERIES + offset +"/"+limit+Config.FIND_TOP_STAR_SPOTS;
        CommonUtil.printRequest("新发现",url);
        Ion.with(this).load("GET",url).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                CommonUtil.printResponse(result);
                discoveryCovers.addAll(RequestUtil.requestToDiscoveries(result));
                discoveryGridAdaper.notifyDataSetChanged();
                offset += (limit +1);
            }
        });
    }

    private void initDiscoveryGridData() {
        offset = 0;
        limit = 8;
        discoveryCovers.clear();
        getDataFromCloud();
    }

    private void initPrtFrame() {
        View view = LayoutInflater.from(this).inflate(R.layout.ptr_header,null);
        ptrFrame.setHeaderView(view);
        ptrFrame.setPullToRefresh(true);
        ptrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                    initDiscoveryGridData();
                    frame.refreshComplete();
            }
        });
    }
}
