package com.xjtu.friendtrip.activity;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.xjtu.friendtrip.Net.Config;
import com.xjtu.friendtrip.Net.FollowTAJson;
import com.xjtu.friendtrip.Net.RequestUtil;
import com.xjtu.friendtrip.R;
import com.xjtu.friendtrip.bean.User;
import com.xjtu.friendtrip.util.CommonUtil;
import com.xjtu.friendtrip.util.StoreBox;
import com.xjtu.friendtrip.util.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserInfoActivity extends AppCompatActivity {

    private static final String TAG = UserInfoActivity.class.getName();
    @BindView(R.id.avatar)
    CircleImageView avatar;
    @BindView(R.id.nick)
    TextView nick;
    @BindView(R.id.fans)
    TextView fans;
    @BindView(R.id.follows)
    TextView follows;
    @BindView(R.id.follow_ta)
    TextView followTa;

    Integer userId;

    @BindView(R.id.stories_bg)
    ImageView storiesBg;
    @BindView(R.id.traces_bg)
    ImageView tracesBg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        initUI();
    }

    private void initUI() {
        getInfoFromCloud();
        UIUtils.loadImage(this,Config.ME_STORIES_BG_URL,storiesBg);
        UIUtils.loadImage(this,Config.ME_TRACES_BG_URL,tracesBg);
    }

    private void getInfoFromCloud() {
        userId = getIntent().getIntExtra("userId",0);
        Log.i(TAG," "+userId);
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
        UIUtils.loadAvatar(this,u.getProfilePhoto(),avatar);
        nick.setText(u.getNickname());
        fans.setText("粉丝:"+u.getIsFocusCount());
        follows.setText("关注:"+u.getFocusCount());
    }

    @OnClick({R.id.back,R.id.stories,R.id.traces,R.id.follow_ta_layout,R.id.fans_layout,R.id.follows_layout})
    void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.stories:
                intent.setClass(UserInfoActivity.this,MyStoriesActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
                break;
            case R.id.traces:
                intent.setClass(UserInfoActivity.this,MyTracesActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
                break;
            case R.id.follow_ta_layout:
                doFollowTa();
                break;
            case R.id.fans_layout:
                intent.setClass(UserInfoActivity.this,FansActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
                break;
            case R.id.follows_layout:
                intent.setClass(UserInfoActivity.this,FollowsActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
                break;
        }
    }

    private void doFollowTa() {
        User u = StoreBox.getUserInfo(this);
        String url = Config.FOLLOW_TA;
        String body = JSON.toJSONString(
                new FollowTAJson(
                        u.getId(),userId,CommonUtil.getCurrentTime2Sectr()
                )
        );
        Ion.with(this).load("POST",url)
                .setStringBody(body)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                             if (RequestUtil.isRequestSuccess(result)){
                                 CommonUtil.showToast(UserInfoActivity.this,"关注成功");
                                 getInfoFromCloud();
                             }
                    }
                });

    }
}
