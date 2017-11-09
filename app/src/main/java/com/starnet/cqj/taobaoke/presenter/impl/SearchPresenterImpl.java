package com.starnet.cqj.taobaoke.presenter.impl;

import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.User;
import com.starnet.cqj.taobaoke.presenter.ISearchPresenter;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/11/09.
 */

public class SearchPresenterImpl implements ISearchPresenter {

    private IView mViewCallback;
    private RxAppCompatActivity mActivity;

    public SearchPresenterImpl(IView viewCallback) {
        mViewCallback = viewCallback;
        if(viewCallback instanceof RxAppCompatActivity){
            mActivity = (RxAppCompatActivity) viewCallback;
        }
    }

    @Override
    public void getSearchHistory() {
        RemoteDataSourceBase.INSTANCE.getSearchService()
                .person("")
                .compose(mActivity.<JsonCommon<User>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<JsonCommon<User>>() {
                    @Override
                    public void accept(JsonCommon<User> userJsonCommon) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void search(String search) {

    }

    @Override
    public void onDestroy() {

    }
}
