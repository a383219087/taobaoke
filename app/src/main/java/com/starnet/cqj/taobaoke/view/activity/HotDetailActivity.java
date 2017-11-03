package com.starnet.cqj.taobaoke.view.activity;

import android.widget.ImageView;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;

import butterknife.BindView;

public class HotDetailActivity extends BaseActivity {

    @BindView(R.id.tv_detail_title)
    TextView mTvDetailTitle;
    @BindView(R.id.tv_detail_time)
    TextView mTvDetailTime;
    @BindView(R.id.tv_detail_see)
    TextView mTvDetailSee;
    @BindView(R.id.iv_detail_big_image)
    ImageView mIvDetailBigImage;
    @BindView(R.id.tv_detail_content)
    TextView mTvDetailContent;

    @Override
    protected int getContentView() {
        return R.layout.activity_hot_detail;
    }

    @Override
    protected void init() {
        setTitleName(R.string.hot_detail_title);
    }

}
