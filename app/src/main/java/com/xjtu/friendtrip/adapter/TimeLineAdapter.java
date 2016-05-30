package com.xjtu.friendtrip.adapter;

import android.content.Context;
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

import java.io.File;
import java.util.List;

/**
 * Created by Meshine on 16/5/29.
 */
public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.TimeLineViewHolder> {
    private List<TimeLineModel> timeLineModels;
    private Context context;
    public static class TimeLineViewHolder extends RecyclerView.ViewHolder{

        public RelativeLayout content;
        public TextView time;
        public TextView location;
        public TimelineView mTimelineView;
        public TimeLineViewHolder(View itemView,int viewType) {
            super(itemView);
            content = (RelativeLayout) itemView.findViewById(R.id.content);
            time = (TextView) itemView.findViewById(R.id.time);
            location = (TextView) itemView.findViewById(R.id.location);
            mTimelineView = (TimelineView) itemView.findViewById(R.id.time_marker);
            mTimelineView.initLine(viewType);
        }
    }

    public TimeLineAdapter(Context context, List<TimeLineModel> timeLineModels) {
        this.context = context;
        this.timeLineModels = timeLineModels;
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position,timeLineModels.size());
    }

    @Override
    public TimeLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_time_line_list,parent,false);
        return new TimeLineViewHolder(view,viewType);
    }

    @Override
    public void onBindViewHolder(TimeLineViewHolder holder, int position) {
        int modelType = timeLineModels.get(position).getType();
        switch (modelType){
            case TimeLineModel.TYPE_TEXT:
                View textContent = View.inflate(context,R.layout.item_time_line_text_content,null);
                TextView text = (TextView) textContent.findViewById(R.id.text);
                Text str = (Text) timeLineModels.get(position).getContent();
                text.setText(str.getTextContent());
                holder.content.addView(textContent);
                break;
            case TimeLineModel.TYPE_RECORD:

                break;
            case TimeLineModel.TYPE_IMAGE:
                View imageContent = View.inflate(context,R.layout.item_time_line_image_content,null);
                ImageView image = (ImageView) imageContent.findViewById(R.id.image);
                TextView summary = (TextView) imageContent.findViewById(R.id.summary);
                Image img = (Image) timeLineModels.get(position).getContent();
                image.setImageURI(Uri.fromFile(new File(img.getImagePath())));
                summary.setText(img.getSummary());
                holder.content.addView(imageContent);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return timeLineModels.size();
    }



}

