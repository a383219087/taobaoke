package com.starnet.cqj.taobaoke.presenter.impl;

import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.MainMenu;
import com.starnet.cqj.taobaoke.model.Product;
import com.starnet.cqj.taobaoke.model.ProductResult;
import com.starnet.cqj.taobaoke.presenter.BasePresenterImpl;
import com.starnet.cqj.taobaoke.presenter.IHomePagePresenter;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HomePagePresenterImpl extends BasePresenterImpl implements IHomePagePresenter {

    private static final String TAG = "HomePagePresenterImpl";

    private IView mViewCallback;

    public HomePagePresenterImpl(IView viewCallback) {
        mViewCallback = viewCallback;
    }


    @Override
    public void getCategory() {
        RemoteDataSourceBase.INSTANCE.getHomePageService().getCategory()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<JsonCommon<List<MainMenu>>>() {
                    @Override
                    public void accept(JsonCommon<List<MainMenu>> listJsonCommon) throws Exception {
                        if (listJsonCommon.getCode().equals("200")) {
                            mViewCallback.setCategoryList(listJsonCommon.getData());
                        }else{
                            mViewCallback.toast(listJsonCommon.getMessage());
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
    public void getLookBuy() {
        RemoteDataSourceBase.INSTANCE.getHomePageService()
                .getLookBuy(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<JsonCommon<ProductResult<Product>>>() {
                    @Override
                    public void accept(JsonCommon<ProductResult<Product>> listJsonCommon) throws Exception {
                        if (listJsonCommon.getCode().equals("200")) {
                            mViewCallback.setLookBuy(listJsonCommon.getData().getList());
                        }else{
                            mViewCallback.toast(listJsonCommon.getMessage());
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
    public void getRecommend() {
        RemoteDataSourceBase.INSTANCE.getHomePageService()
                .getRecommend(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<JsonCommon<ProductResult<Product>>>() {
                    @Override
                    public void accept(JsonCommon<ProductResult<Product>> listJsonCommon) throws Exception {
                        if (listJsonCommon.getCode().equals("200")) {
                            mViewCallback.setRecommend(listJsonCommon.getData().getList());
                        }else{
                            mViewCallback.toast(listJsonCommon.getMessage());
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
    }
}
