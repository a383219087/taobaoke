package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.IntegralDetail;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.ResultWrapper;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;
import com.starnet.cqj.taobaoke.view.BaseApplication;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * Created by mini on 17/11/19.
 */

public class UseIntegralDetailActivity extends ReCheckIntegralDetailActivity {


    @BindView(R.id.title_rightbutton)
    Button mTitleRightbutton;

    @Override
    protected Observable<JsonCommon<ResultWrapper<IntegralDetail>>> getDetailObservable() {
        return RemoteDataSourceBase.INSTANCE.getUserService().bCncbk(((BaseApplication) getApplication()).getToken(),mPage);
    }

    @Override
    protected void init() {
        super.init();
        mTitleRightbutton.setVisibility(View.VISIBLE);
        mTitleRightbutton.setText("提现");
    }

    @Override
    protected int getDetailTitle() {
        return R.string.use_integral_title;
    }


    @OnClick(R.id.title_rightbutton)
    public void onViewClicked() {
        WithdrawalsActivity.start(this);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, UseIntegralDetailActivity.class);
        context.startActivity(starter);
    }
}
