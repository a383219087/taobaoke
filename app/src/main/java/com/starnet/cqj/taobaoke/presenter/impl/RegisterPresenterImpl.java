package com.starnet.cqj.taobaoke.presenter.impl;

import android.util.Log;

import com.starnet.cqj.taobaoke.presenter.IRegisterPresenter;
import com.starnet.cqj.taobaoke.presenter.RxJavaDisposeHelper;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by mini on 17/11/4.
 */
public class RegisterPresenterImpl extends RxJavaDisposeHelper implements IRegisterPresenter {

    private IView mViewCallback;

    public RegisterPresenterImpl(IView viewCallback) {
        mViewCallback = viewCallback;
    }

    private static final String TAG = "RegisterPresenterImpl";

    @Override
    public void sendSMS(String mobile) {
        RemoteDataSourceBase.INSTANCE.getCommonService()
                .sendSMS(mobile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(String value) {
                        Log.e(TAG, "onNext: " + value);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void register(String mobile, String pwd, String nickName) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
