package com.xjtu.friendtrip.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xjtu.friendtrip.R;
import com.xjtu.friendtrip.adapter.StoryImageAdapter;
import com.xjtu.friendtrip.bean.Image;
import com.xjtu.friendtrip.widget.ExpandListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoryDetailsActivity extends BaseActivity {


    @BindView(R.id.image_list)
    ExpandListView imageList;
    StoryImageAdapter imageAdapter;
    List<Image> images = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_details);
        ButterKnife.bind(this);
        initToolbar("朋友印记");

        initImageList();
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
