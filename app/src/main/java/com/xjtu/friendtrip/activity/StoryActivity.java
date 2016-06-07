package com.xjtu.friendtrip.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.theartofdev.edmodo.cropper.CropImage;
import com.xjtu.friendtrip.Net.AddDiscoveryJson;
import com.xjtu.friendtrip.Net.AddSotryJson;
import com.xjtu.friendtrip.Net.Config;
import com.xjtu.friendtrip.Net.RequestUtil;
import com.xjtu.friendtrip.R;
import com.xjtu.friendtrip.adapter.ImageListAdapter;
import com.xjtu.friendtrip.bean.CustomLocation;
import com.xjtu.friendtrip.bean.Image;
import com.xjtu.friendtrip.bean.Story;
import com.xjtu.friendtrip.bean.StoryFile;
import com.xjtu.friendtrip.bean.User;
import com.xjtu.friendtrip.util.CommonUtil;
import com.xjtu.friendtrip.util.StoreBox;
import com.xjtu.friendtrip.widget.ExpandListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class StoryActivity extends BaseActivity {
    private static final String TAG = DiscoveryActivity.class.getName() ;

    private static final int REQUEST_IMAGE = 0x0001;
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;

    protected static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102;


    @BindView(R.id.location)
    TextView location;

    CustomLocation myLoc;

    @BindView(R.id.time)
    TextView time;

    @BindView(R.id.discovery)
    EditText discovery;

    @BindView(R.id.image_list)
    ExpandListView imageList;
    ImageListAdapter imageAdapter;
    List<Image> images = new ArrayList<>();

    @BindView(R.id.auth_icon)
    ImageView authIcon;
    @BindView(R.id.auth_text)
    TextView authText;
    int[] auths = {Story.AUTH_WORLD,Story.AUTH_FRIENDS,Story.AUTH_SELF};
    int[] icons = {R.drawable.ic_world_visible,R.drawable.ic_friends_visible,R.drawable.ic_self_visible};
    String[] authTexts = {"全部可见","好友可见","个人可见"};
    int curAuthIndex = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        initToolbar("新心情");
        ButterKnife.bind(this);
        initImageList();
    }


    private void initImageList() {
        imageAdapter = new ImageListAdapter(images,this);
        imageList.setAdapter(imageAdapter);
    }

    @OnClick({R.id.add_image,R.id.share,R.id.location,R.id.auth_layout})
    void onClick(View view){
        switch (view.getId()){
            case R.id.add_image:
                selectImage();
                break;
            case R.id.share:
                shareStory();
                break;
            case R.id.location:
                getLocation();
                break;
            case R.id.auth_layout:
                changeAuth();
                break;
        }
    }

    private void changeAuth() {
        curAuthIndex++;
        if (curAuthIndex == 3)curAuthIndex=0;
        authIcon.setImageResource(icons[curAuthIndex]);
        authText.setText(authTexts[curAuthIndex]);
    }

    private void getLocation() {
        Intent intent = new Intent(StoryActivity.this,LocationActivity.class);
        startActivityForResult(intent,LocationActivity.GET_LOCATION);
    }

    private void shareStory() {
        User u = StoreBox.getUserInfo(this);
        List<StoryFile> storyFiles = new ArrayList<>();
        for (Image img : images){
            storyFiles.add(new StoryFile(img.getImagePath(),img.getSummary(),StoryFile.TYPE_IMEG));
        }
        String body = new Gson().toJson(new AddSotryJson(
            discovery.getText().toString().trim(),
                myLoc.getName(),
                time.getText().toString().trim(),
                u.getId(),
                myLoc.getLat(),
                myLoc.getLon(),storyFiles
        )

        );
        CommonUtil.printRequest("添加新发现",body);
        Ion.with(this).load("POST", Config.ADD_DISCOVERY).setStringBody(body).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                CommonUtil.printResponse(result);
                if (RequestUtil.isRequestSuccess(result)){
                    CommonUtil.showToast(StoryActivity.this,"添加成功!");
                }else {
                    CommonUtil.showToast(StoryActivity.this,"添加失败!");
                }
            }
        });

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
                    .single()
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
                            ActivityCompat.requestPermissions(StoryActivity.this, new String[]{permission}, requestCode);
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
                    startCropImageActivity(Uri.fromFile(new File(path)));
                }


            }
        }

        // handle the crop image uri
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK) {
                Uri resultUri = result.getUri();
                Image img = new Image(resultUri.getPath());
                images.add(img);
                imageAdapter.notifyDataSetChanged();
                Log.i(TAG, resultUri.toString());
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.i(TAG, error.toString());
            }
        }

        if (requestCode == LocationActivity.GET_LOCATION){
            Log.i(TAG,"ResultCode:"+resultCode);
            if (resultCode > 0){
                myLoc = (CustomLocation) data.getSerializableExtra("data");
                location.setText(myLoc.getName());
                Log.i(TAG,"获得位置:"+myLoc);
            }

        }
    }


    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .start(this);
    }


}
