package com.xjtu.friendtrip.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xjtu.friendtrip.R;
import com.xjtu.friendtrip.bean.Image;
import com.xjtu.friendtrip.bean.Story;
import com.xjtu.friendtrip.bean.Text;

import java.util.List;

/**
 * Created by Ming on 2016/6/7.
 */
public class MyStoryListAdapter extends RecyclerView.Adapter<MyStoryListAdapter.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {

    private List<Story> stories;
    private Context context;

    private ItemClickListenser itemClickListenser;
    private ItemLongClickListenser itemLongClickListenser;

    public void setItemClickListenser(ItemClickListenser itemClickListenser) {
        this.itemClickListenser = itemClickListenser;
    }

    public void setItemLongClickListenser(ItemLongClickListenser itemLongClickListenser) {
        this.itemLongClickListenser = itemLongClickListenser;
    }

    public interface ItemClickListenser {
        void onItemClick(View view, int position);
    }

    public interface ItemLongClickListenser {
        void onItemLongClick(View view, int position);
    }


    public MyStoryListAdapter(List<Story> stories, Context context) {
        this.stories = stories;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_story_list, parent, false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        List<Image> images = stories.get(position).getTravlenotespictures();
        String imgUrl;
        if (images.size() > 0) {
            imgUrl = images.get(0).getImagePath();
        } else {
            //TODO
            imgUrl = "";
        }
        Glide.with(context)
                .load(imgUrl)
                .placeholder(R.drawable.ic_loading)
                .dontAnimate()
                .dontTransform()
                .into(holder.image);
//        holder.browseCount.setText(stories.get(position).get);
        holder.time.setText(stories.get(position).getDatetime());

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    @Override
    public void onClick(View v) {
        if (itemClickListenser != null){
            int position = (int) v.getTag();
            itemClickListenser.onItemClick(v,position);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (itemLongClickListenser != null){
            int position = (int) v.getTag();
            itemLongClickListenser.onItemLongClick(v,position);
        }
        return false;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView browseCount;
        private TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            browseCount = (TextView) itemView.findViewById(R.id.browse_count);
            time = (TextView) time.findViewById(R.id.time);
        }
    }
}
