package com.starnet.cqj.taobaoke.presenter.impl;

import com.starnet.cqj.taobaoke.model.BuyTip;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.Product;
import com.starnet.cqj.taobaoke.model.ResultWrapper;
import com.starnet.cqj.taobaoke.model.SearchType;
import com.starnet.cqj.taobaoke.presenter.BasePresenterImpl;
import com.starnet.cqj.taobaoke.presenter.IProductListPresenter;
import com.starnet.cqj.taobaoke.remote.Constant;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/11/21.
 */

public class ProductListPresenterImpl extends BasePresenterImpl implements IProductListPresenter {

    private IView mViewCallback;
    private SearchType mSearchType;

    public ProductListPresenterImpl(IView viewCallback, SearchType searchType) {
        mViewCallback = viewCallback;
        mSearchType = searchType;
    }

    @Override
    public void getData(int page, String key, String typeName, String minFee, String maxFee, String cateId) {
        getProvider(page, key, typeName, minFee, maxFee, cateId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mCompositeDisposable.add(disposable);
                    }
                })
                .subscribe(new Consumer<JsonCommon<ResultWrapper<Product>>>() {
                    @Override
                    public void accept(JsonCommon<ResultWrapper<Product>> resultWrapperJsonCommon) throws Exception {
                        if (isValidResult(resultWrapperJsonCommon, mViewCallback)) {
                            mViewCallback.setResult(resultWrapperJsonCommon.getData().getList());
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
    public void getTip() {
        Observable.interval(100, Constant.MAIN_TIP_GET_INTERVAL, TimeUnit.MILLISECONDS)
                .flatMap(new Function<Long, ObservableSource<JsonCommon<BuyTip>>>() {
                    @Override
                    public ObservableSource<JsonCommon<BuyTip>> apply(Long aLong) throws Exception {
                        return RemoteDataSourceBase.INSTANCE.getHomePageService()
                                .volist();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mCompositeDisposable.add(disposable);
                    }
                })
                .subscribe(new Consumer<JsonCommon<BuyTip>>() {
                    @Override
                    public void accept(JsonCommon<BuyTip> homePageBannerJsonCommon) throws Exception {
                        if (homePageBannerJsonCommon.getCode().equals("200")) {
                            mViewCallback.setTip(homePageBannerJsonCommon.getData());
                        } else {
                            mViewCallback.toast(homePageBannerJsonCommon.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }

    private Observable<JsonCommon<ResultWrapper<Product>>> getProvider(int page, String key, String typeName, String minFee, String maxFee, String cateId) {
        if (mSearchType == null) {
            return RemoteDataSourceBase.INSTANCE.getSearchService().site(page, key, typeName, minFee, maxFee, cateId);
        }
        switch (mSearchType) {
            case SITE:
                return RemoteDataSourceBase.INSTANCE.getSearchService().site(page, key, typeName, minFee, maxFee, cateId);
            case JHS:
                return RemoteDataSourceBase.INSTANCE.getSearchService().jhs(page, typeName, minFee, maxFee, cateId);
            case TQG:
                return RemoteDataSourceBase.INSTANCE.getSearchService().tqg(page, typeName, minFee, maxFee, cateId);
            case BMQD:
                return RemoteDataSourceBase.INSTANCE.getSearchService().today(page, typeName, minFee, maxFee, cateId);
            case PPQ:
                return RemoteDataSourceBase.INSTANCE.getSearchService().ppq(page, typeName, minFee, maxFee, cateId);
            case VIDEO:
                return RemoteDataSourceBase.INSTANCE.getHomePageService().getLookBuy(page, typeName, minFee, maxFee);
            default:
                return RemoteDataSourceBase.INSTANCE.getSearchService().site(page, key, typeName, minFee, maxFee, cateId);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
