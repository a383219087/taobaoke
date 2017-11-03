package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;

import butterknife.BindView;

public class MessageDetailActivity extends BaseActivity {

    @BindView(R.id.tv_message_detail_title)
    TextView mTvMessageDetailTitle;
    @BindView(R.id.tv_message_detail_time)
    TextView mTvMessageDetailTime;
    @BindView(R.id.tv_message_detail_content)
    TextView mTvMessageDetailContent;

    @Override
    protected int getContentView() {
        return R.layout.activity_message_detail;
    }

    @Override
    protected void init() {
        setTitleName(R.string.message_detail_title);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, MessageDetailActivity.class);
        context.startActivity(starter);
    }

}
