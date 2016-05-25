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
import com.xjtu.friendtrip.bean.Cover;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Meshine on 16/5/25.
 */
public class DiscoveryGridAdapter extends BaseAdapter {

    private List<Cover> covers;
    private Context context;

    public DiscoveryGridAdapter(List<Cover> covers, Context context) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_discovery_grid,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        Glide.with(context)
                .load(covers.get(position).getCoverUrl())
                .placeholder(R.drawable.ic_loading)
                .dontAnimate()
                .dontTransform()
                .into(holder.cover);

        holder.summary.setText(covers.get(position).getSummary());

        Glide.with(context)
                .load(covers.get(position).getAvatarUrl())
                .placeholder(R.drawable.ic_loading)
                .dontAnimate()
                .dontTransform()
                .into(holder.avatar);

        holder.nick.setText(covers.get(position).getNick());
        holder.location.setText(covers.get(position).getLocation());

        return convertView;
    }

    static class ViewHolder{
        @BindView(R.id.cover) ImageView cover;
        @BindView(R.id.summary) TextView summary;
        @BindView(R.id.avatar)CircleImageView avatar;
        @BindView(R.id.nick)TextView nick;
        @BindView(R.id.location)TextView location;

        public ViewHolder(View view) {
            ButterKnife.bind(this,view);
        }
    }


}
