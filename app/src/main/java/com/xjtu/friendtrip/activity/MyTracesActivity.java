package com.xjtu.friendtrip.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.xjtu.friendtrip.Net.Config;
import com.xjtu.friendtrip.Net.RequestUtil;
import com.xjtu.friendtrip.R;
import com.xjtu.friendtrip.util.CommonUtil;

import butterknife.ButterKnife;

public class MyTracesActivity extends BaseActivity {


    Integer userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_traces);
        ButterKnife.bind(this);
        initToolbar("游记集");
        initData();
    }

    private void initData() {
        userId = getIntent().getIntExtra("userId",0);
        //TODO URL 不明确
        String url = Config.REQUEST_TRACES_BY_USER+ userId+Config.GET_DETAILS_NOTES_BY_USER_ID;
        CommonUtil.printRequest("游记集",url);
        Ion.with(this).load("GET",url).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                CommonUtil.printResponse(result);
            }
        });
    }
}
