package com.xjtu.friendtrip.activity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.SupportMapFragment;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.xjtu.friendtrip.Net.CommentJson;
import com.xjtu.friendtrip.Net.Config;
import com.xjtu.friendtrip.Net.RequestUtil;
import com.xjtu.friendtrip.Net.StarJson;
import com.xjtu.friendtrip.R;
import com.xjtu.friendtrip.adapter.CommentListAdapter;
import com.xjtu.friendtrip.adapter.DetailsImageListAdapter;
import com.xjtu.friendtrip.application.MyApplication;
import com.xjtu.friendtrip.bean.Comment;
import com.xjtu.friendtrip.bean.Discovery;
import com.xjtu.friendtrip.bean.Image;
import com.xjtu.friendtrip.bean.Star;
import com.xjtu.friendtrip.bean.User;
import com.xjtu.friendtrip.util.ActivityUtil;
import com.xjtu.friendtrip.util.CommonUtil;
import com.xjtu.friendtrip.util.LocationUtil;
import com.xjtu.friendtrip.util.StoreBox;
import com.xjtu.friendtrip.util.UIUtils;
import com.xjtu.friendtrip.widget.ExpandListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class DiscoveryDetailsActivity extends BaseActivity {

    private static final String TAG = DiscoveryDetailsActivity.class.getName();

    Discovery details;

    ImageView topRight;
    ImageView topSubRight;
    boolean likeFlag = false;

    @BindView(R.id.avatar)
    CircleImageView avatar;
    @BindView(R.id.nick)
    TextView nick;

    @BindView(R.id.time)
    TextView dateTime;
    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.description)
    TextView description;

    @BindView(R.id.map_frame)
    FrameLayout mapFrame;
    SupportMapFragment mapFragment;
    BaiduMap map;

    @BindView(R.id.image_list)
    ExpandListView imageList;

    @BindView(R.id.like_count)
    TextView likeCount;
    @BindView(R.id.like_layout)
    LinearLayout likeLayout;

    @BindView(R.id.comment_count)
    TextView commentCount;
    @BindView(R.id.comment_list)
    ExpandListView commentList;

    @BindView(R.id.comment)
    EditText comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery_details);
        ButterKnife.bind(this);
        initDialog(this);
        initUI();
    }

    @OnClick({R.id.comment_submit, R.id.avatar})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.comment_submit:
                commentDiscovery();
                break;
            case R.id.avatar:
                ActivityUtil.startUserInfoActivity(this, details.getUserid());
                break;
        }
    }


    private void initUI() {
        initTop();
        initDetails();
    }

    private void initDetails() {
        details = (Discovery) getIntent().getSerializableExtra("discovery");
        Log.i(TAG, details.toString());
        String url = Config.USER_INFO + details.getUserid();
        Ion.with(this).load("GET", url).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                User u = RequestUtil.requestToUser(result);
                if (u!=null){
                    UIUtils.loadAvatar(DiscoveryDetailsActivity.this,u.getProfilePhoto(),avatar);
                    nick.setText(u.getNickname());
                }
            }
        });
        location.setText(details.getLocation());
        dateTime.setText(details.getDatetime());
        description.setText(details.getSummary());

        initMap();
        initImageData();
        initComment();
    }

    public void initComment() {
        likeLayout.removeAllViewsInLayout();
        int width = CommonUtil.getScreenWidth(this);
        int avaterSize = width / 7;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(avaterSize, avaterSize);
        int margin = avaterSize/5;
        lp.setMargins(margin,margin,margin,margin);
        likeCount.setText("" + details.getStarCount());
        Integer uId = MyApplication.getUser().getId();
        for (final Star s : details.getStarses()) {
            Log.e("Star","Star:"+s.getId()+" Usr:"+MyApplication.getUser().getId());
            if (s.getId() == uId){
                Log.i(TAG,"用户已经点赞");
                topSubRight.setImageResource(R.drawable.ic_like_filled);
                likeFlag = true;
            }
            CircleImageView iv = new CircleImageView(this);
            iv.setLayoutParams(lp);
            UIUtils.loadAvatar(this,s.getProfilePhoto(),iv);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO
                    ActivityUtil.startUserInfoActivity(DiscoveryDetailsActivity.this, s.getId());
                }
            });
            likeLayout.addView(iv);
        }

        commentCount.setText(""+details.getCommentCount());
        List<Comment> comments = details.getCommentses();
        CommentListAdapter adapter = new CommentListAdapter(comments,this);
        commentList.setAdapter(adapter);

    }

    private void initImageData() {
        List<Image> images = details.getTravlenotespictures();
        DetailsImageListAdapter adapter = new DetailsImageListAdapter(images, this);
        imageList.setAdapter(adapter);
    }

    private void initMapData() {
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.ic_my_location);
        LocationUtil.addMarker(bitmap, details.getLatitude(), details.getLongitude(), map);
        LocationUtil.changeMapCenter(details.getLatitude(), details.getLongitude(), map, 18.0f);
    }

    private void initMap() {
        mapFragment = new SupportMapFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.map_frame, mapFragment).commit();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                //execute the task
                map = mapFragment.getBaiduMap();
                initMapData();
            }
        }, 2000);


    }

    private void initTop() {
        initToolbar("发现详情");
        topRight = new ImageView(this);
        topRight.setImageResource(R.drawable.ic_share);
        topRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DiscoveryDetailsActivity.this, "分享", Toast.LENGTH_SHORT).show();
            }
        });
        setActionBarRightView(topRight);

        topSubRight = new ImageView(this);
        topSubRight.setImageResource(R.drawable.ic_like);
        topSubRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeFlag = !likeFlag;
                if (likeFlag) {
                    starDiscovery(likeFlag);
                } else {
                    starDiscovery(likeFlag);
                }

            }
        });
        setActionBarSubRightView(topSubRight);
    }

    private void starDiscovery(final boolean like) {
        if (!StoreBox.isSomeOneHere(this)){
            showErrDialog("你还没有登录");
            return;
        }
        Integer tag;
        if (like) tag = StarJson.TAG_LIKE;
        else tag = StarJson.TAG_UN_LIKE;
        String body = new Gson().toJson(new StarJson(
                MyApplication.getUser().getId(), StarJson.DISCOVERY, details.getScenicid(), tag
        ));
        Log.i(TAG, "点赞请求:" + body);
        Ion.with(this).load("POST", Config.REQUEST_STAR).setStringBody(body).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                Log.i(TAG, "点赞请求结果:" + result);
                if (RequestUtil.isRequestSuccess(result)){
                    if (like){
                        topSubRight.setImageResource(R.drawable.ic_like_filled);
                    }else {
                        topSubRight.setImageResource(R.drawable.ic_like);
                    }

                }
            }
        });
    }

    private void commentDiscovery() {
        if (!StoreBox.isSomeOneHere(this)){
            showErrDialog("你还没有登录");
            return;
        }
        User u = StoreBox.getUserInfo(this);
        String body = new Gson().toJson(new CommentJson(
                u.getId(), null, comment.getText().toString().trim(), CommentJson.DISCOVERY, details.getScenicid(),CommonUtil.getCurrentTime2Sectr()
        ));
        Log.i(TAG, "评论请求:" + body);
        Ion.with(this).load("POST", Config.REQUEST_COMMENT).setStringBody(body).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                Log.i(TAG, "评论请求结果:" + result);
                if (RequestUtil.isRequestSuccess(result)){
                    refreshDetails();
                }
            }
        });
    }

    private void refreshDetails() {
        String url = Config.REQUEST_GET_SPOTS_BY_ID + details.getScenicid()+Config.GET_SPOTS_BY_ID;
        CommonUtil.printRequest("新发现详情",url);
        Ion.with(this).load("GET",url).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                CommonUtil.printResponse(result);
               Discovery dis = RequestUtil.requestToDiscovery(result);
                if (dis != null){
                    details = dis;
                    initComment();
                }
            }
        });
    }


}
