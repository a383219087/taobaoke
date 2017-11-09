package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

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
        setTitleName(R.string.system_message_title);
        mBtnTopRight.setText("全部已读");
        mBtnTopRight.setVisibility(View.VISIBLE);
        mRvMessage.setLayoutManager(new LinearLayoutManagerWrapper(this));
        mRvMessage.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
        mRvMessage.addItemDecoration(new RecyclerItemDecoration());
        mAdapter = new RecyclerBaseAdapter<>(R.layout.item_message, MessageHolder.class);
        mRvMessage.setAdapter(mAdapter);
        List<Message> messageList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Message message = new Message();
            message.setTitle("123"+i);
            message.setDetail("cccccccccccccccc"+i);
            message.setNew(i/2==0);
            messageList.add(message);
        }
        mAdapter.setAll(messageList);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mAdapter.itemClickObserve()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mCompositeDisposable.add(disposable);
                    }
                })
                .subscribe(new Consumer<Message>() {
                    @Override
                    public void accept(Message message) throws Exception {
                        if(message.isDelete()){
                            mAdapter.remove(message);
                        }else{
                            MessageDetailActivity.start(MessageListActivity.this);
                        }
                    }
                });
    }

    @OnClick(R.id.title_rightbutton)
    void onClick(View view) {

    }

    public static void start(Context context) {
        Intent starter = new Intent(context, MessageListActivity.class);
        context.startActivity(starter);
    }
}
