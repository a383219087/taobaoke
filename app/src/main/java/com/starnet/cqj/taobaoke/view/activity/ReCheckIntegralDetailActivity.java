package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.IntegralDetail;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.ResultWrapper;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;
import com.starnet.cqj.taobaoke.view.BaseApplication;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerBaseAdapter;
import com.starnet.cqj.taobaoke.view.adapter.viewholder.IntegralDetailHolder;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by mini on 17/11/5.
 */

public class ReCheckIntegralDetailActivity extends BaseActivity {


    @BindView(R.id.tv_integral)
    TextView mTvIntegral;
    @BindView(R.id.tv_remark)
    TextView mTvRemark;
    @BindView(R.id.rv_integral_detail)
    RecyclerView mRvIntegralDetail;
    private RecyclerBaseAdapter<IntegralDetail, IntegralDetailHolder> mAdapter;
    protected int mPage;

    @Override
    protected int getContentView() {
        return R.layout.activity_integral_detail;
    }

    @Override
    protected void init() {
        setTitleName(getDetailTitle());
        mTvRemark.setText(getDetailTitle());
        mAdapter = new RecyclerBaseAdapter<>(R.layout.item_integral_detail, IntegralDetailHolder.class);
        mRvIntegralDetail.setLayoutManager(new LinearLayoutManager(this));
        mRvIntegralDetail.setAdapter(mAdapter);
        getData();
    }

    protected int getDetailTitle() {
        return R.string.recheck_integral_title;
    }

    private void getData() {
        getDetailObservable()
                .compose(this.<JsonCommon<ResultWrapper<IntegralDetail>>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<JsonCommon<ResultWrapper<IntegralDetail>>>() {
                    @Override
                    public void accept(JsonCommon<ResultWrapper<IntegralDetail>> resultWrapperJsonCommon) throws Exception {
                        if ("200".equals(resultWrapperJsonCommon.getCode())) {
                            mAdapter.setAll(resultWrapperJsonCommon.getData().getList());
                            mTvIntegral.setText(resultWrapperJsonCommon.getData().getAllScore());
                        } else {
                            toast(resultWrapperJsonCommon.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }

    protected Observable<JsonCommon<ResultWrapper<IntegralDetail>>> getDetailObservable() {
        return RemoteDataSourceBase.INSTANCE.getUserService()
                .djsCncbk(((BaseApplication) getApplication()).token, mPage);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, ReCheckIntegralDetailActivity.class);
        context.startActivity(starter);
    }

}
