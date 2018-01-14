package com.starnet.cqj.taobaoke.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.Statistics;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;
import com.starnet.cqj.taobaoke.view.BaseApplication;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerBaseAdapter;
import com.starnet.cqj.taobaoke.view.adapter.viewholder.StatisticsHolder;
import com.starnet.cqj.taobaoke.view.widget.RecyclerViewLoadMoreHelper;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/01/10.
 */

public class DayMonthStatisticsFragment extends BaseFragment {


    public static final String KEY_TYPE = "type";
    public static final String KEY_IS_AREA = "is_area";
    @BindView(R.id.rv_statistics)
    RecyclerView mRvStatistics;

    private RecyclerBaseAdapter<Statistics, StatisticsHolder> mAdapter;
    private RecyclerViewLoadMoreHelper mHelper;
    private int mSearchType;
    private boolean mIsArea;

    public static DayMonthStatisticsFragment newInstance(int searchType, boolean isArea) {

        Bundle args = new Bundle();
        args.putInt(KEY_TYPE, searchType);
        args.putBoolean(KEY_IS_AREA, isArea);
        DayMonthStatisticsFragment fragment = new DayMonthStatisticsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    int getContentRes() {
        return R.layout.fragment_day_month_statistics;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSearchType = getArguments().getInt(KEY_TYPE);
        mIsArea = getArguments().getBoolean(KEY_IS_AREA);
        mAdapter = new RecyclerBaseAdapter<>(R.layout.item_statistics, StatisticsHolder.class);
        mRvStatistics.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvStatistics.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));
        mRvStatistics.setAdapter(mAdapter);
        mHelper = new RecyclerViewLoadMoreHelper();
        mRvStatistics.addOnScrollListener(mHelper.getOnScrollListener());
        initEvent();
        getData();
    }

    private void initEvent() {
        mHelper.setLoadMoreCallback(new RecyclerViewLoadMoreHelper.LoadMoreCallback() {
            @Override
            public void loadMore() {
                getData();
            }
        });
    }

    private void getData() {

        getJsonCommonObservable()
                .compose(this.<JsonCommon<List<Statistics>>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<JsonCommon<List<Statistics>>>() {
                    @Override
                    public void accept(JsonCommon<List<Statistics>> listJsonCommon) throws Exception {
                        if ("200".equals(listJsonCommon.getCode())) {
                            mHelper.setNoMore(listJsonCommon.getData() == null || listJsonCommon.getData().isEmpty());
                            if (mHelper.isFirstPage()) {
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
                        toast(R.string.net_error);
                    }
                });
    }

    private Observable<JsonCommon<List<Statistics>>> getJsonCommonObservable() {
        if (mIsArea) {
            return RemoteDataSourceBase.INSTANCE.getAreaStatisticsService()
                    .get(((BaseApplication) getActivity().getApplication()).getToken(),
                            mHelper.getPage(), mSearchType);
        } else {
            return RemoteDataSourceBase.INSTANCE.getStatisticsService()
                    .get(((BaseApplication) getActivity().getApplication()).getToken(),
                            mHelper.getPage(), mSearchType);
        }
    }

}
