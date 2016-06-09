package com.xjtu.friendtrip.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.theartofdev.edmodo.cropper.CropImage;
import com.xjtu.friendtrip.R;

import java.io.File;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class UserInfoSettings extends BaseActivity {

    public static final int REQUEST_USER_INFO = 0;
    public static final int STATE_CHANGED = 1;

    private static final int REQUEST_IMAGE = 1;
    private static final String TAG = UserInfoSettings.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_settings);
        ButterKnife.bind(this);
        initToolbar("账户设置");
    }

    @OnClick({R.id.avatar})
    void onClick(View view){
        switch (view.getId()){
            case R.id.avatar:
                selectImage();
                break;
        }
    }

    private void selectImage() {
        // Multi image selector form an Activity
        MultiImageSelector.create(this)
                .showCamera(true) // show camera or not. true by default
                .single()
                .start(this, REQUEST_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //选择图片返回
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                // Get the result list of select image paths
                List<String> paths = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                // do your logic ....
                for (String path : paths) {
                    startCropImageActivity(Uri.fromFile(new File(path)));
                }
            }
        }

        // handle the crop image uri
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK) {
                Uri resultUri = result.getUri();
                Log.i(TAG, resultUri.toString());
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.i(TAG, error.toString());
            }
        }

    }

    private void startCropImageActivity(Uri uri) {
        CropImage.activity(uri)
                .start(this);
    }
}
