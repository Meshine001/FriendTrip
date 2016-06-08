package com.xjtu.friendtrip.activity;

import android.content.Intent;
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
import com.baidu.mapapi.map.SupportMapFragment;
import com.bumptech.glide.Glide;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.xjtu.friendtrip.Net.Config;
import com.xjtu.friendtrip.Net.RequestUtil;
import com.xjtu.friendtrip.R;
import com.xjtu.friendtrip.adapter.CommentListAdapter;
import com.xjtu.friendtrip.adapter.DetailsImageListAdapter;
import com.xjtu.friendtrip.adapter.StoryImageAdapter;
import com.xjtu.friendtrip.bean.Comment;
import com.xjtu.friendtrip.bean.Image;
import com.xjtu.friendtrip.bean.Star;
import com.xjtu.friendtrip.bean.Story;
import com.xjtu.friendtrip.bean.User;
import com.xjtu.friendtrip.util.ActivityUtil;
import com.xjtu.friendtrip.util.CommonUtil;
import com.xjtu.friendtrip.widget.ExpandListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class StoryDetailsActivity extends BaseActivity {

    private static final String TAG = StoryDetailsActivity.class.getName() ;
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



    Story details;


    @OnClick({R.id.avatar})
    void onClick(View view){
        switch (view.getId()){
            case R.id.avatar:
                Intent userInfoIntent = new Intent(StoryDetailsActivity.this,UserInfoActivity.class);
                startActivity(userInfoIntent);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_details);
        ButterKnife.bind(this);
        initUI();
    }

    private void initUI() {
        initTop();
        initDetails();
    }

    private void initDetails() {
        details = (Story) getIntent().getSerializableExtra("story");
        Log.i(TAG, details.toString());
        String url = Config.USER_INFO + details.getUserId();
        Ion.with(this).load("GET", url).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                User u = RequestUtil.requestToUser(result);
                Glide.with(StoryDetailsActivity.this)
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
        description.setText(details.getDiscription());
        initImageData();
        initComment();
    }

    private void initComment() {
        int width = CommonUtil.getScreenWidth(this);
        int avaterSize = width / 7;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(avaterSize, avaterSize);
        likeCount.setText("" + details.getStarCount());
        for (final Star s : details.getStarses()) {
            CircleImageView iv = new CircleImageView(this);
            iv.setLayoutParams(lp);
            Glide.with(this)
                    .load(s.getProfilePhoto())
                    .placeholder(R.drawable.ic_loading)
                    .dontAnimate()
                    .dontTransform()
                    .into(iv);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO
                    ActivityUtil.startUserInfoActivity(StoryDetailsActivity.this, s.getId());//s的id是userId吗？
                }
            });
            likeLayout.addView(iv);
        }

        List<Comment> comments = details.getCommentses();
        CommentListAdapter adapter = new CommentListAdapter(comments,this);
        commentList.setAdapter(adapter);
    }

    private void initTop() {
        initToolbar("朋友");
        topRight = new ImageView(this);
        topRight.setImageResource(R.drawable.ic_share);
        topRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StoryDetailsActivity.this,"分享",Toast.LENGTH_SHORT).show();
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
                }else {
                    topSubRight.setImageResource(R.drawable.ic_like);
                }

            }
        });
        setActionBarSubRightView(topSubRight);
    }


    private void initImageData() {
        List<Image> images = details.getTravlenotespictures();
        StoryImageAdapter  adapter = new StoryImageAdapter(images, this);
        imageList.setAdapter(adapter);
    }
}
