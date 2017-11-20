package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.Message;
import com.starnet.cqj.taobaoke.presenter.IMessageDetailPresenter;
import com.starnet.cqj.taobaoke.presenter.impl.MessageDetailPresenterImpl;
import com.starnet.cqj.taobaoke.view.BaseApplication;

import butterknife.BindView;

public class MessageDetailActivity extends BaseActivity implements IMessageDetailPresenter.IView {

    public static final String KEY_ID = "id";
    @BindView(R.id.tv_message_detail_title)
    TextView mTvMessageDetailTitle;
    @BindView(R.id.tv_message_detail_time)
    TextView mTvMessageDetailTime;
    @BindView(R.id.tv_message_detail_content)
    TextView mTvMessageDetailContent;
    private IMessageDetailPresenter mPresenter;

    @Override
    protected int getContentView() {
        return R.layout.activity_message_detail;
    }

    @Override
    protected void init() {
        setTitleName(R.string.message_detail_title);
        String id = getIntent().getStringExtra(KEY_ID);
        mPresenter = new MessageDetailPresenterImpl(this);
        mPresenter.getMessage(((BaseApplication) getApplication()).token, id);
    }

    @Override
    public void setMessage(Message message) {
        if (message != null) {
            mTvMessageDetailTitle.setText(message.getTitle());
            mTvMessageDetailTime.setText(message.getTime());
            mTvMessageDetailContent.setText(message.getContent());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }

    public static void start(Context context, String id) {
        Intent starter = new Intent(context, MessageDetailActivity.class);
        starter.putExtra(KEY_ID, id);
        context.startActivity(starter);
    }
}
