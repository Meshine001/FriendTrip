package com.xjtu.friendtrip.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.xjtu.friendtrip.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiscoveryDetailsActivity extends BaseActivity {

    ImageView topRight;
    ImageView topSubRight;
    boolean likeFlag = false;


    @BindView(R.id.comment)
    EditText comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery_details);
        ButterKnife.bind(this);
        initUI();
    }

    private void initUI() {
        initToolbar("发现详情");
        topRight = new ImageView(this);
        topRight.setImageResource(R.drawable.ic_share);
        topRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DiscoveryDetailsActivity.this,"分享",Toast.LENGTH_SHORT).show();
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
}
