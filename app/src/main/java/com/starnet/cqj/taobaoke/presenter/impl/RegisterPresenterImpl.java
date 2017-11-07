package com.starnet.cqj.taobaoke.presenter.impl;

import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.presenter.BasePresenterImpl;
import com.starnet.cqj.taobaoke.presenter.IRegisterPresenter;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterPresenterImpl extends BasePresenterImpl implements IRegisterPresenter {

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
                .subscribe(new Observer<JsonCommon<List<String>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(JsonCommon<List<String>> value) {
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
    public void register(String mobile, String pwd, String nickName,String code) {
        RemoteDataSourceBase.INSTANCE.getUserService()
                .register(mobile, pwd, nickName,code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonCommon<List<String>>>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                        mCompositeDisposable.add(disposable);
                    }

                    @Override
                    public void onNext(JsonCommon<List<String>> stringJsonCommon) {
                        String code = stringJsonCommon.getCode();
                        if ("200".equals(code)) {
                            mViewCallback.onRegisterSuccess();
                        } else {
                            mViewCallback.toast(stringJsonCommon.getMessage());
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

//    @Override
//    public void verifyCode(String mobile, String code) {
//        RemoteDataSourceBase.INSTANCE.getCommonService()
//                .verifySMS(mobile,code)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<JsonCommon<String>>() {
//                    @Override
//                    public void onSubscribe(Disposable disposable) {
//                        mCompositeDisposable.add(disposable);
//                    }
//
//                    @Override
//                    public void onNext(JsonCommon<String> stringJsonCommon) {
//                        String code = stringJsonCommon.getCode();
//                        if ("200".equals(code)) {
//                            mViewCallback.onVerifySuccess();
//                        } else {
//                            mViewCallback.toast(CodeParser.parse(code));
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable throwable) {
//                        throwable.printStackTrace();
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
