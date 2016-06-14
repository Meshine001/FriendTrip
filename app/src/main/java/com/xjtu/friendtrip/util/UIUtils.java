package com.xjtu.friendtrip.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xjtu.friendtrip.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Meshine on 16/6/14.
 */
public class UIUtils {
    public static void loadAvatar(Context context,String url, CircleImageView avatar){
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.ic_default_user)
                .dontAnimate()
                .dontTransform()
                .into(avatar);
    }

    public static void loadImage(Context context,String url, ImageView image){
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.ic_loading)
                .dontAnimate()
                .dontTransform()
                .into(image);
    }
}
