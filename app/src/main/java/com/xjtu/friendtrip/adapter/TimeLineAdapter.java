package com.xjtu.friendtrip.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.vipul.hp_hp.timelineview.TimelineView;
import com.xjtu.friendtrip.R;
import com.xjtu.friendtrip.bean.Image;
import com.xjtu.friendtrip.bean.Text;
import com.xjtu.friendtrip.bean.TimeLineModel;
import com.xjtu.friendtrip.util.BitmapUtil;

import java.io.File;
import java.util.List;

/**
 * Created by Meshine on 16/5/29.
 */
public class TimeLineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<TimeLineModel> timeLineModels;
    private Context context;

    public static class TextViewHolder extends RecyclerView.ViewHolder{
        public TextView text;
        public TextView time;
        public TextView location;
        public TextViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
            time = (TextView) itemView.findViewById(R.id.time);
            location = (TextView) itemView.findViewById(R.id.location);
        }
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder{
        public ImageView image;
        public TextView summary;
        public TextView time;
        public TextView location;
        public ImageViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            summary = (TextView) itemView.findViewById(R.id.summary);
            time = (TextView) itemView.findViewById(R.id.time);
            location = (TextView) itemView.findViewById(R.id.location);

        }
    }

    public static class RecorderViewHolder extends RecyclerView.ViewHolder{

        public RecorderViewHolder(View itemView) {
            super(itemView);
        }
    }



    public TimeLineAdapter(Context context, List<TimeLineModel> timeLineModels) {
        this.context = context;
        this.timeLineModels = timeLineModels;
    }

    @Override
    public int getItemViewType(int position) {
        return timeLineModels.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TimeLineModel.TYPE_TEXT:
                return new TextViewHolder(LayoutInflater.from(context).inflate(R.layout.item_time_line_text,parent,false));
            case TimeLineModel.TYPE_RECORD:
                break;
            case TimeLineModel.TYPE_IMAGE:
                return new ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_time_line_image,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TimeLineModel model = timeLineModels.get(position);
        switch (holder.getItemViewType()){
            case TimeLineModel.TYPE_TEXT:
                TextViewHolder textViewHolder = (TextViewHolder) holder;
                Text t = (Text)model.getContent();
                textViewHolder.text.setText(t.getTextContent());
                textViewHolder.location.setText(model.getLocation().getName());
                textViewHolder.time.setText(model.getTime());
                break;
            case TimeLineModel.TYPE_RECORD:
                break;
            case TimeLineModel.TYPE_IMAGE:
                ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
                Image img = (Image) model.getContent();
                Bitmap thumb = BitmapUtil.extractMiniThumb(BitmapFactory.decodeFile(img.getImagePath()),400,300);
                imageViewHolder.image.setImageBitmap(thumb);
                imageViewHolder.summary.setText(img.getSummary());
                imageViewHolder.location.setText(model.getLocation().getName());
                imageViewHolder.time.setText(model.getTime());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return timeLineModels.size();
    }



}

