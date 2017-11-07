package com.starnet.cqj.taobaoke.presenter.impl;

import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.presenter.BasePresenterImpl;
import com.starnet.cqj.taobaoke.presenter.IForgetPwdPresenter;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class ForgetPwdPresenterImpl extends BasePresenterImpl implements IForgetPwdPresenter {

    private IView mViewCallback;

    public ForgetPwdPresenterImpl(IView viewCallback) {
        mViewCallback = viewCallback;
    }

    @Override
    public void sendSMS(String mobile) {
        RemoteDataSourceBase.INSTANCE.getCommonService()
                .sendSMS(mobile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonCommon<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(JsonCommon<String> value) {
                        String code = value.getCode();
                        if ("200".equals(code)) {
                            mViewCallback.onGetCode();
                            mViewCallback.toast("验证码已下发到您手机号");
                        } else {
                            mViewCallback.toast(value.getMessage());
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
    public void reset(String mobile, String pwd, String pwdAgain, String code) {
        RemoteDataSourceBase.INSTANCE.getUserService()
                .resetPwd(mobile, pwd, pwdAgain, code)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonCommon<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(JsonCommon<String> value) {
                        String code = value.getCode();
                        if ("200".equals(code)) {
                            mViewCallback.onResetSuccess();
                        } else {
                            mViewCallback.toast(value.getMessage());
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

    }
}
