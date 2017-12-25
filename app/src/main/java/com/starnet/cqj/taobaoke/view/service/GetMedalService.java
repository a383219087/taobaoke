package com.starnet.cqj.taobaoke.view.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.Medal;
import com.starnet.cqj.taobaoke.remote.Constant;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;
import com.starnet.cqj.taobaoke.view.BaseApplication;
import com.starnet.cqj.taobaoke.view.activity.GetMedalDialogActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class GetMedalService extends Service {

    private Disposable mDisposable;

    @Override
    public void onCreate() {
        super.onCreate();
        Observable.interval(1, Constant.GET_MEDAL_SERVICE_SECOND, TimeUnit.SECONDS)
                .flatMap(new Function<Long, ObservableSource<JsonCommon<Medal>>>() {
                    @Override
                    public ObservableSource<JsonCommon<Medal>> apply(Long aLong) throws Exception {
                        return RemoteDataSourceBase.INSTANCE.getUserService().promote(((BaseApplication) getApplication()).getToken(false));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonCommon<Medal>>() {

                    @Override
                    public void onSubscribe(Disposable disposable) {
                        mDisposable = disposable;
                    }

                    @Override
                    public void onNext(JsonCommon<Medal> medalJsonCommon) {
                        if ("200".equals(medalJsonCommon.getCode())) {
                            GetMedalDialogActivity.start(getApplicationContext(), medalJsonCommon.getData());
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        stopInterval();
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopInterval();
    }

    private void stopInterval() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }
}
