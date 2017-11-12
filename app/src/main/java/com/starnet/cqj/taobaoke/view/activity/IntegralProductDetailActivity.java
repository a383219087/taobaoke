package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.IntegralProduct;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by mini on 17/11/11.
 */

public class IntegralProductDetailActivity extends BaseActivity {


    public static final String KEY_DATA = "data";
    @BindView(R.id.iv_product_pic)
    ImageView mIvProductPic;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_score)
    TextView mTvScore;
    @BindView(R.id.tv_receive_name)
    TextView mTvReceiveName;
    @BindView(R.id.tv_receive_phone)
    TextView mTvReceivePhone;
    @BindView(R.id.tv_receive_address)
    TextView mTvReceiveAddress;
    @BindView(R.id.ll_have_address)
    LinearLayout mLlHaveAddress;
    @BindView(R.id.ll_no_address)
    LinearLayout mLlNoAddress;
    @BindView(R.id.tv_product_detail)
    TextView mTvProductDetail;
    @BindView(R.id.ll_more)
    LinearLayout mLlMore;
    @BindView(R.id.btn_exchange)
    Button mBtnExchange;

    @Override
    protected int getContentView() {
        return R.layout.activity_integral_product_detail;
    }

    @Override
    protected void init() {
        IntegralProduct product = (IntegralProduct) getIntent().getSerializableExtra(KEY_DATA);
        if (product == null) {
            toast("数据错误，请重试");
            return;
        }
        Glide.with(this)
                .load(product.getPic())
                .into(mIvProductPic);
        mTvTitle.setText(product.getTitle());
        mTvScore.setText(String.format("%s积分", product.getScore()));
        mTvProductDetail.setText(Html.fromHtml(product.getDetail()));
        getAddress();
    }

    private void getAddress() {

    }


    @OnClick({R.id.iv_back, R.id.ll_have_address, R.id.ll_no_address, R.id.ll_more, R.id.btn_exchange})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_have_address:
            case R.id.ll_no_address:
                break;
            case R.id.ll_more:
                break;
            case R.id.btn_exchange:
                break;
        }
    }

    public static void start(Context context, IntegralProduct product) {
        Intent starter = new Intent(context, IntegralProductDetailActivity.class);
        starter.putExtra(KEY_DATA, product);
        context.startActivity(starter);
    }
}
