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
import com.xjtu.friendtrip.bean.Site;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Meshine on 16/5/25.
 */
public class SiteListAdapter extends BaseAdapter{
    private List<Site> sites;
    private Context context;

    public SiteListAdapter(List<Site> sites, Context context) {
        this.sites = sites;
        this.context = context;
    }

    @Override
    public int getCount() {
        return sites.size();
    }

    @Override
    public Object getItem(int position) {
        return sites.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_site_list,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        Glide.with(context)
                .load(sites.get(position).getCoverUrl())
                .placeholder(R.drawable.ic_loading)
                .dontAnimate()
                .dontTransform()
                .into(holder.cover);
        holder.name.setText(sites.get(position).getName());
        holder.summary.setText(sites.get(position).getSummary());

        return convertView;
    }

    static class ViewHolder{
        @BindView(R.id.cover) ImageView cover;
        @BindView(R.id.name)TextView name;
        @BindView(R.id.summary)TextView summary;

        public ViewHolder(View view) {
            ButterKnife.bind(this,view);
        }
    }
}
