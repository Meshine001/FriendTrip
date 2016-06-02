package com.xjtu.friendtrip.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.xjtu.friendtrip.Net.Config;
import com.xjtu.friendtrip.R;
import com.xjtu.friendtrip.activity.AllDiscoveryActivity;
import com.xjtu.friendtrip.activity.AllFriendItemsActivity;
import com.xjtu.friendtrip.activity.DiscoveryDetailsActivity;
import com.xjtu.friendtrip.activity.StoryDetailsActivity;
import com.xjtu.friendtrip.adapter.DiscoveryGridAdapter;
import com.xjtu.friendtrip.adapter.SiteListAdapter;
import com.xjtu.friendtrip.bean.Cover;
import com.xjtu.friendtrip.bean.Site;
import com.xjtu.friendtrip.widget.AdBannerView;
import com.xjtu.friendtrip.widget.ExpandGridView;
import com.xjtu.friendtrip.widget.ExpandListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by Ming on 2016/5/25.
 */
public class HomeFragment extends Fragment {


    private static final String TAG =  HomeFragment.class.getName();

    @BindView(R.id.ptr_frame_layout)
    PtrFrameLayout ptrFrameLayout;

    @BindView(R.id.ad_banner)
    AdBannerView adBannerView;
    List<AdBannerView.BannerItem> bannerItems = new ArrayList<>();

    @BindView(R.id.discovery_grid)
    ExpandGridView discoveryGrid;
    List<Cover> discoveryCovers = new ArrayList<>();
    DiscoveryGridAdapter discoveryGridAdaper;

    @BindView(R.id.friend_grid)
    ExpandGridView friendGrid;
    List<Cover> friendCovers = new ArrayList<>();
    DiscoveryGridAdapter friendGridAdaper;

