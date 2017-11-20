package com.starnet.cqj.taobaoke.presenter.impl;

import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.Message;
import com.starnet.cqj.taobaoke.presenter.BasePresenterImpl;
import com.starnet.cqj.taobaoke.presenter.IMessageDetailPresenter;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by mini on 17/11/20.
 */
public class MessageDetailPresenterImpl extends BasePresenterImpl implements IMessageDetailPresenter {


    private IView mViewCallback;

    public MessageDetailPresenterImpl(IView viewCallback) {
        mViewCallback = viewCallback;
    }


    @Override
    public void getMessage(String header, String id) {
        RemoteDataSourceBase.INSTANCE.getUserService()
                .message(header,id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mCompositeDisposable.add(disposable);
                    }
                })
                .subscribe(new Consumer<JsonCommon<Message>>() {
                    @Override
                    public void accept(JsonCommon<Message> listJsonCommon) throws Exception {
                        if (isValidResult(listJsonCommon, mViewCallback)) {
                            mViewCallback.setMessage(listJsonCommon.getData());
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
