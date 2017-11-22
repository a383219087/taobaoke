package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.Message;
import com.starnet.cqj.taobaoke.presenter.IMessagePresenter;
import com.starnet.cqj.taobaoke.presenter.impl.MessagePresenterImpl;
import com.starnet.cqj.taobaoke.view.BaseApplication;
import com.starnet.cqj.taobaoke.view.adapter.LinearLayoutManagerWrapper;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerBaseAdapter;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerItemDecoration;
import com.starnet.cqj.taobaoke.view.adapter.viewholder.MessageHolder;
import com.starnet.cqj.taobaoke.view.widget.SwipeItemLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MessageListActivity extends BaseActivity implements IMessagePresenter.IView {

    @BindView(R.id.rv_message)
    RecyclerView mRvMessage;

    @BindView(R.id.title_rightbutton)
    Button mBtnTopRight;

    private RecyclerBaseAdapter<Message, MessageHolder> mAdapter;
    private IMessagePresenter mPresenter;

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
        mPresenter = new MessagePresenterImpl(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        get();
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
                    public void accept(final Message message) throws Exception {
                        if (message.isDelete()) {
                            AlertDialog dialog = new AlertDialog.Builder(MessageListActivity.this)
                                    .setTitle("提示")
                                    .setMessage("确认删除这条消息吗？")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                            mPresenter.deleteMessage(((BaseApplication) getApplication()).getToken(), message.getId());
                                            message.setDelete(false);
                                        }
                                    })
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                            message.setDelete(false);
                                        }
                                    })
                                    .create();
                            dialog.show();
                        } else {
                            MessageDetailActivity.start(MessageListActivity.this, message.getId());
                        }
                    }
                });
    }

    @OnClick(R.id.title_rightbutton)
    void onClick(View view) {
        mPresenter.allRead(((BaseApplication) getApplication()).getToken());
    }

    @Override
    public void setMessageList(List<Message> messageList) {
        mAdapter.setAll(messageList);
    }

    @Override
    public void onDelete() {
        get();
    }

    @Override
    public void onAllRead() {
        get();
    }

    private void get() {
        mPresenter.getMessageList(((BaseApplication) getApplication()).getToken());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, MessageListActivity.class);
        context.startActivity(starter);
    }
}
