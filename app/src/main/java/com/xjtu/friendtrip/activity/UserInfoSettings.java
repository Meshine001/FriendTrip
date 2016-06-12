package com.xjtu.friendtrip.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.tencent.upload.Const;
import com.tencent.upload.UploadManager;
import com.tencent.upload.task.ITask;
import com.tencent.upload.task.IUploadTaskListener;
import com.tencent.upload.task.data.FileInfo;
import com.tencent.upload.task.impl.PhotoUploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.xjtu.friendtrip.Net.Config;
import com.xjtu.friendtrip.Net.RequestUtil;
import com.xjtu.friendtrip.Net.Tencent;
import com.xjtu.friendtrip.Net.UserJson;
import com.xjtu.friendtrip.R;
import com.xjtu.friendtrip.util.CommonUtil;
import com.xjtu.friendtrip.util.StoreBox;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class UserInfoSettings extends BaseActivity {

    public static final int REQUEST_USER_INFO = 0;
    public static final int STATE_CHANGED = 1;

    private static final int REQUEST_IMAGE = 1;
    private static final String TAG = UserInfoSettings.class.getName();

    @BindView(R.id.avatar)
    CircleImageView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_settings);
        ButterKnife.bind(this);
        initDialog(this);
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
                uploadPic(resultUri);

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



    void uploadPic(final Uri uri){
        showProgressDialog();
        UploadManager manager = Tencent.getUploadManager(this);
        Tencent.uploadPic(manager,uri,new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case Tencent.UPLOAD_SUCCESS:
                        String url = (String) msg.obj;
                        avatar.setImageURI(uri);
                        modifyAvatar2Cloud(url);
                        break;
                    case Tencent.UPLOAD_FAILED:
                        dismissProgressDialog();
                        CommonUtil.showToast(UserInfoSettings.this,"上传头像失败");
                        break;
                }
            }
        });
    }

    private void modifyAvatar2Cloud(String url) {
        String body = JSON.toJSONString(new UserJson(
                StoreBox.getUserInfo(this).getId(),
                "",
                "",
                url,""
        ));
        updateUserInfo(body);
    }

    private void updateUserInfo(String body) {
        CommonUtil.printRequest("更新用户信息",body);
        Ion.with(this).load("POST",Config.UPDATE_USER_INFO).setStringBody(body).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                CommonUtil.printResponse(result);
                if (RequestUtil.isRequestSuccess(result)){
                    setResult(STATE_CHANGED);
                    dismissProgressDialog();
                }else {
                    dismissProgressDialog();
                    CommonUtil.showToast(UserInfoSettings.this,"更新失败");
                }
            }
        });
    }
}
