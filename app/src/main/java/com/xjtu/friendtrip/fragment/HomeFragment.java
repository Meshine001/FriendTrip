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
import com.xjtu.friendtrip.Net.RequestUtil;
import com.xjtu.friendtrip.R;
import com.xjtu.friendtrip.activity.AllDiscoveryActivity;
import com.xjtu.friendtrip.activity.AllFriendItemsActivity;
import com.xjtu.friendtrip.activity.DiscoveryDetailsActivity;
import com.xjtu.friendtrip.activity.StoryDetailsActivity;
import com.xjtu.friendtrip.adapter.DiscoveryGridAdapter;
import com.xjtu.friendtrip.adapter.SiteListAdapter;
import com.xjtu.friendtrip.adapter.StoryGridAdapter;
import com.xjtu.friendtrip.bean.Cover;
import com.xjtu.friendtrip.bean.Discovery;
import com.xjtu.friendtrip.bean.Site;
import com.xjtu.friendtrip.bean.Story;
import com.xjtu.friendtrip.util.CommonUtil;
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


    @BindView(R.id.ad_banner)
    AdBannerView adBannerView;
    List<AdBannerView.BannerItem> bannerItems = new ArrayList<>();

    @BindView(R.id.discovery_grid)
    ExpandGridView discoveryGrid;
    List<Discovery> discoveryCovers = new ArrayList<>();
    DiscoveryGridAdapter discoveryGridAdaper;

    @BindView(R.id.friend_grid)
    ExpandGridView friendGrid;
    List<Story> friendCovers = new ArrayList<>();
    StoryGridAdapter friendGridAdaper;

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




    /**
     * 初始化 好友心情
     */
    private void initFriendGrid() {
        friendGridAdaper = new StoryGridAdapter(friendCovers, getContext());
        friendGrid.setAdapter(friendGridAdaper);
        friendGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), StoryDetailsActivity.class);
                intent.putExtra("story",friendCovers.get(position));
                startActivity(intent);
            }
        });
        initFriendGridData();
    }

    private void initFriendGridData() {


        int offset = 0;
        int limit = 4;

        String url = Config.REQUEST_TOP_STORIES+ offset +"/"+limit+Config.FIND_TOP_STAR_NOTES;
        CommonUtil.printRequest("朋友心情",url);
        Ion.with(this).load("GET",url).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                CommonUtil.printResponse(result);
                friendCovers.clear();
                friendCovers.addAll(RequestUtil.requestToStories(result));
                friendGridAdaper.notifyDataSetChanged();
            }
        });

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
                intent.putExtra("discovery",discoveryCovers.get(position));
                startActivity(intent);
            }
        });
        discoveryGrid.setAdapter(discoveryGridAdaper);
        initDiscoveryGridData();
    }

    private void initDiscoveryGridData() {
        int offset = 0;
        int limit = 4;
        String url = Config.REQUEST_TOP_LIKE_DISCOVERIES + offset +"/"+limit+Config.FIND_TOP_STAR_SPOTS;
        CommonUtil.printRequest("新发现",url);
        Ion.with(this).load("GET",url).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                CommonUtil.printResponse(result);
                discoveryCovers.clear();
                discoveryCovers.addAll(RequestUtil.requestToDiscoveries(result));
                discoveryGridAdaper.notifyDataSetChanged();
            }
        });
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
