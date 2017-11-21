package com.starnet.cqj.taobaoke.presenter.impl;

import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.Message;
import com.starnet.cqj.taobaoke.presenter.BasePresenterImpl;
import com.starnet.cqj.taobaoke.presenter.IMessagePresenter;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MessagePresenterImpl extends BasePresenterImpl implements IMessagePresenter {

    private IView mViewCallback;

    public MessagePresenterImpl(IView viewCallback) {
        mViewCallback = viewCallback;
    }

    @Override
    public void getMessageList(String header) {
        RemoteDataSourceBase.INSTANCE.getUserService()
                .sysmsg(header)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mCompositeDisposable.add(disposable);
                    }
                })
                .subscribe(new Consumer<JsonCommon<List<Message>>>() {
                    @Override
                    public void accept(JsonCommon<List<Message>> listJsonCommon) throws Exception {
                        if (isValidResult(listJsonCommon, mViewCallback)) {
                            mViewCallback.setMessageList(listJsonCommon.getData());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });

    }

    @Override
    public void deleteMessage(String header, String id) {
        RemoteDataSourceBase.INSTANCE.getUserService()
                .delMsg(header, id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mCompositeDisposable.add(disposable);
                    }
                })
                .subscribe(new Consumer<JsonCommon<Object>>() {
                    @Override
                    public void accept(JsonCommon<Object> listJsonCommon) throws Exception {
                        if (isValidResult(listJsonCommon, mViewCallback)) {
                            mViewCallback.onDelete();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void allRead(String header) {
        RemoteDataSourceBase.INSTANCE.getUserService()
                .msgRead(header)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mCompositeDisposable.add(disposable);
                    }
                })
                .subscribe(new Consumer<JsonCommon<Object>>() {
                    @Override
                    public void accept(JsonCommon<Object> objectJsonCommon) throws Exception {
                        if (isValidResult(objectJsonCommon, mViewCallback)) {
                            mViewCallback.onAllRead();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
