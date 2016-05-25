package com.xjtu.friendtrip.activity;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.theartofdev.edmodo.cropper.CropImage;
import com.xjtu.friendtrip.R;
import com.xjtu.friendtrip.fragment.FriendFragment;
import com.xjtu.friendtrip.fragment.HomeFragment;
import com.xjtu.friendtrip.fragment.MeFragment;
import com.xjtu.friendtrip.fragment.ShareFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.majiajie.pagerbottomtabstrip.Controller;
import me.majiajie.pagerbottomtabstrip.PagerBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectListener;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getName();
    /**
     * 底部菜单
     */
    @BindView(R.id.bottom)
    PagerBottomTabLayout bottomTabLayout;
    Controller controller;
    List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initBottom();
    }


    /**
     * 初始化底部TAB
     */
    private void initBottom() {
        mFragments = new ArrayList<>();
        mFragments.add(new HomeFragment());
        mFragments.add(new FriendFragment());
        mFragments.add(new ShareFragment());
        mFragments.add(new MeFragment());

        controller = bottomTabLayout.builder()
                .addTabItem(android.R.drawable.ic_menu_camera, "首页")
                .addTabItem(android.R.drawable.ic_menu_compass, "驴圈")
                .addTabItem(android.R.drawable.ic_menu_search, "分享")
                .addTabItem(android.R.drawable.ic_menu_help, "我")
                .build();
        controller.addTabItemClickListener(tabItemListener);
    }

    OnTabItemSelectListener tabItemListener = new OnTabItemSelectListener() {
        @Override
        public void onSelected(int index, Object tag) {
            Log.i(TAG,"onSelected:"+index+"   TAG: "+tag.toString());

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.push_up_in,R.anim.push_up_out);
            transaction.replace(R.id.frameLayout,mFragments.get(index));
            transaction.commit();
        }

        @Override
        public void onRepeatClick(int index, Object tag) {
            Log.i(TAG,"onRepeatClick:"+index+"   TAG: "+tag.toString());
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            mFragments.get(2).onActivityResult(requestCode,resultCode,data);
        }
    }
}
