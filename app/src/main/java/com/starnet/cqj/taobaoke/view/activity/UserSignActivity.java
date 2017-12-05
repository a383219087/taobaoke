package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.Medal;
import com.starnet.cqj.taobaoke.model.ResultWrapper;
import com.starnet.cqj.taobaoke.model.SignResult;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;
import com.starnet.cqj.taobaoke.view.BaseApplication;
import com.starnet.cqj.taobaoke.view.widget.SignCalendar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by mini on 17/11/15.
 */

public class UserSignActivity extends BaseActivity {


    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.sign_calendar)
    SignCalendar mSignCalendar;
    @BindView(R.id.tv_continuity)
    TextView mTvContinuity;
    @BindView(R.id.tv_remark)
    TextView mTvRemark;

    @Override
    protected int getContentView() {
        return R.layout.activity_user_check;
    }

    @Override
    protected void init() {
        setTitleName(R.string.user_sign_title);
        SimpleDateFormat dateFm = new SimpleDateFormat("yyyy年MM月dd日 EEEE", Locale.getDefault());
        mTvDate.setText(dateFm.format(new Date()));
        getData();
    }

    private void getData() {
        RemoteDataSourceBase.INSTANCE.getActionService()
                .signList(((BaseApplication) getApplication()).getToken())
                .compose(this.<JsonCommon<ResultWrapper<String>>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<JsonCommon<ResultWrapper<String>>>() {
                    @Override
                    public void accept(JsonCommon<ResultWrapper<String>> resultWrapperJsonCommon) throws Exception {
                        if ("200".equals(resultWrapperJsonCommon.getCode())) {
                            mSignCalendar.addMarks(resultWrapperJsonCommon.getData().getList());
                            mTvContinuity.setText("连续签到" + resultWrapperJsonCommon.getData().getCount() + "天");
                            mTvRemark.setText(Html.fromHtml(resultWrapperJsonCommon.getData().getSignRemark()));
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

    @OnClick(R.id.btn_sign)
    public void onViewClicked() {
        RemoteDataSourceBase.INSTANCE.getActionService()
                .signIn(((BaseApplication) getApplication()).getToken())
                .compose(this.<JsonCommon<SignResult>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<JsonCommon<SignResult>>() {
                    @Override
                    public void accept(JsonCommon<SignResult> resultWrapperJsonCommon) throws Exception {
                        if ("200".equals(resultWrapperJsonCommon.getCode())) {
                            mSignCalendar.addMark(resultWrapperJsonCommon.getData().getDate());
                            mTvContinuity.setText("连续签到" + resultWrapperJsonCommon.getData().getCount() + "天");
                            getMedal();
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

    private void getMedal(){
        RemoteDataSourceBase.INSTANCE.getUserService().promote(((BaseApplication) getApplication()).getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JsonCommon<Medal>>() {
                    @Override
                    public void accept(JsonCommon<Medal> medalJsonCommon) throws Exception {
                        if ("200".equals(medalJsonCommon.getCode())) {
                            GetMedalDialogActivity.start(getApplicationContext(), medalJsonCommon.getData());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, UserSignActivity.class);
        context.startActivity(starter);
    }
}
