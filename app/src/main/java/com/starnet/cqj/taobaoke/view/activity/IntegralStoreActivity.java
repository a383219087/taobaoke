package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/06.
 */

public class IntegralStoreActivity extends BaseActivity {
    @BindView(R.id.title_rightbutton)
    Button mTitleRightbutton;
    @BindView(R.id.tv_my_integral)
    TextView mTvMyIntegral;
    @BindView(R.id.rv_integral_product)
    RecyclerView mRvIntegralProduct;

    @Override
    protected int getContentView() {
        return R.layout.activity_integral_store;
    }

    @Override
    protected void init() {
        setTitleName(R.string.integral_store_title);
        mTitleRightbutton.setText("兑换记录");
        mTitleRightbutton.setVisibility(View.VISIBLE);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, IntegralStoreActivity.class);
        context.startActivity(starter);
    }

    @OnClick(R.id.title_rightbutton)
    public void onViewClicked() {
    }
}
