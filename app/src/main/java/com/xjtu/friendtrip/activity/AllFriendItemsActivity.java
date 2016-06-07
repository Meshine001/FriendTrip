package com.xjtu.friendtrip.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.xjtu.friendtrip.Net.Config;
import com.xjtu.friendtrip.Net.RequestUtil;
import com.xjtu.friendtrip.R;
import com.xjtu.friendtrip.adapter.DiscoveryGridAdapter;
import com.xjtu.friendtrip.adapter.StoryGridAdapter;
import com.xjtu.friendtrip.bean.Discovery;
import com.xjtu.friendtrip.bean.Story;
import com.xjtu.friendtrip.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class AllFriendItemsActivity extends BaseActivity {

    @BindView(R.id.ptr_frame)
    PtrFrameLayout ptrFrame;


    @BindView(R.id.friend_grid)
    GridView friendGrid;
    List<Story> friendCovers = new ArrayList<>();
    StoryGridAdapter storyGridAdapter;

    int offset = 0;
    int limit = 8;

    @BindView(R.id.pull_up_bottom)
    RelativeLayout pullUpBottom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_friend_items);
        ButterKnife.bind(this);
        initToolbar("朋友");
        initPrtFrame();
        initFriendGrid();
    }

    private void initFriendGrid() {
        friendGrid.setOnScrollListener(new AbsListView.OnScrollListener() {
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
        storyGridAdapter = new StoryGridAdapter(friendCovers, this);
        friendGrid.setAdapter(storyGridAdapter);
        initFriendGridData();

    }

    private void appendData() {
        pullUpBottom.setVisibility(View.VISIBLE);
        getDataFromCloud();
        storyGridAdapter.notifyDataSetChanged();
        friendGrid.setSelection(friendCovers.size()-1);
        pullUpBottom.setVisibility(View.GONE);
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
                initFriendGridData();
                frame.refreshComplete();
            }
        });
    }

    private void initFriendGridData() {
        offset = 0;
        limit = 8;
        friendCovers.clear();
        getDataFromCloud();
    }

    private void getDataFromCloud() {
        String url = Config.REQUEST_TOP_LIKE_FRIENDS + offset +"/"+limit+Config.FIND_TOP_STAR_NOTES;
        CommonUtil.printRequest("朋友",url);
        Ion.with(this).load("GET",url).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                CommonUtil.printResponse(result);
                friendCovers.addAll(RequestUtil.requestToStories(result));
                storyGridAdapter.notifyDataSetChanged();
                offset += (limit +1);
            }
        });
    }

}
