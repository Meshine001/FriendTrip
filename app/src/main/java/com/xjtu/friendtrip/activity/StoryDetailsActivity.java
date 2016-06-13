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
import com.xjtu.friendtrip.adapter.StoryImageAdapter;
import com.xjtu.friendtrip.bean.Comment;
import com.xjtu.friendtrip.bean.Discovery;
import com.xjtu.friendtrip.bean.Image;
import com.xjtu.friendtrip.bean.Star;
import com.xjtu.friendtrip.bean.Story;
import com.xjtu.friendtrip.bean.Text;
import com.xjtu.friendtrip.bean.User;
import com.xjtu.friendtrip.util.ActivityUtil;
import com.xjtu.friendtrip.util.CommonUtil;
import com.xjtu.friendtrip.util.StoreBox;
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

    @BindView(R.id.browse_count)
    TextView browseCount;
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

    @OnClick({R.id.avatar,R.id.comment_submit})
    void onClick(View view){
        switch (view.getId()){
            case R.id.avatar:
                ActivityUtil.startUserInfoActivity(this, details.getUserId());
                break;
            case R.id.comment_submit:
                commentStory();
                break;
        }
    }

    private void commentStory() {
        if (!StoreBox.isSomeOneHere(this)){
            showErrDialog("你还没有登录");
            return;
        }
        User u = StoreBox.getUserInfo(this);
        String body = new Gson().toJson(new CommentJson(
                u.getId(), null, comment.getText().toString().trim(), CommentJson.STORY, details.getTravelNotesid(),CommonUtil.getCurrentTime2Sectr()
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
        String url = Config.REQUEST_GET_STORY_BY_ID + details.getTravelNotesid()+Config.GET_STORY_BY_ID;
        CommonUtil.printRequest("新发现详情",url);
        Ion.with(this).load("GET",url).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                CommonUtil.printResponse(result);
                Story s = RequestUtil.requestToStory(result);
                if (s != null){
                    details = s;
                    initComment();
                }
            }
        });
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
        browseCount.setText(details.getScanCount()+"次浏览");
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
                    starStory(likeFlag);
                }else {
                    starStory(likeFlag);
                }

            }
        });
        setActionBarSubRightView(topSubRight);
    }

    private void starStory(final boolean like) {
        if (!StoreBox.isSomeOneHere(this)){
            showErrDialog("你还没有登录");
            return;
        }
        User u = StoreBox.getUserInfo(this);
        Integer tag;
        if (like) tag = StarJson.TAG_LIKE;
        else tag = StarJson.TAG_UN_LIKE;
        String body = new Gson().toJson(new StarJson(
                u.getId(), StarJson.STORY, details.getTravelNotesid(), tag
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


    private void initImageData() {
        List<Image> images = details.getTravlenotespictures();
        StoryImageAdapter  adapter = new StoryImageAdapter(images, this);
        imageList.setAdapter(adapter);
    }
}
