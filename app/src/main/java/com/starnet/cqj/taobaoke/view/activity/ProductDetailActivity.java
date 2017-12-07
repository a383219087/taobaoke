package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.AliItemImage;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.Product;
import com.starnet.cqj.taobaoke.remote.RemoteAlibabaDataSource;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;
import com.starnet.cqj.taobaoke.utils.AppUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class ProductDetailActivity extends BaseActivity {
    public static final String KEY_URL = "url";
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
    @BindView(R.id.tv_detail_content)
    TextView mTvDetailContent;
    @BindView(R.id.ll_image)
    LinearLayout mLlImage;
    @BindView(R.id.iv_arrow)
    ImageView mIvArrow;
    private String mClickUrl;

    @Override
    protected int getContentView() {
        return R.layout.activity_product_detail;
    }

    @Override
    protected void init() {
        mTvOriginPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        mTvOriginPriceHint.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        String url = getIntent().getStringExtra(KEY_URL);
        RemoteDataSourceBase.INSTANCE.getSearchService()
                .itemDetail(url)
                .compose(this.<JsonCommon<Product>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JsonCommon<Product>>() {
                    @Override
                    public void accept(JsonCommon<Product> productJsonCommon) throws Exception {
                        if ("200".equals(productJsonCommon.getCode())) {
                            Product data = productJsonCommon.getData();
                            if (data == null) {
                                return;
                            }
                            setContent(data);
                            getDetailImage(data.getItemid());
                        } else {
                            toast(productJsonCommon.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }

    private void getDetailImage(String itemId) {
        String param = "{item_num_id:" + itemId + "}";
        RemoteAlibabaDataSource.INSTANCE.getProductImageService()
                .getItemImage(param, String.valueOf(System.currentTimeMillis()))
                .compose(this.<JsonCommon<AliItemImage>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JsonCommon<AliItemImage>>() {
                    @Override
                    public void accept(JsonCommon<AliItemImage> productJsonCommon) throws Exception {
                        setDetailImage(productJsonCommon.getData().getImages());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });

    }


    private void setDetailImage(ArrayList<String> images) {
        for (String image : images) {
            ImageView iv = new ImageView(this);
            Glide.with(this)
                    .load(image)
                    .into(iv);
            mLlImage.addView(iv);
        }
    }

    private void setContent(Product product) {
        Glide.with(this)
                .load(product.getItempic())
                .into(mIvProductPic);
        mTvProductTitle.setText(product.getTitle());
        mTvOriginPrice.setText(product.getOrigin_price());
        mTvPrice.setText(product.getPrice());
        mTvRemainCount.setText(product.getCouponLast());
        mTvRemainPercent.setText(String.format("%s%%", product.getCouponRate()));
        mTvRewardMoney.setText(String.format("%s元", product.getCoupon_fee()));
        mTvRewardIntegral.setText(String.format("%s积分", product.getScore()));
        mTvDetailContent.setText(product.getDetail());
        mClickUrl = product.getClickUrl();
    }


    @OnClick({R.id.iv_back, R.id.tv_home_page, R.id.tv_service_online, R.id.btn_buy, R.id.ll_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_home_page:
                MainActivity.start(this);
                break;
            case R.id.tv_service_online:
                ServiceCustomerActivity.start(this);
                break;
            case R.id.btn_buy:
                if (TextUtils.isEmpty(mClickUrl)) {
                    return;
                }
                if (AppUtils.checkPackage(this, "com.taobao.taobao")) {
//                    PackageManager packageManager = getPackageManager();
//                    Intent intent = packageManager.getLaunchIntentForPackage("com.taobao.taobao");
//                    Uri uri = Uri.parse(mClickUrl); // 商品地址
//                    intent.setData(uri);
//                    startActivity(intent);
                    String click = mClickUrl.replace("https", "taobao");
                    Uri uri = Uri.parse(click);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } else {
                    WebViewActivity.start(this, mClickUrl);
                }
                break;
            case R.id.ll_more:
                if (mLlImage.getVisibility() == View.VISIBLE) {
                    mIvArrow.setSelected(false);
                    mLlImage.setVisibility(View.GONE);
                } else {
                    mIvArrow.setSelected(true);
                    mLlImage.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    public static void start(Context context, String url) {
        Intent starter = new Intent(context, ProductDetailActivity.class);
        starter.putExtra(KEY_URL, url);
        context.startActivity(starter);
    }

}
