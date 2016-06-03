package com.xjtu.friendtrip.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.xjtu.friendtrip.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        initToolbar("设置");
    }

    @OnClick({R.id.user_info_settings})
    void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.user_info_settings:
                intent.setClass(SettingsActivity.this,UserInfoSettings.class);
                break;
        }

        startActivity(intent);
    }
}
