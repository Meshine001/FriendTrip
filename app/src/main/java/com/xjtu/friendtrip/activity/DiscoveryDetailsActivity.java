package com.xjtu.friendtrip.activity;

import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.xjtu.friendtrip.Net.CommentJson;
import com.xjtu.friendtrip.Net.Config;
import com.xjtu.friendtrip.Net.StarJson;
import com.xjtu.friendtrip.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DiscoveryDetailsActivity extends BaseActivity {

    private static final String TAG = DiscoveryDetailsActivity.class.getName();

    ImageView topRight;
    ImageView topSubRight;
    boolean likeFlag = false;


    @BindView(R.id.comment)
    EditText comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery_details);
        ButterKnife.bind(this);
        initUI();
    }

    @OnClick({R.id.comment_submit})
    void onClick(View view){
        switch (view.getId()){
            case R.id.comment_submit:
                commentDiscovery();
                break;
        }
    }



    private void initUI() {
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
