package com.xjtu.friendtrip.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

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
        return TimelineView.getTimeLineViewType(position,getItemCount());
    }

    @Override
    public TimeLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_time_line_list, null);
        return new TimeLineViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(TimeLineViewHolder holder, int position) {

        TimeLineModel timeLineModel = mFeedList.get(position);

        holder.name.setText("name：" + timeLineModel.getName() + "    age：" + timeLineModel.getAge());

    }

    @Override
    public int getItemCount() {
        return (mFeedList!=null? mFeedList.size():0);
    }

}
