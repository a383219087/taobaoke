package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.IntegralDetail;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.ResultWrapper;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;
import com.starnet.cqj.taobaoke.view.BaseApplication;

import io.reactivex.Observable;

/**
 * Created by mini on 17/11/19.
 */

public class IceIntegralDetailActivity extends ReCheckIntegralDetailActivity {

    @Override
    protected Observable<JsonCommon<ResultWrapper<IntegralDetail>>> getDetailObservable() {
        return RemoteDataSourceBase.INSTANCE.getUserService().djCncbk(((BaseApplication) getApplication()).token,mPage);
    }


    @Override
    protected int getDetailTitle() {
        return R.string.ice_integral_title;
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, IceIntegralDetailActivity.class);
        context.startActivity(starter);
    }
}
