package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.OrderShareScore;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;
import com.starnet.cqj.taobaoke.view.BaseApplication;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class GetIntegralDialogActivity extends BaseActivity {

    public static final String KEY_ORDER_ID = "order_id";
    @BindView(R.id.dialog_tv_name)
    TextView mTvIntegral;

    @Override
    protected int getContentView() {
        return R.layout.dialog_get_integral;
    }

    @Override
    protected void init() {
        String orderId = getIntent().getStringExtra(KEY_ORDER_ID);
        RemoteDataSourceBase.INSTANCE.getUserService()
                .shareRecord(((BaseApplication) getApplication()).getToken(),
                        orderId == null ? "" : orderId)
                .compose(this.<JsonCommon<OrderShareScore>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JsonCommon<OrderShareScore>>() {
                    @Override
                    public void accept(JsonCommon<OrderShareScore> orderShareScoreJsonCommon) throws Exception {
                        if ("200".equals(orderShareScoreJsonCommon.getCode())) {
                            String score = orderShareScoreJsonCommon.getData().getScore();
                            mTvIntegral.setText(score);
                        } else {
                            toast(orderShareScoreJsonCommon.getMessage());
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

    @OnClick(R.id.btn_done)
    public void onViewClicked() {
        finish();
    }

    public static void start(Context context, String orderId) {
        Intent starter = new Intent(context, GetIntegralDialogActivity.class);
        starter.putExtra(KEY_ORDER_ID, orderId);
        context.startActivity(starter);
    }

}
