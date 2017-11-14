package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.IntegralProduct;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.ProductResult;
import com.starnet.cqj.taobaoke.remote.Constant;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;
import com.starnet.cqj.taobaoke.view.BaseApplication;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerBaseAdapter;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerSpaceDecoration;
import com.starnet.cqj.taobaoke.view.adapter.viewholder.IntegralProductHolder;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class IntegralStoreActivity extends BaseActivity {
    @BindView(R.id.title_rightbutton)
    Button mTitleRightbutton;
    @BindView(R.id.tv_my_integral)
    TextView mTvMyIntegral;
    @BindView(R.id.rv_integral_product)
    RecyclerView mRvIntegralProduct;
    private RecyclerBaseAdapter<IntegralProduct, IntegralProductHolder> mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_integral_store;
    }

    @Override
    protected void init() {
        setTitleName(R.string.integral_store_title);
        mTitleRightbutton.setText("兑换记录");
        mTitleRightbutton.setVisibility(View.VISIBLE);
        mAdapter = new RecyclerBaseAdapter<>(R.layout.item_integral_product, IntegralProductHolder.class);
        mRvIntegralProduct.setLayoutManager(new GridLayoutManager(this, 2));
        mRvIntegralProduct.addItemDecoration(new RecyclerSpaceDecoration(getResources().getDimensionPixelOffset(R.dimen.product_item_padding)));
        mRvIntegralProduct.setAdapter(mAdapter);
        getData();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mAdapter.itemClickObserve()
                .compose(this.<IntegralProduct>bindToLifecycle())
                .subscribe(new Consumer<IntegralProduct>() {
                    @Override
                    public void accept(IntegralProduct product) throws Exception {
                        IntegralProductDetailActivity.start(IntegralStoreActivity.this,product);
                    }
                });
    }

    private void getData() {
        RemoteDataSourceBase.INSTANCE.getIntegralService()
                .integral(Constant.HEADER_PREFIX + ((BaseApplication) getApplication()).token)
                .compose(this.<JsonCommon<ProductResult<IntegralProduct>>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<JsonCommon<ProductResult<IntegralProduct>>>() {
                    @Override
                    public void accept(JsonCommon<ProductResult<IntegralProduct>> productResultJsonCommon) throws Exception {
                        if("200".equals(productResultJsonCommon.getCode())){
                            mAdapter.setAll(productResultJsonCommon.getData().getList());
                        }else{
                            toast(productResultJsonCommon.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }

    @OnClick({R.id.title_rightbutton, R.id.to_my_integral})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_rightbutton:
                ExchangeRecordActivity.start(this);
                break;
            case R.id.to_my_integral:
                break;
        }
    }


    public static void start(Context context) {
        Intent starter = new Intent(context, IntegralStoreActivity.class);
        context.startActivity(starter);
    }
}
