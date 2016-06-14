package com.xjtu.friendtrip.Net;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.tencent.upload.Const;
import com.tencent.upload.UploadManager;
import com.tencent.upload.task.ITask;
import com.tencent.upload.task.IUploadTaskListener;
import com.tencent.upload.task.data.FileInfo;
import com.tencent.upload.task.impl.PhotoUploadTask;

import java.io.File;

/**
 * Created by Meshine on 16/5/25.
 */
public class Tencent {
    public static final int UPLOAD_SUCCESS = 0X1000;
    public static final int UPLOAD_FAILED = 0X1001;

//    public static final String APPID = "10041240";
//    public static final String BUCKET = "tripfile";
public static final String APPID = "10042654";
    public static final String BUCKET = "friendtrip";
    public static String picAuth;

    public static UploadManager getUploadManager(Context context){
        UploadManager manager = new UploadManager(context, Tencent.APPID, Const.FileType.Photo, null);
        return manager;
    }

    public static void uploadPic(UploadManager manager, Uri uri, final Handler handler){
        File file = new File(uri.getPath());
        PhotoUploadTask task = new PhotoUploadTask(file.getAbsolutePath(),new IUploadTaskListener() {
            @Override
            public void onUploadSucceed(FileInfo fileInfo) {
                Log.i("Demo", "upload succeed: " + fileInfo.url);
                Message msg = new Message();
                msg.what = UPLOAD_SUCCESS;
                msg.obj = fileInfo.url;
                handler.sendMessage(msg);
            }

            @Override
            public void onUploadFailed(int errorCode, String errorMsg) {
                Log.i("Demo", "上传结果:失败! ret:" + errorCode + " msg:" + errorMsg);
                Message msg = new Message();
                msg.what = UPLOAD_FAILED;
                msg.obj = errorMsg;
                handler.sendMessage(msg);
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
        task.setAuth(Tencent.picAuth);
        manager.upload(task);
    }
    public static void uploadPic(UploadManager manager, String path, final Handler handler){
        File file = new File(path);
        PhotoUploadTask task = new PhotoUploadTask(file.getAbsolutePath(),new IUploadTaskListener() {
            @Override
            public void onUploadSucceed(FileInfo fileInfo) {
                Log.i("Demo", "upload succeed: " + fileInfo.url);
                Message msg = new Message();
                msg.what = UPLOAD_SUCCESS;
                msg.obj = fileInfo.url;
                handler.sendMessage(msg);
            }

            @Override
            public void onUploadFailed(int errorCode, String errorMsg) {
                Log.i("Demo", "上传结果:失败! ret:" + errorCode + " msg:" + errorMsg);
                Message msg = new Message();
                msg.what = UPLOAD_FAILED;
                msg.obj = errorMsg;
                handler.sendMessage(msg);
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
        task.setAuth(Tencent.picAuth);
        manager.upload(task);
    }
}
