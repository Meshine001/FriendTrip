package com.xjtu.friendtrip.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.xjtu.friendtrip.R;
import com.xjtu.friendtrip.adapter.StoryImageAdapter;
import com.xjtu.friendtrip.bean.Image;
import com.xjtu.friendtrip.widget.ExpandListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoryDetailsActivity extends BaseActivity {

    ImageView topRight;
    ImageView topSubRight;
    boolean likeFlag = false;

    @BindView(R.id.image_list)
    ExpandListView imageList;
    StoryImageAdapter imageAdapter;
    List<Image> images = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_details);
        ButterKnife.bind(this);
        initUI();

        initImageList();
    }

    private void initUI() {
        initToolbar("朋友印记");
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

    private void initImageList() {
        imageAdapter = new StoryImageAdapter(images,this);
        imageList.setAdapter(imageAdapter);
        initImageData();
    }

    private void initImageData() {
        images.add(new Image("http://img5.imgtn.bdimg.com/it/u=1589956140,1606448699&fm=21&gp=0.jpg","我爱上了这里"));
        images.add(new Image("http://img5.imgtn.bdimg.com/it/u=1589956140,1606448699&fm=21&gp=0.jpg","我爱上了这里"));
        images.add(new Image("http://img5.imgtn.bdimg.com/it/u=1589956140,1606448699&fm=21&gp=0.jpg","我爱上了这里"));
        images.add(new Image("http://img5.imgtn.bdimg.com/it/u=1589956140,1606448699&fm=21&gp=0.jpg","我爱上了这里"));
        images.add(new Image("http://img5.imgtn.bdimg.com/it/u=1589956140,1606448699&fm=21&gp=0.jpg","我爱上了这里"));
        images.add(new Image("http://img5.imgtn.bdimg.com/it/u=1589956140,1606448699&fm=21&gp=0.jpg","我爱上了这里"));
        images.add(new Image("http://img5.imgtn.bdimg.com/it/u=1589956140,1606448699&fm=21&gp=0.jpg","我爱上了这里"));
        imageAdapter.notifyDataSetChanged();
    }
}
