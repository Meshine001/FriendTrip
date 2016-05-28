package com.xjtu.friendtrip.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xjtu.friendtrip.R;
import com.xjtu.friendtrip.bean.Image;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Meshine on 16/5/28.
 */
public class StoryImageAdapter extends BaseAdapter {

    private List<Image> images;
    private Context context;

    public StoryImageAdapter(List<Image> images, Context context) {
        this.images = images;
        this.context = context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_story_image_list,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        Glide.with(context)
                .load(images.get(position).getImagePath())
                .placeholder(R.drawable.ic_loading)
                .dontAnimate()
                .dontTransform()
                .into(holder.image);
        holder.summary.setText(images.get(position).getSummary());

        return convertView;

    }

    static class ViewHolder{

        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.summary)
        TextView summary;

        public ViewHolder(View view) {
            ButterKnife.bind(this,view);
        }
    }
}
