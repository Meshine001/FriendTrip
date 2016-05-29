package com.xjtu.friendtrip.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vipul.hp_hp.timelineview.TimelineView;
import com.xjtu.friendtrip.R;
import com.xjtu.friendtrip.bean.TimeLineModel;

import java.util.List;

/**
 * Created by Meshine on 16/5/29.
 */
public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineViewHolder> {

    private List<TimeLineModel> mFeedList;

    public TimeLineAdapter(List<TimeLineModel> feedList) {
        mFeedList = feedList;
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    @Override
    public TimeLineViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = View.inflate(parent.getContext(), R.layout.item_time_line_list, null);
        return new TimeLineViewHolder(view, position);
    }

    @Override
    public void onBindViewHolder(TimeLineViewHolder holder, int position) {

        TimeLineModel timeLineModel = mFeedList.get(position);

        holder.textContent.setText(timeLineModel.getTextContent());
        holder.time.setText(timeLineModel.getTime());
        holder.location.setText(timeLineModel.getLocation());

    }

    @Override
    public int getItemCount() {
        return (mFeedList != null ? mFeedList.size() : 0);
    }

}

class TimeLineViewHolder extends RecyclerView.ViewHolder {

    public TextView textContent;
    public TextView time;
    public TextView location;

    public TimelineView mTimelineView;

    public TimeLineViewHolder(View itemView, int viewType) {
        super(itemView);
        textContent = (TextView) itemView.findViewById(R.id.text_content);
        time = (TextView) itemView.findViewById(R.id.time);
        location = (TextView) itemView.findViewById(R.id.location);

        mTimelineView = (TimelineView) itemView.findViewById(R.id.time_marker);
        mTimelineView.initLine(viewType);
    }

}
