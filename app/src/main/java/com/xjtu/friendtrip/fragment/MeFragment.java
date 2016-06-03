package com.xjtu.friendtrip.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xjtu.friendtrip.Net.Config;
import com.xjtu.friendtrip.R;
import com.xjtu.friendtrip.activity.LoginActivity;
import com.xjtu.friendtrip.activity.MessageActivity;
import com.xjtu.friendtrip.activity.MyStoriesActivity;
import com.xjtu.friendtrip.activity.MyTracesActivity;
import com.xjtu.friendtrip.activity.SettingsActivity;
import com.xjtu.friendtrip.bean.User;
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
    @BindView(R.id.friends)
    TextView friends;

    @BindView(R.id.stories)
    RelativeLayout stories;
    @BindView(R.id.traces)
    RelativeLayout traces;

    @BindView(R.id.stories_bg)
    ImageView storiesBg;
    @BindView(R.id.traces_bg)
    ImageView tracesBg;


    User me;



    boolean isLogedIn = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        ButterKnife.bind(this, view);
        initUI();
        initUserInfo();
        return view;
    }

    private void initUI() {
        Glide.with(this)
                .load(Config.ME_STORIES_BG_URL)
                .placeholder(R.drawable.ic_loading)
                .dontAnimate()
                .dontTransform()
                .into(storiesBg);
        Glide.with(this)
                .load(Config.ME_TRACES_BG_URL)
                .placeholder(R.drawable.ic_loading)
                .dontAnimate()
                .dontTransform()
                .into(tracesBg);
    }

    @OnClick({R.id.message, R.id.settings, R.id.avatar, R.id.nick, R.id.stories, R.id.traces})
    void onClick(View view) {

        if (!isLogedIn) {
            gotoLogin();
            return;
        }

        Intent intent = new Intent();

        switch (view.getId()) {
            case R.id.message:
                intent.setClass(getContext(), MessageActivity.class);
            case R.id.settings:
                intent.setClass(getContext(), SettingsActivity.class);
                break;
            case R.id.avatar:
                break;
            case R.id.nick:
                break;
            case R.id.stories:
                intent.setClass(getContext(), MyStoriesActivity.class);
                break;
            case R.id.traces:
                intent.setClass(getContext(), MyTracesActivity.class);
                break;
        }

        startActivity(intent);
    }

    private void initUserInfo() {
        String username = PrefUtils.getStringPreference(getContext(), "username");
        if (username == null) {
            nick.setText("现在登陆");
        } else {
            isLogedIn = true;
            updateUserInfo();
        }
    }

    /**
     * 网络获取最新用户信息
     */
    private void updateUserInfo() {
        me = StoreBox.getUserInfo(getContext());
        Glide.with(this).load(me.getProfilePhoto())
                .placeholder(R.drawable.ic_loading)
                .dontAnimate()
                .dontTransform().into(avatar);
        nick.setText(me.getNickname());
        fans.setText("粉丝:"+me.getIsFocusCount());
        follows.setText("关注:"+me.getFocusCount());
        friends.setText("好友:"+me.getFriendsCount());

        updateFromCloud();
    }

    /**
     * 从云端更新
     */
    private void updateFromCloud() {

    }

    private void gotoLogin() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivityForResult(intent,LoginActivity.REQUEST_LOGIN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LoginActivity.REQUEST_LOGIN && resultCode >1){
            if (data.getBooleanExtra("result",false)){
                updateUserInfo();
            }
        }
    }
}
