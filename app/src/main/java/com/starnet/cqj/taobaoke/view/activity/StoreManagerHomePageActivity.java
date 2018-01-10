package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.view.widget.ThreeShowDataView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 会员主页
 * Created by JohnChen on 2018/01/10.
 */

public class StoreManagerHomePageActivity extends BaseActivity {

    @BindView(R.id.tv_score)
    TextView mTvScore;
    @BindView(R.id.ll_tip)
    LinearLayout mLlTip;
    @BindView(R.id.three_view_score)
    ThreeShowDataView mThreeViewScore;
    @BindView(R.id.ll_statistics)
    LinearLayout mLlStatistics;
    @BindView(R.id.three_view_day)
    ThreeShowDataView mThreeViewDay;
    @BindView(R.id.three_view_month)
    ThreeShowDataView mThreeViewMonth;
    @BindView(R.id.three_view_history)
    ThreeShowDataView mThreeViewHistory;

    @Override
    protected int getContentView() {
        return R.layout.activity_store_manager;
    }

    @Override
    protected void init() {

    }


    @OnClick(R.id.ll_statistics)
    public void onViewClicked() {
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, StoreManagerHomePageActivity.class);
        context.startActivity(starter);
    }
}
