package com.starnet.cqj.taobaoke.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.ResultWrapper;
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
    @BindView(R.id.tv_user_count_tip)
    TextView mTvUserCountTip;

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
        if (mIsArea) {
            mTvUserCountTip.setText("消费用户数");
        }
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
                .compose(this.<JsonCommon<ResultWrapper<Statistics>>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<JsonCommon<ResultWrapper<Statistics>>>() {
                    @Override
                    public void accept(JsonCommon<ResultWrapper<Statistics>> listJsonCommon) throws Exception {
                        if ("200".equals(listJsonCommon.getCode())) {
                            List<Statistics> datas = listJsonCommon.getData().getList();
                            mHelper.setNoMore(datas == null || datas.isEmpty());
                            if (mHelper.isFirstPage()) {
                                mAdapter.setAll(datas);
                            } else {
                                mAdapter.addAll(datas);
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

    private Observable<JsonCommon<ResultWrapper<Statistics>>> getJsonCommonObservable() {
        if (mIsArea) {
            return RemoteDataSourceBase.INSTANCE.getAreaStatisticsService()
                    .get(((BaseApplication) getActivity().getApplication()).getToken(), mSearchType, mHelper.getPage());
        } else {
            return RemoteDataSourceBase.INSTANCE.getStatisticsService()
                    .get(((BaseApplication) getActivity().getApplication()).getToken(),
                            mSearchType, mHelper.getPage());
        }
    }

}
