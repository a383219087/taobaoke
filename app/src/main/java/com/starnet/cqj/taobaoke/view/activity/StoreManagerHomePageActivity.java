package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 会员主页
 * Created by JohnChen on 2018/01/10.
 */

public class StoreManagerHomePageActivity extends AreaManagerHomepageActivity {


    @BindView(R.id.ll_my_member)
    LinearLayout mLlMyMember;
    @BindView(R.id.line_my_member)
    View line;

    @Override
    protected void init() {
        setTitleName(R.string.store_manager_home_page_title);
    }

    protected void getData() {
        mLlMyMember.setVisibility(View.VISIBLE);
        line.setVisibility(View.VISIBLE);
        RemoteDataSourceBase.INSTANCE.getStoreManagerService()
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
                                addMemberSignView("-1");
                            } else if ("1".equals(storeIndex.getStatus())) {
                                addMemberSignView(storeIndex.getType());
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
        mThreeViewScore.setThreeValue(storeIndex.getData1().getCredit1());
        mThreeViewScore.setOneValue(storeIndex.getData1().getCredit2());
        mThreeViewScore.setTwoValue(storeIndex.getData1().getCredit3());
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

    @OnClick(R.id.ll_my_member)
    void onClick(View view) {
        MemberListActivity.start(this);
    }

    private void addToRegisterView() {
        View viewToRegister = LayoutInflater.from(StoreManagerHomePageActivity.this)
                .inflate(R.layout.view_to_register, null, false);
        viewToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreManagerRegisterActivity.start(StoreManagerHomePageActivity.this, "1", getIsArea());
            }
        });
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mLlTip.addView(viewToRegister, params);
    }

    @NonNull
    protected String getIsArea() {
        return "0";
    }

    private void addMemberSignView(String type) {
        View memberSignView = LayoutInflater.from(StoreManagerHomePageActivity.this)
                .inflate(R.layout.view_member_sign, null, false);
        TextView tvMemberSign = (TextView) memberSignView.findViewById(R.id.tv_member_sign);
        if ("-1".equals(type)) {
            tvMemberSign.setText("审核中");
            memberSignView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StoreManagerRegisterActivity.start(StoreManagerHomePageActivity.this, "0", getIsArea());
                }
            });
        } else if ("1".equals(type)) {
            tvMemberSign.setText("金牌店长");
        } else if ("2".equals(type)) {
            tvMemberSign.setText("银牌店长");
        } else if ("3".equals(type)) {
            tvMemberSign.setText("铜牌店长");
        }

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mLlTip.addView(memberSignView, params);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, StoreManagerHomePageActivity.class);
        context.startActivity(starter);
    }
}
