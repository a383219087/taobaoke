package com.starnet.cqj.taobaoke.view.activity;


import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.ExchangeRecord;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;
import com.starnet.cqj.taobaoke.view.BaseApplication;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerBaseAdapter;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerItemDecoration;
import com.starnet.cqj.taobaoke.view.adapter.viewholder.ExchangeRecordHolder;

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
    private int mPage;
    private boolean hasMore;

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

    }

    private void getData() {
        RemoteDataSourceBase.INSTANCE.getUserService()
                .integralRecord(((BaseApplication) getApplication()).token, mPage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(this.<JsonCommon<List<ExchangeRecord>>>bindToLifecycle())
                .subscribe(new Consumer<JsonCommon<List<ExchangeRecord>>>() {
                    @Override
                    public void accept(JsonCommon<List<ExchangeRecord>> listJsonCommon) throws Exception {
                        mSrRefresh.setRefreshing(false);
                        if ("200".equals(listJsonCommon.getCode())) {
                            hasMore = listJsonCommon.getData() == null || listJsonCommon.getData().isEmpty();
                            if (mPage == 0) {
                                mAdapter.setAll(listJsonCommon.getData());
                            } else {
                                mAdapter.addAll(listJsonCommon.getData());
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

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            int lastPosition = 0;
            if (layoutManager instanceof GridLayoutManager) {
                //通过LayoutManager找到当前显示的最后的item的position
                lastPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else if (layoutManager instanceof LinearLayoutManager) {
                lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            }
            if (hasMore && lastPosition == recyclerView.getLayoutManager().getItemCount() - 1) {
                mPage++;
                getData();
            }
        }
    };


    @Override
    protected void initEvent() {
        super.initEvent();
        mSrRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPage = 0;
                getData();
            }
        });


        mRvRecord.addOnScrollListener(mOnScrollListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRvRecord.removeOnScrollListener(mOnScrollListener);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, ExchangeRecordActivity.class);
        context.startActivity(starter);
    }

}
