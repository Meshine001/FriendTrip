package com.xjtu.friendtrip.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.Marker;
import com.google.gson.Gson;
import com.theartofdev.edmodo.cropper.CropImage;
import com.xjtu.friendtrip.R;
import com.xjtu.friendtrip.bean.CustomLocation;
import com.xjtu.friendtrip.bean.Image;
import com.xjtu.friendtrip.bean.Text;
import com.xjtu.friendtrip.bean.TimeLineModel;
import com.xjtu.friendtrip.listener.Locationlistener;
import com.xjtu.friendtrip.util.CommonUtil;
import com.xjtu.friendtrip.util.LocationUtil;


import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by Meshine on 16/5/29.
 */
public class TraceItemActivity extends BaseActivity {
    private static final String TAG = TraceItemActivity.class.getName();
    public static final int TIME_LINE_ITEM_EDIT = 0;
    private static final int REQUEST_IMAGE = 1;
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;

    protected static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102;

    @BindView(R.id.text_content)
    RelativeLayout textContentLayout;
    @BindView(R.id.image_content)
    RelativeLayout imageContentLayout;

    @BindView(R.id.text)
    TextView text;

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.summary)
    TextView summary;
    String imagePath;

    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.location)
    TextView location;

    LocationClient locationClient;
    BDLocationListener locationListener;

    CustomLocation myLocation;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Locationlistener.LOCATION_SUCCESS:
                    BDLocation loc = (BDLocation) msg.obj;
                    String name = loc.getAddrStr();
                    Double lat = loc.getLatitude();
                    Double lon = loc.getLongitude();
                    location.setText(name);
                    myLocation = new CustomLocation(name,lat,lon);
                    locationClient.stop();
                    break;
                case Locationlistener.LOCATION_FAILD:
                    Toast.makeText(getBaseContext(), "获取位置失败", Toast.LENGTH_SHORT).show();
                    locationClient.stop();
                    break;
            }
        }
    };

    int type = 0;

    @OnClick({R.id.update_location, R.id.image})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.update_location:
                Log.i(TAG, "updateLocation");
                Intent locIntent = new Intent(TraceItemActivity.this,LocationActivity.class);
                startActivityForResult(locIntent,LocationActivity.GET_LOCATION);
                break;
            case R.id.image:
                selectImage();
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trace_item);
        ButterKnife.bind(this);
        initUI();
        initLocation();
    }

    private void initUI() {
        initToolbar("添加印记");
        initToolbarRight();
        initContent();
    }

    private void initLocation() {
        locationListener = new Locationlistener(handler);
        locationClient = new LocationClient(this);
        locationClient.registerLocationListener(locationListener);
        LocationUtil.initLocationOptions(locationClient);
        locationClient.start();
    }

    private void initContent() {

        time.setText(CommonUtil.getCurrentTimeStr());
        //TODO
        location.setText("湖南");

        type = getIntent().getIntExtra("type", 0);
        switch (type) {
            case TimeLineModel.TYPE_TEXT:
                textContentLayout.setVisibility(View.VISIBLE);
                imageContentLayout.setVisibility(View.GONE);
                break;
            case TimeLineModel.TYPE_RECORD:
                break;
            case TimeLineModel.TYPE_IMAGE:
                selectImage();
                textContentLayout.setVisibility(View.GONE);
                imageContentLayout.setVisibility(View.VISIBLE);
                break;
        }
    }

    @BindDrawable(R.drawable.ic_ok)
    Drawable dOk;

    private void initToolbarRight() {
        ImageView iv = new ImageView(this);
        iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        iv.setImageDrawable(dOk);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                TimeLineModel timeLineModel = new TimeLineModel();
                timeLineModel.setTime(time.getText().toString().trim());
                timeLineModel.setLocation(myLocation);
                switch (type) {
                    case TimeLineModel.TYPE_TEXT:
                        Text str = new Text(text.getText().toString().trim());
                        timeLineModel.setType(TimeLineModel.TYPE_TEXT);
                        timeLineModel.setContent(str);
                        break;
                    case TimeLineModel.TYPE_RECORD:
                        break;
                    case TimeLineModel.TYPE_IMAGE:
                        Image img = new Image(imagePath, summary.getText().toString().trim());
                        timeLineModel.setContent(img);
                        timeLineModel.setType(TimeLineModel.TYPE_IMAGE);
                        break;
                }
                String str = new Gson().toJson(timeLineModel);
                Log.i(TAG,str);
                data.putExtra("data",timeLineModel);
                setResult(TIME_LINE_ITEM_EDIT,data);
                finish();
            }
        });
        setActionBarRightView(iv);
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

    private void requestPermission(final String permission, String rationale, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.permission_dialog_title)
                    .setMessage(rationale)
                    .setPositiveButton(R.string.permission_dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(TraceItemActivity.this, new String[]{permission}, requestCode);
                        }
                    })
                    .setNegativeButton(R.string.permission_dialog_cancel, null)
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
                imagePath = resultUri.getPath();
                image.setImageURI(resultUri);
                Log.i(TAG, resultUri.toString());
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.i(TAG, error.toString());
            }
        }


        if (requestCode == LocationActivity.GET_LOCATION){
            Log.i(TAG,"ResultCode:"+resultCode);
            if (resultCode > 0){
                myLocation = (CustomLocation) data.getSerializableExtra("data");
                location.setText(myLocation.getName());
                Log.i(TAG,"获得位置:"+myLocation);
            }

        }
    }
    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .start(this);
    }
}
