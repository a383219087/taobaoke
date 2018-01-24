package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.StoreIndex;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;
import com.starnet.cqj.taobaoke.view.BaseApplication;
import com.starnet.cqj.taobaoke.view.widget.ThreeShowDataView;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 会员主页
 * Created by JohnChen on 2018/01/10.
 */

public class AreaManagerHomepageActivity extends BaseActivity {

    @BindView(R.id.tv_score)
    TextView mTvScore;
    @BindView(R.id.ll_tip)
    LinearLayout mLlTip;
    @BindView(R.id.three_view_score)
    ThreeShowDataView mThreeViewScore;
    @BindView(R.id.ll_statistics)
    LinearLayout mLlStatistics;
    @BindView(R.id.three_view_day)
    ThreeShowDataView mThreeViewDay;
    @BindView(R.id.three_view_month)
    ThreeShowDataView mThreeViewMonth;
    @BindView(R.id.three_view_history)
    ThreeShowDataView mThreeViewHistory;

    @Override
    protected int getContentView() {
        return R.layout.activity_store_manager;
    }

    @Override
    protected void init() {
        setTitleName(R.string.area_proxy_title);
        mThreeViewScore.setValueCount(2);
        mThreeViewScore.setOneTip("未确认");
        mThreeViewScore.setTwoTip("已确认");
        mThreeViewDay.setOneTip("消费用户数");
        mThreeViewMonth.setOneTip("消费用户数");
        mThreeViewHistory.setOneTip("消费用户数");
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    protected void getData() {
        RemoteDataSourceBase.INSTANCE.getAreaManagerService()
                .index(((BaseApplication) getApplication()).getToken())
                .compose(this.<JsonCommon<StoreIndex>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<JsonCommon<StoreIndex>>() {
                    @Override
                    public void accept(JsonCommon<StoreIndex> storeIndexJsonCommon) throws Exception {
                        if ("200".equals(storeIndexJsonCommon.getCode())) {
                            StoreIndex storeIndex = storeIndexJsonCommon.getData();
                            if ("0".equals(storeIndex.getStatus())) {
                                addMemberSignView("-1", "");
                            } else if ("1".equals(storeIndex.getStatus())) {
                                String area = storeIndex.getProvince() +
                                        (TextUtils.isEmpty(storeIndex.getCity()) ? "" : ","+ storeIndex.getCity())   +
                                        (TextUtils.isEmpty(storeIndex.getArea()) ? "" : ","+ storeIndex.getArea());
                                addMemberSignView(storeIndex.getType(), TextUtils.isEmpty(storeIndex.getProvince()) ? "无" : area);
                                setValue(storeIndex);
                            } else {
                                addToRegisterView();
                            }
                        } else {
                            toast(storeIndexJsonCommon.getMessage());
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

    private void setValue(StoreIndex storeIndex) {
        mTvScore.setText(storeIndex.getData1().getTotal());
        mThreeViewScore.setTwoValue(storeIndex.getData1().getCredit1());
        mThreeViewScore.setOneValue(storeIndex.getData1().getCredit2());
        mThreeViewDay.setOneValue(storeIndex.getData2().getUserNum());
        mThreeViewDay.setTwoValue(storeIndex.getData2().getOrderNum());
        mThreeViewDay.setThreeValue(storeIndex.getData2().getScore());
        mThreeViewMonth.setOneValue(storeIndex.getData3().getUserNum());
        mThreeViewMonth.setTwoValue(storeIndex.getData3().getOrderNum());
        mThreeViewMonth.setThreeValue(storeIndex.getData3().getScore());
        mThreeViewHistory.setOneValue(storeIndex.getData4().getUserNum());
        mThreeViewHistory.setTwoValue(storeIndex.getData4().getOrderNum());
        mThreeViewHistory.setThreeValue(storeIndex.getData4().getScore());
    }

    private void addToRegisterView() {
        mLlTip.removeAllViews();
        View viewToRegister = LayoutInflater.from(AreaManagerHomepageActivity.this)
                .inflate(R.layout.view_to_register, null, false);
        TextView tvTip = (TextView) viewToRegister.findViewById(R.id.tv_tip);
        tvTip.setText("您还不是区域代理哦！");
        viewToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreManagerRegisterActivity.start(AreaManagerHomepageActivity.this, "1", getIsArea());
            }
        });

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mLlTip.addView(viewToRegister, params);
    }


    private void addMemberSignView(String type, String area) {
        mLlTip.removeAllViews();
        View memberSignView = LayoutInflater.from(AreaManagerHomepageActivity.this)
                .inflate(R.layout.view_member_sign, null, false);
        TextView tvMemberTip = (TextView) memberSignView.findViewById(R.id.tv_member_tip);
        TextView tvMemberSign = (TextView) memberSignView.findViewById(R.id.tv_member_sign);
        tvMemberTip.setText("代理区域");
        if ("-1".equals(type)) {
            tvMemberSign.setText("审核中");
            memberSignView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StoreManagerRegisterActivity.start(AreaManagerHomepageActivity.this, "0", getIsArea());
                }
            });
        } else {
            tvMemberSign.setText(area);
        }

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mLlTip.addView(memberSignView, params);
    }

    @OnClick(R.id.ll_statistics)
    public void onViewClicked() {
        DataStatisticsActivity.start(this, getIsArea());
    }

    @NonNull
    protected String getIsArea() {
        return "1";
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, AreaManagerHomepageActivity.class);
        context.startActivity(starter);
    }
}
