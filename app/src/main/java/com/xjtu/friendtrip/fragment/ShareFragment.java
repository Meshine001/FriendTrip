package com.xjtu.friendtrip.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Color;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.tencent.download.Downloader;
import com.tencent.download.core.DownloadResult;
import com.tencent.upload.Const;
import com.tencent.upload.UploadManager;
import com.tencent.upload.task.ITask;
import com.tencent.upload.task.IUploadTaskListener;
import com.tencent.upload.task.data.FileInfo;
import com.tencent.upload.task.impl.PhotoUploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.xjtu.friendtrip.Net.Tencent;
import com.xjtu.friendtrip.R;
import com.xjtu.friendtrip.util.CommonUtil;

import org.json.JSONException;
import org.json.JSONObject;

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
    private static final Object TAG = ShareFragment.class.getName() ;

    List<Uri> uploadsUri = new ArrayList<>();

    @BindView(R.id.image)
    ImageView imageView;
    @BindView(R.id.image1)
    ImageView imageView1;
    @BindView(R.id.button)
    Button button;


    @OnClick({R.id.button, R.id.image})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                getPicUploadAuth();
                break;
            case R.id.image:
                selectImage();

                break;
        }


    }

    private static final int MESSAGE_DOWNLOAD_OK = 0;
    private static final int MESSAGE_UPLOAD_OK = 1;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MESSAGE_DOWNLOAD_OK:
                    String tmpPath = (String) msg.obj;
                    imageView1.setImageURI(Uri.fromFile(new File(tmpPath)));
                    break;
                case MESSAGE_UPLOAD_OK:

                    break;
            }
        }
    };

    void getPicUploadAuth(){
        Ion.with(getContext())
                .load("http://192.168.0.108:8080/qcloud/qPicAuth?type=upload")
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        try {
                            Log.i("GetAuth",result);
                            JSONObject jo = new JSONObject(result);
                            String auth = jo.getString("sign");
                            uploadPics(auth);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
    }

    void downloadPic(String url){
        Downloader downloader = new Downloader(getContext(),Tencent.APPID,"downloader");
        downloader.download(url, new Downloader.DownloadListener() {
            @Override
            public void onDownloadCanceled(String url) {
                Log.i("Demo", "下载任务被取消");
            }

            @Override
            public void onDownloadFailed(String url, DownloadResult result) {
                Log.i("Demo", "下载失败: " + result. getErrorCode());
            }

            @Override
            public void onDownloadSucceed(String url, DownloadResult result) {
                Log.i("Demo", "下载成功: " + result.getPath());
                Message msg = new Message();
                msg.what = MESSAGE_DOWNLOAD_OK;
                msg.obj = result.getPath();
                handler.sendMessage(msg);
            }

            @Override
            public void onDownloadProgress(String url, long totalSize, float progress) {
                long nProgress = (int) (progress * 100);
                Log.i("Demo", "下载进度: " + nProgress + "%");
            }
        });
    }

    void uploadPics(String auth){
        UploadManager manager = new UploadManager(getContext(), Tencent.APPID, Const.FileType.Photo, null);
        for (Uri uri : uploadsUri) {
            File file = new File(uri.getPath());
            PhotoUploadTask task = new PhotoUploadTask(file.getAbsolutePath(),new IUploadTaskListener() {
                @Override
                public void onUploadSucceed(FileInfo fileInfo) {
                    Log.i("Demo", "upload succeed: " + fileInfo.url);
//                    downloadPic(fileInfo.url);

                }

                @Override
                public void onUploadFailed(int errorCode, String errorMsg) {
                    Log.i("Demo", "上传结果:失败! ret:" + errorCode + " msg:" + errorMsg);
                }

                @Override
                public void onUploadProgress(long totalSize, long sendSize) {
                    long p = (long) ((sendSize * 100) / (totalSize * 1.0f));
                    Log.i("Demo", "上传进度: " + p + "%");
                }

                @Override
                public void onUploadStateChange(ITask.TaskState taskState) {

                }
            });
            task.setBucket(Tencent.BUCKET);
            task.setAuth(auth);
            manager.upload(task);
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
                Log.i("sssss", resultUri.toString());
                uploadsUri.add(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.i("sssss", error.toString());
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setMinCropResultSize(100, 100)
                .setMaxCropResultSize(1000, 1000)
                .start(getActivity());
    }


    /**
     * Activity 传数据进来
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void setActivityResult(int requestCode, int resultCode, Intent data) {
        onActivityResult(requestCode, resultCode, data);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share, container, false);
        ButterKnife.bind(this, view);
        Glide.with(getContext())
                .load("http://tripfile-10041240.image.myqcloud.com/6cee5846-4f21-4fb8-8541-0d88ec58ac6d")
                .placeholder(R.drawable.ic_loading)
                .dontAnimate()
                .dontTransform()
                .into(imageView1);
        return view;
    }


}
