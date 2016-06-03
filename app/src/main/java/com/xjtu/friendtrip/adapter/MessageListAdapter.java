package com.xjtu.friendtrip.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xjtu.friendtrip.R;
import com.xjtu.friendtrip.bean.Message;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Meshine on 16/6/3.
 */
public class MessageListAdapter extends BaseAdapter {
    private static final String TAG = MessageListAdapter.class.getName();
    List<Message> messages;
    Context context;
    static final int SYS = 1;
    static final int USER = 2;

    public MessageListAdapter(List<Message> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < 2) {
            return SYS;
        }else {
            return USER;
        }

    }

    int[] draws = {R.drawable.ic_msg_conment, R.drawable.ic_msg_thumb_up};
    String[] titles = {"评论","嗲赞"};
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SysViewHolder sysViewHolder = null;
        ViewHolder holder = null;

        int type = getItemViewType(position);
        Log.i(TAG, "type:" + type + "  位置:" + position);
        if (convertView == null) {
            switch (type) {
                case SYS:
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_message_sys, parent, false);
                    sysViewHolder = new SysViewHolder(convertView);
                    convertView.setTag(sysViewHolder);
                    break;
                case USER:
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_message_list, parent, false);
                    holder = new ViewHolder(convertView);
                    convertView.setTag(holder);
                    break;
            }
        } else {
            switch (type) {
                case SYS:
                    sysViewHolder = (SysViewHolder) convertView.getTag();
                    break;
                case USER:
                    holder = (ViewHolder) convertView.getTag();
                    break;
            }
        }

        switch (type) {
            case SYS:
                sysViewHolder.avatar.setImageResource(draws[position]);
                sysViewHolder.title.setText(titles[position]);
                break;
            case USER:
                Glide.with(context)
                        .load(messages.get(position).getAvatar())
                        .placeholder(R.drawable.ic_loading)
                        .dontAnimate()
                        .dontTransform()
                        .into(holder.avatar);
                holder.title.setText(messages.get(position).getTile());
                holder.content.setText(messages.get(position).getConent());
                holder.time.setText(messages.get(position).getTime());
                break;
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.avatar)
        CircleImageView avatar;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.content)
        TextView content;
        @BindView(R.id.time)
        TextView time;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class SysViewHolder {
        @BindView(R.id.avatar)
        CircleImageView avatar;
        @BindView(R.id.title)
        TextView title;

        public SysViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
