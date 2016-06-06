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
import com.xjtu.friendtrip.bean.Discovery;
import com.xjtu.friendtrip.bean.Image;
import com.xjtu.friendtrip.bean.User;
import com.xjtu.friendtrip.util.LocationUtil;
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

    @BindView(R.id.date_time)
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
        initUI();
    }

    @OnClick({R.id.comment_submit,R.id.avatar})
    void onClick(View view){
        switch (view.getId()){
            case R.id.comment_submit:
                commentDiscovery();
                break;
            case R.id.avatar:
                Intent userInfoIntent = new Intent(DiscoveryDetailsActivity.this,UserInfoActivity.class);
                startActivity(userInfoIntent);
                break;
        }
    }



    private void initUI() {
        initTop();
        initMap();
        initDetails();
    }

    private void initDetails() {
        details = (Discovery) getIntent().getSerializableExtra("discovery");
        String url = Config.USER_INFO + details.getUserid();
        Ion.with(this).load("GET",url).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                User u = RequestUtil.requestToUser(result);
                Glide.with(DiscoveryDetailsActivity.this)
                        .load(u.getProfilePhoto())
                        .placeholder(R.drawable.ic_loading)
                        .dontAnimate()
                        .dontTransform()
                        .into(avatar);
                nick.setText(u.getNickname());
            }
        });
        location.setText(details.getLocation());
        dateTime.setText(details.getDatetime());
        description.setText(details.getSummary());

        initMapData();
        initImageData();

    }

    private void initImageData() {
        List<Image> images = details.getPictures();

    }

    private void initMapData() {
        BitmapDescriptor   bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.ic_trace_recorder);
        LocationUtil.addMarker(bitmap,details.getLatitude(),details.getLongitude(),map);
    }

    private void initMap() {
        mapFragment = new SupportMapFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.map_frame,mapFragment).commit();
        new Handler().postDelayed(new Runnable(){
            public void run() {
                //execute the task
                map = mapFragment.getBaiduMap();
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
                Toast.makeText(DiscoveryDetailsActivity.this,"分享",Toast.LENGTH_SHORT).show();
            }
        });
        setActionBarRightView(topRight);

        topSubRight = new ImageView(this);
        topSubRight.setImageResource(R.drawable.ic_like);
        topSubRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeFlag = !likeFlag;
                if (likeFlag){
                    topSubRight.setImageResource(R.drawable.ic_like_filled);
                    starDiscovery(likeFlag);
                }else {
                    topSubRight.setImageResource(R.drawable.ic_like);
                    starDiscovery(likeFlag);
                }

            }
        });
        setActionBarSubRightView(topSubRight);
    }

    private void starDiscovery(boolean like) {
        String body = new Gson().toJson(new StarJson(
                1203,0,8
        ));
        Log.i(TAG,"点赞请求:"+body);
        Ion.with(this).load("POST", Config.REQUEST_STAR).setStringBody(body).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                Log.i(TAG,"点赞请求结果:"+result);
            }
        });
    }

    private void commentDiscovery() {
        String body = new Gson().toJson(new CommentJson(
                1203,1203,"写的好",0,8
        ));
        Log.i(TAG,"评论请求:"+body);
        Ion.with(this).load("POST", Config.REQUEST_COMMENT).setStringBody(body).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                Log.i(TAG,"评论请求结果:"+result);
            }
        });
    }
}
