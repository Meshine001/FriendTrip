package com.xjtu.friendtrip.activity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;
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

    HomeFragment homeFragment;
    FriendFragment friendFragment;
    MeFragment meFragment;


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

        controller = bottomTabLayout.builder()
                .addTabItem(android.R.drawable.ic_menu_camera, "首页")
                .addTabItem(android.R.drawable.ic_menu_compass, "驴圈")
                .addTabItem(android.R.drawable.ic_menu_search, "分享")
                .addTabItem(android.R.drawable.ic_menu_help, "我")
                .build();
        controller.addTabItemClickListener(tabItemListener);
    }

    Class[] shareItems = {DiscoveryActivity.class, StoryActivity.class, TraceActivity.class};

    void showShareDialog() {

        final String[] stringItems = {"新发现", "新心情", "新游记"};

        final ActionSheetDialog dialog = new ActionSheetDialog(this, stringItems, null);
        dialog.isTitleShow(false).show();


        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "position:" + position);
                Intent intent = new Intent(MainActivity.this, shareItems[position]);
                startActivity(intent);
                dialog.dismiss();
            }
        });

    }

    int currTab = 0;

    OnTabItemSelectListener tabItemListener = new OnTabItemSelectListener() {
        @Override
        public void onSelected(int index, Object tag) {
            Log.i(TAG, "onSelected:" + index + "   TAG: " + tag.toString());
            if (index == 2) {
                showShareDialog();
            } else {
                showTab(index);
            }

        }

        @Override
        public void onRepeatClick(int index, Object tag) {
            Log.i(TAG, "onRepeatClick:" + index + "   TAG: " + tag.toString());
            if (index == 2) {
                showShareDialog();
            }
        }
    };

    void showTab(int postion) {
       // recycleFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.push_up_in, R.anim.push_up_out);
        switch (postion) {
            case 0:
                homeFragment = new HomeFragment();
                transaction.replace(R.id.frameLayout, homeFragment);
                break;
            case 1:
                friendFragment = new FriendFragment();
                transaction.replace(R.id.frameLayout, friendFragment);
                break;
            case 2:
                break;
            case 3:
                meFragment = new MeFragment();
                transaction.replace(R.id.frameLayout, meFragment);
                break;
        }

        transaction.commit();
    }

    void recycleFragment() {
        if (homeFragment != null)
            homeFragment.onDestroy();
        if (friendFragment != null)
            friendFragment.onDestroy();
        if (meFragment != null)
            meFragment.onDestroy();
    }


}
