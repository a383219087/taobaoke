package com.starnet.cqj.taobaoke.presenter.impl;

import com.starnet.cqj.taobaoke.model.BuyTip;
import com.starnet.cqj.taobaoke.model.HomePageBanner;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.MainMenu;
import com.starnet.cqj.taobaoke.model.Product;
import com.starnet.cqj.taobaoke.model.ResultWrapper;
import com.starnet.cqj.taobaoke.presenter.BasePresenterImpl;
import com.starnet.cqj.taobaoke.presenter.IHomePagePresenter;
import com.starnet.cqj.taobaoke.remote.Constant;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
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
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mCompositeDisposable.add(disposable);
                    }
                })
                .subscribe(new Consumer<JsonCommon<List<MainMenu>>>() {
                    @Override
                    public void accept(JsonCommon<List<MainMenu>> listJsonCommon) throws Exception {
                        if (listJsonCommon.getCode().equals("200")) {
                            mViewCallback.setCategoryList(listJsonCommon.getData());
                        } else {
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
                .getLookBuy(1,"","","")
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
                    public void accept(JsonCommon<ResultWrapper<Product>> listJsonCommon) throws Exception {
                        if (listJsonCommon.getCode().equals("200")) {
                            mViewCallback.setLookBuy(listJsonCommon.getData().getList());
                        } else {
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
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mCompositeDisposable.add(disposable);
                    }
                })
                .subscribe(new Consumer<JsonCommon<ResultWrapper<Product>>>() {
                    @Override
                    public void accept(JsonCommon<ResultWrapper<Product>> listJsonCommon) throws Exception {
                        if (listJsonCommon.getCode().equals("200")) {
                            mViewCallback.setRecommend(listJsonCommon.getData().getList());
                        } else {
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
    public void getBanner() {
        RemoteDataSourceBase.INSTANCE.getHomePageService()
                .banner()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mCompositeDisposable.add(disposable);
                    }
                })
                .subscribe(new Consumer<JsonCommon<HomePageBanner>>() {
                    @Override
                    public void accept(JsonCommon<HomePageBanner> homePageBannerJsonCommon) throws Exception {
                        if (homePageBannerJsonCommon.getCode().equals("200")) {
                            mViewCallback.setBanner(homePageBannerJsonCommon.getData());
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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
