package com.starnet.cqj.taobaoke.presenter.impl;

import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.User;
import com.starnet.cqj.taobaoke.model.WechatUser;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/11/17.
 */

public class BindNewPresenterImpl extends RegisterPresenterImpl {


    private IView mViewCallback;
    private WechatUser mUser;

    public BindNewPresenterImpl(IView viewCallback,WechatUser user) {
        super(viewCallback);
        mViewCallback = viewCallback;
        mUser = user;
    }

    @Override
    public void register(String mobile, String pwd, String nickName, String code) {
        RemoteDataSourceBase.INSTANCE.getUserService()
                .bindUser(mobile,mUser.getOpenid(),mUser.getNickname(),
                        pwd,pwd,mUser.getAvatar(),mUser.getUnionId(),
                        mUser.getGender(),code,"1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonCommon<User>>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(JsonCommon<User> userJsonCommon) {
                        if(isValidResult(userJsonCommon,mViewCallback)){
                            mViewCallback.onRegisterSuccess(userJsonCommon.getData());
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
