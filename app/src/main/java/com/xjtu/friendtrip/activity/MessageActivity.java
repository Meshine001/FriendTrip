
package com.xjtu.friendtrip.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xjtu.friendtrip.R;
import com.xjtu.friendtrip.adapter.MessageListAdapter;
import com.xjtu.friendtrip.bean.Message;
import com.xjtu.friendtrip.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageActivity extends BaseActivity {

    @BindView(R.id.message_list)
    ListView messageList;
    List<Message> messages;
    MessageListAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        initToolbar("消息");
        initList();
    }

    private void initList() {
        messages = new ArrayList<>();
        messages.add(new Message("回复"));
        messages.add(new Message("点赞"));
        messageAdapter = new MessageListAdapter(messages,this);
        messageList.setAdapter(messageAdapter);
        messageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CommonUtil.showToast(MessageActivity.this,""+position);
            }
        });


        testData();
    }

    private void testData() {
        Message msg = new Message(
                "http://img4.duitang.com/uploads/item/201511/08/20151108131440_HvuEB.thumb.700_0.jpeg",
                "王宝强","你好啊...","25-3");
        messages.add(msg);

        messageAdapter.notifyDataSetChanged();
    }
}
