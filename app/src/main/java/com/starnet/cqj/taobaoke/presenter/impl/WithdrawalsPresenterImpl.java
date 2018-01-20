package com.starnet.cqj.taobaoke.presenter.impl;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.CNCBKUser;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.User;
import com.starnet.cqj.taobaoke.presenter.BasePresenterImpl;
import com.starnet.cqj.taobaoke.presenter.IWithdrawalsPresenter;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by mini on 17/11/15.
 */
public class WithdrawalsPresenterImpl extends BasePresenterImpl implements IWithdrawalsPresenter {

    private IView mViewCallback;

    public WithdrawalsPresenterImpl(IView viewCallback) {
        mViewCallback = viewCallback;
    }

    @Override
    public void getScore(String header) {
        RemoteDataSourceBase.INSTANCE.getUserService()
                .person(header)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<JsonCommon<User>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(JsonCommon<User> value) {
                        if (isValidResult(value, mViewCallback)) {
                            mViewCallback.setScore(String.valueOf(value.getData().getCredit1()));
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
    public void getBindUser(String header) {
        RemoteDataSourceBase.INSTANCE.getUserService()
                .cncbk(header)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<JsonCommon<CNCBKUser>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(JsonCommon<CNCBKUser> value) {
                        if (isValidResult(value, mViewCallback)) {
                            mViewCallback.onGetBindUser(value.getData());
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
    public void bindUser(final String header, String userName, String phone) {
        RemoteDataSourceBase.INSTANCE.getUserService()
                .bindCNCBK(header, userName, phone)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<JsonCommon<Object>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(JsonCommon<Object> value) {
                        if (isValidResult(value, mViewCallback)) {
                            mViewCallback.toast("绑定成功");
                            getBindUser(header);
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
    public void cashCNCBK(String header, String score) {
        RemoteDataSourceBase.INSTANCE.getUserService()
                .cashCNCBK(header, score)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<JsonCommon<Object>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(JsonCommon<Object> value) {
                        if (isValidResult(value, mViewCallback)) {
                            mViewCallback.onCash();
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
    public void cashMoney(String header, String name, String userName, String money) {
        RemoteDataSourceBase.INSTANCE.getStoreManagerService()
                .withdraw(header,name,userName,money,1,"")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<JsonCommon<Object>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(JsonCommon<Object> value) {
                        if (isValidResult(value, mViewCallback)) {
                            mViewCallback.onCash();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mViewCallback.toast(R.string.net_error);
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
