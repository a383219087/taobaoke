package com.starnet.cqj.taobaoke.presenter.impl;

import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.User;
import com.starnet.cqj.taobaoke.presenter.BasePresenterImpl;
import com.starnet.cqj.taobaoke.presenter.ILoginPresenter;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/11/07.
 */

public class LoginPresenterImpl extends BasePresenterImpl implements ILoginPresenter {

    private IView mViewCallback;

    public LoginPresenterImpl(IView viewCallback) {
        mViewCallback = viewCallback;
    }

    @Override
    public void login(String mobile, String pwd,String regId) {
        RemoteDataSourceBase.INSTANCE.getUserService()
                .login(mobile, pwd,regId, "0","")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonCommon<User>>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                        mCompositeDisposable.add(disposable);
                    }

                    @Override
                    public void onNext(JsonCommon<User> userJsonCommon) {
                        String code = userJsonCommon.getCode();
                        if ("200".equals(code)) {
                            mViewCallback.onLoginSuccess(userJsonCommon.getData());
                        } else {
                            mViewCallback.toast(userJsonCommon.getMessage());
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

    @Override
    public void wechatLogin(String openId,String regId) {
        RemoteDataSourceBase.INSTANCE.getUserService()
                .login("", "",regId, "1",openId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonCommon<User>>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                        mCompositeDisposable.add(disposable);
                    }

                    @Override
                    public void onNext(JsonCommon<User> userJsonCommon) {
                        String code = userJsonCommon.getCode();
                        if ("200".equals(code)) {
                            mViewCallback.onLoginSuccess(userJsonCommon.getData());
                        } else if("9000".equals(code)){
                            mViewCallback.noBind();
                        }else {
                            mViewCallback.toast(userJsonCommon.getMessage());
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

    @Override
    public void onDestroy() {

    }
}
