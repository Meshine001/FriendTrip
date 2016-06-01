
package com.xjtu.friendtrip.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.xjtu.friendtrip.R;
import com.xjtu.friendtrip.adapter.DiscoveryGridAdapter;
import com.xjtu.friendtrip.bean.Cover;
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
    List<Cover> discoveryCovers = new ArrayList<>();
    DiscoveryGridAdapter discoveryGridAdaper;

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
        discoveryGridAdaper = new DiscoveryGridAdapter(discoveryCovers, this);
        discoveryGrid.setAdapter(discoveryGridAdaper);
        initDiscoveryGridData();
    }

    private void appendData() {
        pullUpBottom.setVisibility(View.VISIBLE);
        //TODO
        discoveryCovers.add(new Cover(
                "http://pic1.nipic.com/2008-12-01/2008121221714934_2.jpg",
                "发现一处美景,曲江景园",
                "http://img5.imgtn.bdimg.com/it/u=2593135614,3870081477&fm=21&gp=0.jpg",
                "小明", "曲江区"
        ));
        discoveryCovers.add(new Cover(
                "http://img1.qunarzz.com/sight/p0/201403/07/e52053cbc7468d9ac4c19401b21dad34.jpg_1190x550_0344d9a4.jpg",
                "lalalala。这是个好地方",
                "http://img5.duitang.com/uploads/item/201509/12/20150912081740_nkuFA.jpeg",
                "小亚茹", "雁塔区"
        ));
        discoveryCovers.add(new Cover(
                "http://pic1.nipic.com/2008-12-01/2008121221714934_2.jpg",
                "发现一处美景,曲江景园",
                "http://img5.imgtn.bdimg.com/it/u=2593135614,3870081477&fm=21&gp=0.jpg",
                "小明", "曲江区"
        ));
        discoveryCovers.add(new Cover(
                "http://img1.qunarzz.com/sight/p0/201403/07/e52053cbc7468d9ac4c19401b21dad34.jpg_1190x550_0344d9a4.jpg",
                "lalalala。这是个好地方",
                "http://img5.duitang.com/uploads/item/201509/12/20150912081740_nkuFA.jpeg",
                "小亚茹", "雁塔区"
        ));
        discoveryGridAdaper.notifyDataSetChanged();
        discoveryGrid.setSelection(discoveryCovers.size()-1);
        pullUpBottom.setVisibility(View.GONE);
    }


    private void initDiscoveryGridData() {
        discoveryCovers.clear();
        //TODO
        discoveryCovers.add(new Cover(
                "http://pic1.nipic.com/2008-12-01/2008121221714934_2.jpg",
                "发现一处美景,曲江景园",
                "http://img5.imgtn.bdimg.com/it/u=2593135614,3870081477&fm=21&gp=0.jpg",
                "小明", "曲江区"
        ));
        discoveryCovers.add(new Cover(
                "http://img1.qunarzz.com/sight/p0/201403/07/e52053cbc7468d9ac4c19401b21dad34.jpg_1190x550_0344d9a4.jpg",
                "lalalala。这是个好地方",
                "http://img5.duitang.com/uploads/item/201509/12/20150912081740_nkuFA.jpeg",
                "小亚茹", "雁塔区"
        ));
        discoveryCovers.add(new Cover(
                "http://pic19.nipic.com/20120207/9201992_183203277000_2.jpg",
                "还是要善于发现美",
                "http://img4.duitang.com/uploads/item/201511/08/20151108131440_HvuEB.thumb.700_0.jpeg",
                "小思思", "雁塔区"
        ));
        discoveryCovers.add(new Cover(
                "http://e.hiphotos.baidu.com/zhidao/pic/item/d0c8a786c9177f3e53a94fd872cf3bc79e3d5641.jpg",
                "景色不错.在那个神马神马地方",
                "http://img4q.duitang.com/uploads/item/201404/14/20140414004026_H4Q8R.jpeg",
                "小波波熊", "碑林区"
        ));
        discoveryCovers.add(new Cover(
                "http://pic1.nipic.com/2008-12-01/2008121221714934_2.jpg",
                "发现一处美景,曲江景园",
                "http://img5.imgtn.bdimg.com/it/u=2593135614,3870081477&fm=21&gp=0.jpg",
                "小明", "曲江区"
        ));
        discoveryCovers.add(new Cover(
                "http://img1.qunarzz.com/sight/p0/201403/07/e52053cbc7468d9ac4c19401b21dad34.jpg_1190x550_0344d9a4.jpg",
                "lalalala。这是个好地方",
                "http://img5.duitang.com/uploads/item/201509/12/20150912081740_nkuFA.jpeg",
                "小亚茹", "雁塔区"
        ));
        discoveryCovers.add(new Cover(
                "http://pic19.nipic.com/20120207/9201992_183203277000_2.jpg",
                "还是要善于发现美",
                "http://img4.duitang.com/uploads/item/201511/08/20151108131440_HvuEB.thumb.700_0.jpeg",
                "小思思", "雁塔区"
        ));
        discoveryCovers.add(new Cover(
                "http://e.hiphotos.baidu.com/zhidao/pic/item/d0c8a786c9177f3e53a94fd872cf3bc79e3d5641.jpg",
                "景色不错.在那个神马神马地方",
                "http://img4q.duitang.com/uploads/item/201404/14/20140414004026_H4Q8R.jpeg",
                "小波波熊", "碑林区"
        ));
        discoveryGridAdaper.notifyDataSetChanged();
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
