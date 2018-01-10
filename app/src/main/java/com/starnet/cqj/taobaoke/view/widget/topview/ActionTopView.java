package com.starnet.cqj.taobaoke.view.widget.topview;

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
import com.starnet.cqj.taobaoke.view.BaseApplication;
import com.starnet.cqj.taobaoke.view.activity.UserSignActivity;
import com.starnet.cqj.taobaoke.view.activity.WebViewActivity;
import com.starnet.cqj.taobaoke.view.widget.SharePopupWindow;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
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
    @BindView(R.id.btn_action_get)
    TextView mBtnActionBuy;

    private RxAppCompatActivity mActivity;
    private String mUrl;

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
        mActivity = (RxAppCompatActivity) getContext();
        getData();
    }

    public void getData() {
        RemoteDataSourceBase.INSTANCE.getActionService()
                .active()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(mActivity.<JsonCommon<BuyAction>>bindToLifecycle())
                .subscribe(new Consumer<JsonCommon<BuyAction>>() {
                    @Override
                    public void accept(JsonCommon<BuyAction> actionJsonCommon) throws Exception {
                        if ("200".equals(actionJsonCommon.getCode())) {
                            mUrl = actionJsonCommon.getData().getUrl();
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
                            if(actionJsonCommon.getData().isOver()){
                                mBtnActionBuy.setVisibility(VISIBLE);
                                if(actionJsonCommon.getData().isReceive()){
                                    mBtnActionBuy.setEnabled(false);
                                    mBtnActionBuy.setText(R.string.get_buy_action_done);
                                }else{
                                    mBtnActionBuy.setEnabled(true);
                                    mBtnActionBuy.setText(R.string.get_buy_action);
                                }
                            }else{
                                mBtnActionBuy.setVisibility(GONE);
                            }
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

    @OnClick({R.id.ll_action_sign, R.id.action_share, R.id.btn_action_get, R.id.card_buy_action})
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
            case R.id.card_buy_action:
                if(!TextUtils.isEmpty(mUrl)) {
                    WebViewActivity.start(mActivity, mUrl);
                }
                break;
            case R.id.btn_action_get:
                RemoteDataSourceBase.INSTANCE.getUserService()
                        .activeReceive(((BaseApplication) mActivity.getApplication()).getToken())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(mActivity.<JsonCommon<Object>>bindToLifecycle())
                        .subscribe(new Consumer<JsonCommon<Object>>() {
                            @Override
                            public void accept(JsonCommon<Object> objectJsonCommon) throws Exception {
                                if("200".equals(objectJsonCommon.getCode())){
                                    Toast.makeText(mActivity, "领取成功", Toast.LENGTH_SHORT).show();
                                    mBtnActionBuy.setEnabled(false);
                                    mBtnActionBuy.setText(R.string.get_buy_action_done);
                                }else{
                                    Toast.makeText(mActivity, objectJsonCommon.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                throwable.printStackTrace();
                            }
                        });
                break;
        }
    }

}
