package com.starnet.cqj.taobaoke.presenter.impl;

import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.MainMenu;
import com.starnet.cqj.taobaoke.presenter.IHomePagePresenter;
import com.starnet.cqj.taobaoke.presenter.RxJavaDisposeHelper;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by mini on 17/11/4.
 */
public class HomePagePresenterImpl extends RxJavaDisposeHelper implements IHomePagePresenter {

    private IView mViewCallback;

    public HomePagePresenterImpl(IView viewCallback) {
        mViewCallback = viewCallback;
    }

    private static final String TAG = "HomePagePresenterImpl";

    @Override
    public void getCategory() {
        RemoteDataSourceBase.INSTANCE.getHomePageService().getCategory()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<JsonCommon<MainMenu>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(JsonCommon<MainMenu> value) {
                        if (value.getCode().equals("200")) {
                            mViewCallback.setCategoryList(value.getData());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mViewCallback.toast(e.getMessage());
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