    @BindView(R.id.sites_list)
    ExpandListView sitesList;
    List<Site> sites = new ArrayList<>();
    SiteListAdapter siteListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, view);

        init();

        return view;
    }

    @OnClick({R.id.discovery_all,R.id.friend_all})
    void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.discovery_all:
                intent.setClass(getContext(), AllDiscoveryActivity.class);
                break;
            case R.id.friend_all:
                intent.setClass(getContext(), AllFriendItemsActivity.class);
                break;
        }
        startActivity(intent);

    }

    private void init() {
        initPtrFrameLayout();
        initAdBanner();
        initDiscoveryGrid();
        initFriendGrid();
        initSites();
    }

    /**
     * 初始化普通大景点信息
     */
    private void initSites() {
        siteListAdapter = new SiteListAdapter(sites, getContext());
        sitesList.setAdapter(siteListAdapter);
        initSitesData();
    }

    private void initSitesData() {
        sites.clear();
        //TODO
        sites.add(new Site(
                "http://m.tuniucdn.com/filebroker/cdn/olv/82/1f/821fc8a99db04d67ec571a8e3086688e_w800_h400_c1_t0.jpg",
                "东京",
                "舒适直飞 迪斯尼或购物自在随心"
        ));
        sites.add(new Site(
                "http://m.tuniucdn.com/filebroker/cdn/snc/97/8c/978c845f1bf14822b672a6de419b2dd3_w450_h300_c1_t0_w500_h280_c1_t0.jpg",
                "德法意瑞四国",
                "欧洲屋脊铁力士，金色山口快车，凡尔赛宫，卢浮宫专业讲解，贡多拉游船，奔驰博物馆"
        ));
        sites.add(new Site(
                "http://img1.imgtn.bdimg.com/it/u=2273217319,3184967872&fm=11&gp=0.jpg",
                "威尼斯",
                "让我门荡起双桨"
        ));
        siteListAdapter.notifyDataSetChanged();

    }

    private void initPtrFrameLayout() {
        ptrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptrFrameLayout.refreshComplete();
                    }
                }, 1800);
            }
        });
    }


    /**
     * 初始化 好友景点
     */
    private void initFriendGrid() {
        friendGridAdaper = new DiscoveryGridAdapter(friendCovers, getContext());
        friendGrid.setAdapter(friendGridAdaper);
        friendGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), StoryDetailsActivity.class);
                startActivity(intent);
            }
        });
        initFriendGridData();
    }

    private void initFriendGridData() {

        friendCovers.clear();
        //TODO
        friendCovers.add(new Cover(
                "http://pic41.nipic.com/20140531/18107775_134019262173_2.jpg",
                "巴厘岛乐园之旅",
                "http://www.qqpk.cn/Article/UploadFiles/201202/20120208134912759.jpg",
                "小小", "巴厘岛"
        ));
        friendCovers.add(new Cover(
                "http://img1.qunarzz.com/sight/p0/201403/07/e52053cbc7468d9ac4c19401b21dad34.jpg_1190x550_0344d9a4.jpg",
                "lalalala。这是个好地方",
                "http://v1.qzone.cc/avatar/201408/21/13/32/53f5845197327705.jpg!200x200.jpg",
                "Sla", "雁塔区"
        ));
        friendCovers.add(new Cover(
                "http://img1.imgtn.bdimg.com/it/u=4081927646,1723414767&fm=21&gp=0.jpg",
                "华山之巅，我愿飞翔",
                "http://v1.qzone.cc/avatar/201507/19/15/30/55ab520054839972.jpg!200x200.jpg",
                "帅到爆", "渭南"
        ));
        friendCovers.add(new Cover(
                "http://img3.fengniao.com/forum/attachpics/787/138/31467492_1024.jpg",
                "夜色摩尼",
                "http://www.feizl.com/upload2007/2011_04/110403170151167.jpg",
                "HuLaha", "雁塔区"
        ));
        friendGridAdaper.notifyDataSetChanged();
    }


    /**
     * 初始化 新发现,新景点
     */
    private void initDiscoveryGrid() {
        discoveryGridAdaper = new DiscoveryGridAdapter(discoveryCovers, getContext());
        discoveryGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), DiscoveryDetailsActivity.class);
                startActivity(intent);
            }
        });
        discoveryGrid.setAdapter(discoveryGridAdaper);
        initDiscoveryGridData();
    }

    private void initDiscoveryGridData() {
        discoveryCovers.clear();
        int offset = 0;
        int limit = 3;
        String url = Config.REQUEST_TOP_LIKE_DISCOVERIES + offset +"/"+limit+Config.FIND_TOP_STAR_SPOTS;
        Ion.with(this).load("GET",url).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                Log.i(TAG,"请求新发现结果:"+result);
            }
        });

        offset = 4;
        String url1 = Config.REQUEST_TOP_LIKE_DISCOVERIES + offset +"/"+limit+Config.FIND_TOP_STAR_SPOTS;
        Ion.with(this).load("GET",url1).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                Log.i(TAG,"请求新发现1结果:"+result);
            }
        });

        //TODO
        discoveryCovers.add(new Cover(
                "http://i0.sinaimg.cn/travel/2013/0403/U7118P704DT20130403134240.jpg",
                "在大街的那条巷子里",
                "http://img5.imgtn.bdimg.com/it/u=2593135614,3870081477&fm=21&gp=0.jpg",
                "小明", "曲江区"
        ));
        discoveryCovers.add(new Cover(
                "http://pic3.nipic.com/20090623/2743956_192919027_2.jpg",
                "身边的美食，你只是没发现而已",
                "http://h.hiphotos.baidu.com/image/h%3D200/sign=71cd4229be014a909e3e41bd99763971/472309f7905298221dd4c458d0ca7bcb0b46d442.jpg",
                "小亚茹", "雁塔区"
        ));
        discoveryCovers.add(new Cover(
                "http://img.bendibao.com/tour/201210/15/2012101594223628.JPG",
                "还是要善于发现美",
                "http://img4.duitang.com/uploads/item/201511/08/20151108131440_HvuEB.thumb.700_0.jpeg",
                "小思", "我的雁塔"
        ));
        discoveryCovers.add(new Cover(
                "http://liaoning.sinaimg.cn/2013/0331/U8386P1195DT20130331212520.jpg",
                "古城也有江南风味",
                "http://img4q.duitang.com/uploads/item/201404/14/20140414004026_H4Q8R.jpeg",
                "小波波熊", "古城江南"
        ));
        discoveryGridAdaper.notifyDataSetChanged();
    }

    /**
     * 初始化大屏广告
     */
    private void initAdBanner() {
        bannerItems.clear();
        AdBannerView.BannerItem item1 = new AdBannerView.BannerItem("带上吉他去旅行,那是艺术的澄净", "http://heilongjiang.sinaimg.cn/2011/1220/U7398P1274DT20111220011526.jpg");
        AdBannerView.BannerItem item2 = new AdBannerView.BannerItem("一路有你相伴,比什么都好", "http://rescdn.qqmail.com/dyimg/20131230/7ABD88EE2F68.jpg");
        bannerItems.add(item1);
        bannerItems.add(item2);
        adBannerView.setTitleEnabled(true);
        adBannerView.setBannerItems(bannerItems);
        adBannerView.startLoop();
        adBannerView.setOnItemClickListener(new AdBannerView.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(getContext(), "You click " + String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        });

        adBannerView.setImageLoadder(new AdBannerView.ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url) {
                Glide.with(imageView.getContext())
                        .load(url)
                        .placeholder(R.drawable.ic_loading)
                        .dontAnimate()
                        .dontTransform()
                        .into(imageView);
            }
        });

    }
}
