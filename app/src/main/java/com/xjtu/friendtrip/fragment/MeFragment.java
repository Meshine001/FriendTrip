package com.xjtu.friendtrip.fragment;

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

    boolean isViewOk = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        ButterKnife.bind(this, view);
        initUI();
        initUserInfo();

        isViewOk = true;

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

        if (!StoreBox.isSomeOneHere(getContext())) {
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
        if (!StoreBox.isSomeOneHere(getContext())) {
            resetUserInfo();
        } else {
            getUserInfo();
        }
    }

    private void resetUserInfo() {
        nick.setText("现在登陆");
        fans.setText("粉丝:0");
        follows.setText("关注:0");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isViewOk){
           getUserInfo();
        }
    }

    /**
     * 获取最新用户信息
     */
    private void getUserInfo() {
        me = StoreBox.getUserInfo(getContext());
        updateUI(me);
        updateFromCloud();
    }

    /**
     * 从云端更新
     */
    private void updateFromCloud() {
        Integer userId = me.getId();
        String url  = Config.USER_INFO + userId;
        CommonUtil.printRequest("用户信息",url);
        Ion.with(this).load("GET",url).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                User u = RequestUtil.requestToUser(result);
                updateUI(u);
            }
        });
    }

    private void updateUI(User u) {
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
        startActivity(intent);
    }

}
