package com.starnet.cqj.taobaoke.presenter.impl;

import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.User;
import com.starnet.cqj.taobaoke.presenter.BasePresenterImpl;
import com.starnet.cqj.taobaoke.presenter.ILoginPresenter;
import com.starnet.cqj.taobaoke.remote.CodeParser;
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
    public void login(String mobile, String pwd) {
        RemoteDataSourceBase.INSTANCE.getLoginService()
                .login(mobile, pwd, "0")
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
                            mViewCallback.toast(CodeParser.parse(code));
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
