package com.xjtu.friendtrip.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.tencent.upload.UploadManager;
import com.theartofdev.edmodo.cropper.CropImage;
import com.xjtu.friendtrip.Net.AddDiscoveryJson;
import com.xjtu.friendtrip.Net.Config;
import com.xjtu.friendtrip.Net.RequestUtil;
import com.xjtu.friendtrip.Net.Tencent;
import com.xjtu.friendtrip.R;
import com.xjtu.friendtrip.adapter.ImageListAdapter;
import com.xjtu.friendtrip.bean.CustomLocation;
import com.xjtu.friendtrip.bean.Image;
import com.xjtu.friendtrip.bean.User;
import com.xjtu.friendtrip.util.CommonUtil;
import com.xjtu.friendtrip.util.PrefUtils;
import com.xjtu.friendtrip.util.StoreBox;
import com.xjtu.friendtrip.widget.ExpandListView;

import java.io.File;
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
    int positionSeed = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);
        initDialog(this);
        initToolbar("新发现");
        ButterKnife.bind(this);
        time.setText(CommonUtil.getCurrentTime2Sectr());
        initImageList();
    }


    private void initImageList() {
        imageAdapter = new ImageListAdapter(images,this);
        imageList.setAdapter(imageAdapter);
    }

    @OnClick({R.id.add_image,R.id.share,R.id.location})
    void onClick(View view){
        switch (view.getId()){
            case R.id.add_image:
                selectImage();
                break;
            case R.id.share:
                shareDiscovery();
                break;
            case R.id.location:
                getLocation();
                break;
        }
    }

    private void getLocation() {
        Intent intent = new Intent(DiscoveryActivity.this,LocationActivity.class);
        startActivityForResult(intent,LocationActivity.GET_LOCATION);
    }



    private void shareDiscovery() {
        showProgressDialog();
        final User u = StoreBox.getUserInfo(this);
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case Tencent.UPLOAD_SUCCESS:
                        if (msg.arg1 == images.size()){

                            String body = new Gson().toJson(new AddDiscoveryJson(
                                    "未命名",discovery.getText().toString().trim(),
                                    u.getId(),
                                    myLoc.getLat(),
                                    myLoc.getLon(),
                                    images,
                                    myLoc.getName(),
                                    time.getText().toString()
                            ));
                            CommonUtil.printRequest("添加新发现",body);
                            Ion.with(DiscoveryActivity.this).load("POST", Config.ADD_DISCOVERY).setStringBody(body).asString().setCallback(new FutureCallback<String>() {
                                @Override
                                public void onCompleted(Exception e, String result) {
                                    CommonUtil.printResponse(result);
                                    dismissProgressDialog();
                                    if (RequestUtil.isRequestSuccess(result)){
                                        CommonUtil.showToast(DiscoveryActivity.this,"添加成功!");
                                    }else {
                                        CommonUtil.showToast(DiscoveryActivity.this,"添加失败!");
                                    }
                                }
                            });
                        }
                        break;
                    case Tencent.UPLOAD_FAILED:
                        CommonUtil.showToast(DiscoveryActivity.this,"上传图片失败");
                        dismissProgressDialog();
                        break;
                }
            }
        };

        UploadManager manager = Tencent.getUploadManager(this);
            
        for (int i=0;i<images.size();i++){
            final Image img = images.get(i);
            final int finalI = i+1;
            Tencent.uploadPic(manager,img.getImagePath(),new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what){
                        case Tencent.UPLOAD_SUCCESS:
                            String url = (String) msg.obj;
                            img.setUrl(url);
                            img.setPosition(finalI);
                            Message message = new Message();
                            message.what = Tencent.UPLOAD_SUCCESS;
                            message.arg1 = finalI;
                            handler.sendMessage(message);
                            break;
                        case Tencent.UPLOAD_FAILED:
                            Message message1 = new Message();
                            message1.what = Tencent.UPLOAD_FAILED;
                            message1.arg1 = finalI;
                            handler.sendMessage(message1);
                            break;
                    }
                }
            });
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
