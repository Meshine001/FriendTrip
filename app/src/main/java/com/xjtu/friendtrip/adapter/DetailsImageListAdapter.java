package com.xjtu.friendtrip.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xjtu.friendtrip.R;
import com.xjtu.friendtrip.bean.Image;
import com.xjtu.friendtrip.util.UIUtils;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Meshine on 16/5/27.
 */
public class DetailsImageListAdapter extends BaseAdapter {

    private List<Image> images;
    private Context context;
    private int width;

    public DetailsImageListAdapter(List<Image> images, Activity context) {
        this.images = images;
        this.context = context;
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);

        width = dm.widthPixels;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_details_image_list,parent,false);
            holder = new ViewHolder(convertView);
            int screenWidth = width;

            ViewGroup.LayoutParams lp = holder.image.getLayoutParams();
            lp.width = screenWidth;
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;

            holder.image.setLayoutParams(lp);

            holder.image.setMaxWidth(screenWidth);
            holder.image.setMaxHeight(screenWidth*5);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        UIUtils.loadImage(context,images.get(position).getUrl(),holder.image);
        holder.summary.setText(images.get(position).getSummary());

        return convertView;
    }

    static class ViewHolder{
        @BindView(R.id.image) ImageView image;
        @BindView(R.id.summary) TextView summary;
        public ViewHolder(View view) {
            ButterKnife.bind(this,view);
        }
    }
}
