package com.xjtu.friendtrip.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.xjtu.friendtrip.Net.Config;
import com.xjtu.friendtrip.Net.RequestUtil;
import com.xjtu.friendtrip.R;
import com.xjtu.friendtrip.bean.Comment;
import com.xjtu.friendtrip.bean.User;
import com.xjtu.friendtrip.util.ActivityUtil;
import com.xjtu.friendtrip.util.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ming on 2016/6/6.
 */
public class CommentListAdapter extends BaseAdapter {
    private List<Comment> comments;
    private Context context;

    public CommentListAdapter(List<Comment> comments, Context context) {
        this.comments = comments;
        this.context = context;
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_comment_list,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        String url = Config.USER_INFO + comments.get(position).getFromUserId();
        Ion.with(context).load("GET",url).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                final User u = RequestUtil.requestToUser(result);
                if (u!=null){
                    UIUtils.loadAvatar(context,u.getProfilePhoto(),holder.avatar);
                    holder.nick.setText(u.getNickname()+":");
                    holder.avatar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ActivityUtil.startUserInfoActivity(context,u.getId());
                        }
                    });
                }
            }
        });

        holder.content.setText(comments.get(position).getContent());
        holder.time.setText(comments.get(position).getDatetime());

        return convertView;
    }

    static class ViewHolder{

        @BindView(R.id.avatar)
        CircleImageView avatar;
        @BindView(R.id.nick)
        TextView nick;
        @BindView(R.id.content)
        TextView content;
        @BindView(R.id.time)
        TextView time;

        public ViewHolder(View view) {
            ButterKnife.bind(this,view);
        }
    }
}
