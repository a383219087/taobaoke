package com.starnet.cqj.taobaoke.presenter.impl;

import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.presenter.BasePresenterImpl;
import com.starnet.cqj.taobaoke.presenter.IBindPhonePresenter;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BindPhonePresenterImpl extends BasePresenterImpl implements IBindPhonePresenter {

    private IView mViewCallback;

    public BindPhonePresenterImpl(IView viewCallback) {
        mViewCallback = viewCallback;
    }

    @Override
    public void getCode(String mobile) {
        RemoteDataSourceBase.INSTANCE.getCommonService()
                .sendSMS(mobile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonCommon<Object>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(JsonCommon<Object> value) {
                        if (isValidResult(value,mViewCallback)) {
                            mViewCallback.onGetCode();
                            mViewCallback.toast("验证码已下发到您手机号");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void bind(String token,String phone, String code) {
        RemoteDataSourceBase.INSTANCE.getUserService()
                .bindPhone(token, phone, code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonCommon<Object>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(JsonCommon<Object> value) {
                        if (isValidResult(value,mViewCallback)) {
                            mViewCallback.onBind();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
