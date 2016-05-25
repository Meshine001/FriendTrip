package com.xjtu.friendtrip.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.tencent.upload.Const;
import com.tencent.upload.UploadManager;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.xjtu.friendtrip.Net.Tencent;
import com.xjtu.friendtrip.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by Ming on 2016/5/25.
 */
public class ShareFragment extends Fragment {

    private static final int REQUEST_IMAGE = 0x0001;

    List<Uri> uploadsUri = new ArrayList<>();

    @BindView(R.id.image)
    ImageView imageView;
    @BindView(R.id.button)
    Button button;


    @OnClick({R.id.button, R.id.image})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                UploadManager manager = new UploadManager(getContext(), Tencent.APPID, Const.FileType.Photo, null);
                break;
            case R.id.image:
                selectImage();

                break;
        }


    }

    public void selectImage() {
        if (CropImage.isExplicitCameraPermissionRequired(getContext())) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE);
        } else {
            // Multi image selector form an Activity
            MultiImageSelector.create(getContext())
                    .showCamera(true) // show camera or not. true by default
                    .count(1) // max select image size, 9 by default. used width #.multi()
                    .single() // single mode
                    .start(this, REQUEST_IMAGE);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //选择图片返回
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                // Get the result list of select image paths
                List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                // do your logic ....
                startCropImageActivity(Uri.fromFile(new File(path.get(0))));
            }
        }

        // handle the crop image uri
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK) {
                Uri resultUri = result.getUri();
                imageView.setImageURI(resultUri);
                Log.i("sssss",resultUri.toString());
                uploadsUri.add(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.i("sssss",error.toString());
                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setMinCropResultSize(100,100)
                .setMaxCropResultSize(1000,1000)
                .start(getActivity());
    }


    /**
     * Activity 传数据进来
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void setActivityResult(int requestCode, int resultCode, Intent data){
        onActivityResult(requestCode,resultCode,data);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


}
