package com.starnet.cqj.taobaoke.presenter.impl;

import com.starnet.cqj.taobaoke.model.Article;
import com.starnet.cqj.taobaoke.model.HotItem;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.ResultWithBanner;
import com.starnet.cqj.taobaoke.model.ResultWrapper;
import com.starnet.cqj.taobaoke.presenter.BasePresenterImpl;
import com.starnet.cqj.taobaoke.presenter.IHotPresenter;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by mini on 17/11/18.
 */
public class HotPresenterImpl extends BasePresenterImpl implements IHotPresenter {

    private IView mViewCallback;

    public HotPresenterImpl(IView viewCallback) {
        mViewCallback = viewCallback;
    }

    @Override
    public void getItem() {
        RemoteDataSourceBase.INSTANCE.getHotService()
                .getItem()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<JsonCommon<ResultWrapper<HotItem>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(JsonCommon<ResultWrapper<HotItem>> value) {
                        if (isValidResult(value, mViewCallback)) {
                            mViewCallback.onGetItem(value.getData().getList());
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
    public void getList(String itemId) {
        RemoteDataSourceBase.INSTANCE.getHotService()
                .getHotArticle(itemId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<JsonCommon<ResultWithBanner<Article>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(JsonCommon<ResultWithBanner<Article>> value) {
                        if (isValidResult(value, mViewCallback)) {
                            mViewCallback.setBanner(value.getData().getBannerList());
                            mViewCallback.setArticleList(value.getData().getList());
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
    public void getMoreList(int page, String itemId) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
