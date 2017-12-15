package com.starnet.cqj.taobaoke.view.widget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.BuyAction;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;
import com.starnet.cqj.taobaoke.utils.RxBus;
import com.starnet.cqj.taobaoke.utils.event.ToHomePageEvent;
import com.starnet.cqj.taobaoke.view.BaseApplication;
import com.starnet.cqj.taobaoke.view.activity.UserSignActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class ActionTopView extends LinearLayout {

    @BindView(R.id.tv_action_title)
    TextView mTvActionTitle;
    @BindView(R.id.tv_action_start_time)
    TextView mTvActionStartTime;
    @BindView(R.id.tv_action_end_time)
    TextView mTvActionEndTime;
    @BindView(R.id.pb_action)
    ProgressBar mPbAction;
    @BindView(R.id.tv_action_condition)
    TextView mTvActionCondition;

    private Disposable mDisposable;
    private Activity mActivity;

    public ActionTopView(Context context) {
        super(context);
        init();
    }

    public ActionTopView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ActionTopView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        LayoutInflater.from(getContext()).inflate(R.layout.view_action_top, this, true);
        ButterKnife.bind(this);
        mActivity = (Activity) getContext();
        getData();
    }

    public void getData() {
        RemoteDataSourceBase.INSTANCE.getActionService()
                .active()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {

                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mDisposable = disposable;
                    }
                })
                .subscribe(new Consumer<JsonCommon<BuyAction>>() {
                    @Override
                    public void accept(JsonCommon<BuyAction> actionJsonCommon) throws Exception {
                        if ("200".equals(actionJsonCommon.getCode())) {
                            mTvActionTitle.setText(actionJsonCommon.getData().getName());
                            mTvActionStartTime.setText(actionJsonCommon.getData().getStime());
                            mTvActionEndTime.setText(actionJsonCommon.getData().getEtime());
                            try {
                                int max = Integer.parseInt(actionJsonCommon.getData().getOrders());
                                int progress = Integer.parseInt(actionJsonCommon.getData().getCount());
                                mPbAction.setMax(max);
                                mPbAction.setProgress(progress);
                            } catch (NumberFormatException ignored) {
                            }
                            mTvActionCondition.setText(actionJsonCommon.getData().getMsg());
                        } else {
                            Toast.makeText(getContext(), actionJsonCommon.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }

    @OnClick({R.id.ll_action_sign, R.id.action_share, R.id.btn_action_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_action_sign:
                if (TextUtils.isEmpty(((BaseApplication) mActivity.getApplication()).getToken())) {
                    return;
                }
                UserSignActivity.start(mActivity);
                break;
            case R.id.action_share:
                if (TextUtils.isEmpty(((BaseApplication) mActivity.getApplication()).getToken())) {
                    return;
                }
                SharePopupWindow sharePopupWindow = new SharePopupWindow(mActivity);
                sharePopupWindow.showAtLocation(mActivity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.btn_action_buy:
                RxBus.getInstance().send(new ToHomePageEvent());
                break;
        }
    }

    public void onDestroy() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }
}
