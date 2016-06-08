package com.xjtu.friendtrip.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.xjtu.friendtrip.Net.Config;
import com.xjtu.friendtrip.Net.RequestUtil;
import com.xjtu.friendtrip.R;
import com.xjtu.friendtrip.adapter.MyStoryListAdapter;
import com.xjtu.friendtrip.adapter.TimeLineAdapter;
import com.xjtu.friendtrip.bean.Story;
import com.xjtu.friendtrip.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyStoriesActivity extends BaseActivity {

    @BindView(R.id.recycler_list)
    RecyclerView recyclerList;
    MyStoryListAdapter storyListAdapter;
    List<Story> stories = new ArrayList<>();

    Integer userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_stories);
        ButterKnife.bind(this);
        initRecyclerList();
        initData();
    }

    private void initData() {
        userId = getIntent().getIntExtra("userId",0);
        String url = Config.REQUEST_STORIES_BY_USER+ userId+Config.GET_NOTES_BY_USER_ID;
        CommonUtil.printRequest("心情集",url);
        Ion.with(this).load("GET",url).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                CommonUtil.printResponse(result);
                stories.addAll(RequestUtil.requestToStories(result));
                storyListAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initRecyclerList() {
        recyclerList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerList.setLayoutManager(linearLayoutManager);
        storyListAdapter = new MyStoryListAdapter(stories,this);
        storyListAdapter.setItemClickListenser(new MyStoryListAdapter.ItemClickListenser() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MyStoriesActivity.this,StoryDetailsActivity.class);
                intent.putExtra("story",stories.get(position));
                startActivity(intent);
            }
        });
        storyListAdapter.setItemLongClickListenser(new MyStoryListAdapter.ItemLongClickListenser() {
            @Override
            public void onItemLongClick(View view, int position) {
                Intent intent = new Intent(MyStoriesActivity.this, StoryDetailsActivity.class);
                intent.putExtra("story",stories.get(position));
                startActivity(intent);
            }
        });

        recyclerList.setAdapter(storyListAdapter);
    }
}
