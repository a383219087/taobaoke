package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.ResultWrapper;
import com.starnet.cqj.taobaoke.model.WithdrawalsRecord;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;
import com.starnet.cqj.taobaoke.view.BaseApplication;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerBaseAdapter;
import com.starnet.cqj.taobaoke.view.adapter.viewholder.WithdrawalsRecordHolder;
import com.starnet.cqj.taobaoke.view.widget.RecyclerViewLoadMoreHelper;
import com.starnet.cqj.taobaoke.view.widget.WithdrawalsHeaderView;

import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class WithdrawalsActivity extends BaseActivity {


    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private RecyclerBaseAdapter<WithdrawalsRecord, WithdrawalsRecordHolder> mAdapter;
    private RecyclerViewLoadMoreHelper mHelper;

    @Override
    protected int getContentView() {
        return R.layout.activity_withdrawals;
    }

    @Override
    protected void init() {
        mHelper = new RecyclerViewLoadMoreHelper();
        WithdrawalsHeaderView headerView = new WithdrawalsHeaderView(this);
        mAdapter = new RecyclerBaseAdapter<>(headerView, R.layout.item_withdrawals_record, WithdrawalsRecordHolder.class);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(mHelper.getOnScrollListener());
        mHelper.resetPage();
        getData();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mHelper.setLoadMoreCallback(new RecyclerViewLoadMoreHelper.LoadMoreCallback() {
            @Override
            public void loadMore() {
                getData();
            }
        });
    }

    private void getData() {
        RemoteDataSourceBase.INSTANCE.getUserService()
                .cashLog(((BaseApplication) getApplication()).getToken(), mHelper.getPage())
                .compose(this.<JsonCommon<ResultWrapper<WithdrawalsRecord>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JsonCommon<ResultWrapper<WithdrawalsRecord>>>() {
                    @Override
                    public void accept(JsonCommon<ResultWrapper<WithdrawalsRecord>> resultWrapperJsonCommon) throws Exception {
                        if ("200".equals(resultWrapperJsonCommon.getCode())) {
                            List<WithdrawalsRecord> list = resultWrapperJsonCommon.getData().getList();
                            if (mHelper.isFirstPage()) {
                                mAdapter.setAll(list);
                            } else {
                                mAdapter.addAll(list);
                                mHelper.setNoMore(list == null || list.isEmpty());
                            }
                        } else {
                            toast(resultWrapperJsonCommon.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        toast(R.string.net_error);
                    }
                });
    }


    public static void start(Context context) {
        Intent starter = new Intent(context, WithdrawalsActivity.class);
        context.startActivity(starter);
    }

}
