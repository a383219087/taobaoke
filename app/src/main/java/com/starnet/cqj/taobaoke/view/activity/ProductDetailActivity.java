package com.starnet.cqj.taobaoke.view.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/08.
 */

public class ProductDetailActivity extends BaseActivity {
    @BindView(R.id.iv_product_pic)
    ImageView mIvProductPic;
    @BindView(R.id.tv_product_title)
    TextView mTvProductTitle;
    @BindView(R.id.tv_product_remark)
    TextView mTvProductRemark;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_origin_price_hint)
    TextView mTvOriginPriceHint;
    @BindView(R.id.tv_origin_price)
    TextView mTvOriginPrice;
    @BindView(R.id.tv_reward_integral)
    TextView mTvRewardIntegral;
    @BindView(R.id.tv_remain_percent)
    TextView mTvRemainPercent;
    @BindView(R.id.tv_remain_count)
    TextView mTvRemainCount;
    @BindView(R.id.tv_reward_money)
    TextView mTvRewardMoney;

    @Override
    protected int getContentView() {
        return R.layout.activity_product_detail;
    }

    @Override
    protected void init() {

    }

    @OnClick({R.id.iv_back, R.id.tv_collection, R.id.tv_service_online, R.id.btn_buy, R.id.btn_command_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_collection:
                break;
            case R.id.tv_service_online:
                break;
            case R.id.btn_buy:
                break;
            case R.id.btn_command_buy:
                break;
        }
    }
}
