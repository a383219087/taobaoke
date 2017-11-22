package com.starnet.cqj.taobaoke.presenter.impl;

import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.User;
import com.starnet.cqj.taobaoke.model.WechatUser;
import com.starnet.cqj.taobaoke.presenter.BasePresenterImpl;
import com.starnet.cqj.taobaoke.presenter.IBindExistPresenter;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BindExistPresenterImpl extends BasePresenterImpl implements IBindExistPresenter {

    private IView mViewCallback;

    public BindExistPresenterImpl(IView viewCallback) {
        mViewCallback = viewCallback;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
    public void bind(WechatUser user,String regId) {
        RemoteDataSourceBase.INSTANCE.getUserService()
                .bindUser(regId,user.getMobile(),user.getOpenid(),
                        user.getNickname(),user.getPassword(),
                        user.getPassword_confirm(),user.getAvatar(),
                        user.getUnionId(),user.getGender(),
                        user.getCode(),user.getIs_create())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<JsonCommon<User>>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                        mCompositeDisposable.add(disposable);
                    }

                    @Override
                    public void onNext(JsonCommon<User> objectJsonCommon) {
                        if(isValidResult(objectJsonCommon,mViewCallback)){
                            mViewCallback.onBind(objectJsonCommon.getData());
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
}
