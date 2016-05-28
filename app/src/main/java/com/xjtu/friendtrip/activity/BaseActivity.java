package com.xjtu.friendtrip.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xjtu.friendtrip.R;
import com.xjtu.friendtrip.util.AppManager;

import butterknife.ButterKnife;

/**
 * Created by Ming on 2016/5/24.
 */
public class BaseActivity extends AppCompatActivity {
    ActionBar actionBar;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    protected void initToolbar(String title) {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        View view = getLayoutInflater().inflate(R.layout.view_topbar, null);
        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView tvTitle = (TextView) view.findViewById(R.id.title);
        tvTitle.setText(title);

        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT);
                // Get a support ActionBar corresponding to this toolbar
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(view, params);
    }


}
