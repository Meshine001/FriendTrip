package com.xjtu.friendtrip.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.xjtu.friendtrip.Net.Config;
import com.xjtu.friendtrip.Net.RequestUtil;
import com.xjtu.friendtrip.R;
import com.xjtu.friendtrip.activity.LoginActivity;
import com.xjtu.friendtrip.activity.MessageActivity;
import com.xjtu.friendtrip.activity.MyStoriesActivity;
import com.xjtu.friendtrip.activity.MyTracesActivity;
import com.xjtu.friendtrip.activity.SettingsActivity;
import com.xjtu.friendtrip.bean.User;
import com.xjtu.friendtrip.util.CommonUtil;
import com.xjtu.friendtrip.util.PrefUtils;
import com.xjtu.friendtrip.util.StoreBox;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ming on 2016/5/25.
 */
public class MeFragment extends Fragment {
    private static final String TAG = MeFragment.class.getName();
    @BindView(R.id.message)
    RelativeLayout message;
    @BindView(R.id.message_dot)
    ImageView messageDot;

    @BindView(R.id.settings)
    RelativeLayout settings;

    @BindView(R.id.avatar)
    CircleImageView avatar;

    @BindView(R.id.nick)
    TextView nick;

    @BindView(R.id.fans)
    TextView fans;
    @BindView(R.id.follows)
    TextView follows;

    @BindView(R.id.stories)
    RelativeLayout stories;
    @BindView(R.id.traces)
    RelativeLayout traces;

    @BindView(R.id.stories_bg)
    ImageView storiesBg;
    @BindView(R.id.traces_bg)
    ImageView tracesBg;


    User myInfo;

    boolean isLogin = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        checkUser();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        ButterKnife.bind(this, view);
        checkUser();
        initUI();
        return view;
    }

    private void checkUser(){
        isLogin = StoreBox.isSomeOneHere(getContext());
        if (isLogin){
            myInfo = StoreBox.getUserInfo(getContext());
        }
    }

    /**
     *
     */
    private void initUI() {
        initMessageBox();
        initUserProfile();
        initDiscovery();
        initStory();
        initTrace();


    }

    private void initMessageBox() {

    }

    private void initUserProfile() {
        if (!isLogin) {
            Log.i(TAG,"用户未登录");
            resetUserInfo();
        } else {
            Log.i(TAG,"用户已登录：+\n"+myInfo.toString());
            updateProfile(myInfo);
            updateFromCloud();
        }
    }

    private void initTrace(){

    }
    private void initStory() {
        Glide.with(this)
                .load(Config.ME_STORIES_BG_URL)
                .placeholder(R.drawable.ic_loading)
                .dontAnimate()
                .dontTransform()
                .into(storiesBg);
    }

    private void initDiscovery() {
        Glide.with(this)
                .load(Config.ME_TRACES_BG_URL)
                .placeholder(R.drawable.ic_loading)
                .dontAnimate()
                .dontTransform()
                .into(tracesBg);
    }

    @OnClick({R.id.message, R.id.settings, R.id.avatar, R.id.nick, R.id.stories, R.id.traces})
    void onClick(View view) {

        if (!StoreBox.isSomeOneHere(getContext())) {
            gotoLogin();
            return;
        }

        Intent intent = new Intent();

        switch (view.getId()) {
            case R.id.message:
                intent.setClass(getContext(), MessageActivity.class);
                startActivity(intent);
                break;
            case R.id.settings:
                intent.setClass(getContext(), SettingsActivity.class);
                startActivityForResult(intent,SettingsActivity.REQUEST_SETTINGS);
                break;
            case R.id.avatar:
                break;
            case R.id.nick:
                break;
            case R.id.stories:
                intent.setClass(getContext(), MyStoriesActivity.class);
                intent.putExtra("userId",myInfo.getId());
                startActivity(intent);
                break;
            case R.id.traces:
                intent.setClass(getContext(), MyTracesActivity.class);
                startActivity(intent);
                break;
        }


    }

    private void resetUserInfo() {
        nick.setText("现在登陆");
        fans.setText("粉丝:0");
        follows.setText("关注:0");
    }

    /**
     * 从云端更新
     */
    private void updateFromCloud() {
        Integer userId = myInfo.getId();
        String url  = Config.USER_INFO + userId;
        CommonUtil.printRequest("获取用户信息",url);
        Ion.with(this).load("GET",url).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                User u = RequestUtil.requestToUser(result);
                updateProfile(u);
            }
        });
    }

    /**
     *
     * @param u
     */
    private void updateProfile(User u) {
        Glide.with(this)
                .load(u.getProfilePhoto())
                .placeholder(R.drawable.ic_loading)
                .dontAnimate()
                .dontTransform()
                .into(avatar);
        nick.setText(u.getNickname());
        fans.setText("粉丝:"+u.getIsFocusCount());
        follows.setText("关注:"+u.getFocusCount());

        saveUserInfo(u);
    }

    private void saveUserInfo(User u) {
        StoreBox.saveUserInfo(getContext(),u);
    }

    private void gotoLogin() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivityForResult(intent,LoginActivity.REQUEST_LOGIN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //登录界面返回
        if (requestCode == LoginActivity.REQUEST_LOGIN && resultCode == Activity.RESULT_OK){
           stateChanges();
        }

        //设置界面返回
        if (requestCode == SettingsActivity.REQUEST_SETTINGS && resultCode == SettingsActivity.SETTINGS_UPDATED){
            stateChanges();
        }
    }

    private void stateChanges(){
        checkUser();
        initUserProfile();
    }
}
