package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.Address;
import com.starnet.cqj.taobaoke.model.IntegralProduct;
import com.starnet.cqj.taobaoke.presenter.IntegralProductDetailPresenter;
import com.starnet.cqj.taobaoke.presenter.impl.IntegralProductDetailPresenterImpl;
import com.starnet.cqj.taobaoke.view.BaseApplication;

import butterknife.BindView;
import butterknife.OnClick;


public class IntegralProductDetailActivity extends BaseActivity implements IntegralProductDetailPresenter.IView {


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

    private IntegralProductDetailPresenter mPresenter;
    private IntegralProduct mProduct;

    @Override
    protected int getContentView() {
        return R.layout.activity_integral_product_detail;
    }

    @Override
    protected void init() {
        mProduct = (IntegralProduct) getIntent().getSerializableExtra(KEY_DATA);
        if (mProduct == null) {
            toast("数据错误，请重试");
            return;
        }
        Glide.with(this)
                .load(mProduct.getPic())
                .into(mIvProductPic);
        mTvTitle.setText(mProduct.getTitle());
        mTvScore.setText(String.format("%s积分", mProduct.getScore()));
        mTvProductDetail.setText(Html.fromHtml(mProduct.getDetail()));
        mPresenter = new IntegralProductDetailPresenterImpl(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAddress();
    }

    private void getAddress() {
        mPresenter.getAddress(((BaseApplication) getApplication()).getToken());
    }


    @OnClick({R.id.iv_back, R.id.ll_have_address, R.id.ll_no_address, R.id.ll_more, R.id.btn_exchange})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_have_address:
            case R.id.ll_no_address:
                AddressListActivity.start(this);
                break;
            case R.id.ll_more:
                mLlMore.setVisibility(View.GONE);
                ViewGroup.LayoutParams layoutParams = mTvProductDetail.getLayoutParams();
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                mTvProductDetail.setLayoutParams(layoutParams);
                break;
            case R.id.btn_exchange:
                if (mProduct == null) {
                    return;
                }
                if (mLlNoAddress.getVisibility() == View.VISIBLE) {
                    toast("请设置默认地址");
                    return;
                }
                mPresenter.exchange(((BaseApplication) getApplication()).getToken(), mProduct.getScore(), mTvReceiveAddress.getText().toString(),
                        mTvReceivePhone.getText().toString(), mProduct.getId(), mTvReceiveName.getText().toString());
                break;
        }
    }

    public static void start(Context context, IntegralProduct product) {
        Intent starter = new Intent(context, IntegralProductDetailActivity.class);
        starter.putExtra(KEY_DATA, product);
        context.startActivity(starter);
    }

    @Override
    public void onGetAddress(Address address) {
        if (address != null) {
            mLlNoAddress.setVisibility(View.GONE);
            mLlHaveAddress.setVisibility(View.VISIBLE);
            mTvReceiveName.setText(address.getName());
            mTvReceivePhone.setText(address.getPhone());
            mTvReceiveAddress.setText(address.getArea() + "," + address.getAddress());
        } else {
            mLlNoAddress.setVisibility(View.VISIBLE);
            mLlHaveAddress.setVisibility(View.GONE);
        }
    }

    @Override
    public void onExchange() {
        toast("兑换成功");
    }
}
