package com.xjtu.friendtrip.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.xjtu.friendtrip.R;
import com.xjtu.friendtrip.adapter.ImageListAdapter;
import com.xjtu.friendtrip.bean.Image;
import com.xjtu.friendtrip.widget.ExpandListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class DiscoveryActivity extends BaseActivity {

    private static final String TAG = DiscoveryActivity.class.getName() ;

    private static final int REQUEST_IMAGE = 0x0001;
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;

    protected static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102;


    @BindView(R.id.image_list)
    ExpandListView imageList;
    ImageListAdapter imageAdapter;
    List<Image> images = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);
        ButterKnife.bind(this);
        initImageList();
    }


    private void initImageList() {
        imageAdapter = new ImageListAdapter(images,this);
        imageList.setAdapter(imageAdapter);
    }

    @OnClick({R.id.add_image})
    void onClick(View view){
        switch (view.getId()){
            case R.id.add_image:
                selectImage();
                break;
        }
    }

     void selectImage() {
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                 && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                 != PackageManager.PERMISSION_GRANTED) {
             requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                     getString(R.string.permission_rationale),
                     REQUEST_STORAGE_READ_ACCESS_PERMISSION);
         } else {
            // Multi image selector form an Activity
            MultiImageSelector.create(this)
                    .showCamera(true) // show camera or not. true by default
                    .count(9) // max select image size, 9 by default. used width #.multi()
                    .multi()
                    .start(this, REQUEST_IMAGE);
        }
    }

    private void requestPermission(final String permission, String rationale, final int requestCode){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, permission)){
            new AlertDialog.Builder(this)
                    .setTitle(R.string.permission_dialog_title)
                    .setMessage(rationale)
                    .setPositiveButton(R.string.permission_dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(DiscoveryActivity.this, new String[]{permission}, requestCode);
                        }
                    })
                    .setNegativeButton(R.string.permission_dialog_cancel, null)
                    .create().show();
        }else{
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_STORAGE_READ_ACCESS_PERMISSION){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                selectImage();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
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
                for (String path:paths){
                    Image img = new Image(path);
                    images.add(img);
                }

                imageAdapter.notifyDataSetChanged();

            }
        }
    }
}
