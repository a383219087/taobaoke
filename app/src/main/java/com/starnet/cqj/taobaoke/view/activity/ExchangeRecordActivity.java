package com.starnet.cqj.taobaoke.view.activity;


import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.ExchangeRecord;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.ResultWrapper;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;
import com.starnet.cqj.taobaoke.view.BaseApplication;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerBaseAdapter;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerItemDecoration;
import com.starnet.cqj.taobaoke.view.adapter.viewholder.ExchangeRecordHolder;
import com.starnet.cqj.taobaoke.view.widget.RecyclerViewLoadMoreHelper;

import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ExchangeRecordActivity extends BaseActivity {


    @BindView(R.id.rv_record)
    RecyclerView mRvRecord;
    @BindView(R.id.sr_refresh)
    SwipeRefreshLayout mSrRefresh;
    private RecyclerBaseAdapter<ExchangeRecord, ExchangeRecordHolder> mAdapter;
    private RecyclerViewLoadMoreHelper mHelper;

    @Override
    protected int getContentView() {
        return R.layout.activity_exchange_record;
    }

    @Override
    protected void init() {
        setTitleName(R.string.exchange_record_title);
        mRvRecord.setLayoutManager(new LinearLayoutManager(this));
        mRvRecord.addItemDecoration(new RecyclerItemDecoration());
        mAdapter = new RecyclerBaseAdapter<>(R.layout.item_record, ExchangeRecordHolder.class);
        mRvRecord.setAdapter(mAdapter);
        mHelper = new RecyclerViewLoadMoreHelper();
        mHelper.setLoadMoreCallback(new RecyclerViewLoadMoreHelper.LoadMoreCallback() {
            @Override
            public void loadMore() {
                getData();
            }
        });
        getData();
    }

    private void getData() {
        RemoteDataSourceBase.INSTANCE.getUserService()
                .integralRecord(((BaseApplication) getApplication()).getToken(), mHelper.getPage())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(this.<JsonCommon<ResultWrapper<ExchangeRecord>>>bindToLifecycle())
                .subscribe(new Consumer<JsonCommon<ResultWrapper<ExchangeRecord>>>() {
                    @Override
                    public void accept(JsonCommon<ResultWrapper<ExchangeRecord>> listJsonCommon) throws Exception {
                        mSrRefresh.setRefreshing(false);
                        mHelper.setLoading(false);
                        if ("200".equals(listJsonCommon.getCode())) {
                            List<ExchangeRecord> list = listJsonCommon.getData().getList();
                            mHelper.setNoMore(list == null || list.isEmpty());
                            if (mHelper.isFirstPage()) {
                                mAdapter.setAll(list);
                            } else {
                                mAdapter.addAll(list);
                            }
                        } else {
                            toast(listJsonCommon.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }


    @Override
    protected void initEvent() {
        super.initEvent();
        mSrRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHelper.resetPage();
                getData();
            }
        });


        mRvRecord.addOnScrollListener(mHelper.getOnScrollListener());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, ExchangeRecordActivity.class);
        context.startActivity(starter);
    }

}
