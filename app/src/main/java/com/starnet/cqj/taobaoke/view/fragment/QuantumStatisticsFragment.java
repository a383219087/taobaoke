package com.starnet.cqj.taobaoke.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.Statistics;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;
import com.starnet.cqj.taobaoke.view.BaseApplication;
import com.starnet.cqj.taobaoke.view.widget.DateTimePopupWindow;
import com.starnet.cqj.taobaoke.view.widget.ThreeShowDataView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 按时间段统计
 * Created by JohnChen on 2018/01/10.
 */

public class QuantumStatisticsFragment extends BaseFragment {


    private static final String KEY_IS_AREA = "is_area";
    @BindView(R.id.tv_start_date)
    TextView mTvStartDate;
    @BindView(R.id.tv_end_date)
    TextView mTvEndDate;
    @BindView(R.id.view_three_show)
    ThreeShowDataView mThreeShowDataView;
    private Calendar mCalendar;
    private SimpleDateFormat mDateFormat;
    private DateTimePopupWindow mPopupWindow;
    private TextView mCurrentChooseView;
    private boolean mIsArea;

    public static QuantumStatisticsFragment newInstance(boolean isArea) {

        Bundle args = new Bundle();

        args.putBoolean(KEY_IS_AREA, isArea);
        QuantumStatisticsFragment fragment = new QuantumStatisticsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    int getContentRes() {
        return R.layout.fragment_quantum_statistics;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCalendar = Calendar.getInstance();
        mIsArea = getArguments().getBoolean(KEY_IS_AREA);
        mTvStartDate.setText(mCalendar.get(Calendar.YEAR) + "-" + (mCalendar.get(Calendar.MONTH) + 1) + "-1");
        mDateFormat = new SimpleDateFormat("yyyy-M-d", Locale.getDefault());
        mTvEndDate.setText(mDateFormat.format(new Date()));
        mPopupWindow = new DateTimePopupWindow(getActivity());
        initEvent();
        getData();
    }

    private void initEvent() {
        mPopupWindow.dateObservable()
                .compose(this.<Date>bindToLifecycle())
                .subscribe(new Consumer<Date>() {
                    @Override
                    public void accept(Date date) throws Exception {
                        mCurrentChooseView.setText(mDateFormat.format(date));
                    }
                });
    }

    private void getData() {
        getJsonCommonObservable()
                .compose(this.<JsonCommon<Statistics>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<JsonCommon<Statistics>>() {
                    @Override
                    public void accept(JsonCommon<Statistics> objectJsonCommon) throws Exception {
                        if ("200".equals(objectJsonCommon.getCode())) {
                            Statistics data = objectJsonCommon.getData();
                            mThreeShowDataView.setTwoValue(data.getOrderNum());
                            mThreeShowDataView.setOneValue(data.getUserNum());
                            mThreeShowDataView.setThreeValue(data.getScore());
                        } else {
                            toast(objectJsonCommon.getMessage());
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

    private Observable<JsonCommon<Statistics>> getJsonCommonObservable() {
        if (mIsArea) {
            return RemoteDataSourceBase.INSTANCE.getAreaStatisticsService()
                    .get(((BaseApplication) getActivity().getApplication()).getToken(), 3,
                            mTvStartDate.getText().toString(),
                            mTvEndDate.getText().toString());
        } else {
            return RemoteDataSourceBase.INSTANCE.getStatisticsService()
                    .get(((BaseApplication) getActivity().getApplication()).getToken(), 3,
                            mTvStartDate.getText().toString(),
                            mTvEndDate.getText().toString());
        }
    }

    @OnClick({R.id.tv_start_date, R.id.tv_end_date, R.id.btn_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_start_date:
                String startDate = mTvStartDate.getText().toString();
                showDatePicker(mTvStartDate, startDate);
                break;
            case R.id.tv_end_date:
                String endDate = mTvEndDate.getText().toString();
                showDatePicker(mTvEndDate, endDate);
                break;
            case R.id.btn_search:
                getData();
                break;
        }
    }

    private void showDatePicker(TextView view, String startDate) {
        mCurrentChooseView = view;
        try {
            Date date = mDateFormat.parse(startDate);
            mCalendar.setTime(date);
            mPopupWindow.setDefaultDate(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mPopupWindow.showAsDropDown(view);
    }
}
