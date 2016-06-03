package com.xjtu.friendtrip.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xjtu.friendtrip.R;

import butterknife.ButterKnife;

public class UserInfoSettings extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_settings);
        ButterKnife.bind(this);
        initToolbar("账户设置");
    }
}
