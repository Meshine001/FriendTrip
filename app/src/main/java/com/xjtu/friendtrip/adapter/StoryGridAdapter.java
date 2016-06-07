package com.xjtu.friendtrip.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.makeramen.roundedimageview.RoundedImageView;
import com.xjtu.friendtrip.Net.Config;
import com.xjtu.friendtrip.Net.RequestUtil;
import com.xjtu.friendtrip.R;
import com.xjtu.friendtrip.bean.Discovery;
import com.xjtu.friendtrip.bean.Image;
import com.xjtu.friendtrip.bean.Story;
import com.xjtu.friendtrip.bean.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Meshine on 16/5/25.
 */
public class StoryGridAdapter extends BaseAdapter {

    private List<Story> covers;
    private Context context;

    public StoryGridAdapter(List<Story> covers, Context context) {
        this.covers = covers;
        this.context = context;
    }

    @Override
    public int getCount() {
        return covers.size();
    }

    @Override
    public Object getItem(int position) {
        return covers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_discovery_grid,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        List<Image> images = covers.get(position).getTravlenotespictures();
        if (images.size() > 0){
            Glide.with(context)
                    .load(images.get(0).getImagePath())
                    .placeholder(R.drawable.ic_loading)
                    .dontAnimate()
                    .dontTransform()
                    .into(holder.cover);
        }
        holder.summary.setText(covers.get(position).getDiscription());

        String url = Config.USER_INFO + covers.get(position).getUserId();
        Ion.with(context).load("GET",url).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                User u = RequestUtil.requestToUser(result);
                Glide.with(context)
                        .load(u.getProfilePhoto())
                        .placeholder(R.drawable.ic_loading)
                        .dontAnimate()
                        .dontTransform()
                        .into(holder.avatar);
                holder.nick.setText(u.getNickname());
            }
        });
        holder.location.setText(covers.get(position).getLocation());

        return convertView;
    }

    static class ViewHolder{
        @BindView(R.id.cover)
        RoundedImageView cover;
        @BindView(R.id.summary) TextView summary;
        @BindView(R.id.avatar)CircleImageView avatar;
        @BindView(R.id.nick)TextView nick;
        @BindView(R.id.location)TextView location;

        public ViewHolder(View view) {
            ButterKnife.bind(this,view);
        }
    }


}