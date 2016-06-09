package com.xjtu.friendtrip.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xjtu.friendtrip.R;
import com.xjtu.friendtrip.util.StoreBox;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends BaseActivity {

    public static final int REQUEST_SETTINGS = 0;
    public static final int SETTINGS_UPDATED = 1;

    @BindView(R.id.exit)
    Button exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        initToolbar("设置");
    }

    @OnClick({R.id.user_info_settings,R.id.exit})
    void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.user_info_settings:
                intent.setClass(SettingsActivity.this,UserInfoSettings.class);
                startActivity(intent);
                break;
            case R.id.exit:
                StoreBox.clearUserInfo(this);
                setResult(SETTINGS_UPDATED);
                finish();
                break;
        }

    }
}
