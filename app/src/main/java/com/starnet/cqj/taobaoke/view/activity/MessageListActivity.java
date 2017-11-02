package com.starnet.cqj.taobaoke.view.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.Message;
import com.starnet.cqj.taobaoke.view.adapter.LinearLayoutManagerWrapper;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerBaseAdapter;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerItemDecoration;
import com.starnet.cqj.taobaoke.view.adapter.viewholder.MessageHolder;
import com.starnet.cqj.taobaoke.view.widget.SwipeItemLayout;

import butterknife.BindView;
import butterknife.OnClick;

public class MessageListActivity extends BaseActivity {

    @BindView(R.id.rv_message)
    RecyclerView mRvMessage;

    @BindView(R.id.title_rightbutton)
    Button mBtnTopRight;

    private RecyclerBaseAdapter<Message, MessageHolder> mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_message_list;
    }

    @Override
    protected void init() {
        mBtnTopRight.setText("全部已读");
        mRvMessage.setLayoutManager(new LinearLayoutManagerWrapper(this));
        mRvMessage.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
        mRvMessage.addItemDecoration(new RecyclerItemDecoration());
        mAdapter = new RecyclerBaseAdapter<>(R.layout.item_message, MessageHolder.class);
        mRvMessage.setAdapter(mAdapter);
    }

    @OnClick(R.id.title_rightbutton)
    void onClick(View view) {

    }
}
