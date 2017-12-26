package com.starnet.cqj.taobaoke.presenter.impl;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.Product;
import com.starnet.cqj.taobaoke.model.ResultWrapper;
import com.starnet.cqj.taobaoke.presenter.BasePresenterImpl;
import com.starnet.cqj.taobaoke.presenter.ISearchPresenter;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/11/09.
 */

public class SearchPresenterImpl extends BasePresenterImpl implements ISearchPresenter {

    private IView mViewCallback;
    private RxAppCompatActivity mActivity;

    public SearchPresenterImpl(IView viewCallback) {
        mViewCallback = viewCallback;
        if(viewCallback instanceof RxAppCompatActivity){
            mActivity = (RxAppCompatActivity) viewCallback;
        }
    }

    @Override
    public void search(int page,String search) {
        RemoteDataSourceBase.INSTANCE.getSearchService()
                .superSearch(search, page, 0, 3)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(mActivity.<JsonCommon<ResultWrapper<Product>>>bindToLifecycle())
                .subscribe(new Consumer<JsonCommon<ResultWrapper<Product>>>() {
                    @Override
                    public void accept(JsonCommon<ResultWrapper<Product>> resultWrapperJsonCommon) throws Exception {
                        if(isValidResult(resultWrapperJsonCommon,mViewCallback)){
                            mViewCallback.searchResult(resultWrapperJsonCommon.getData().getList());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        mViewCallback.toast(R.string.net_error);
                    }
                });
    }

    @Override
    public void superSearch(String key) {
        RemoteDataSourceBase.INSTANCE.getSearchService()
                .superSearch(key,1,0,3)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(mActivity.<JsonCommon<ResultWrapper<Product>>>bindToLifecycle())
                .subscribe(new Consumer<JsonCommon<ResultWrapper<Product>>>() {
                    @Override
                    public void accept(JsonCommon<ResultWrapper<Product>> resultWrapperJsonCommon) throws Exception {
                        if(isValidResult(resultWrapperJsonCommon,mViewCallback)){
                            mViewCallback.superResult(resultWrapperJsonCommon.getData().getList());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        mViewCallback.toast(R.string.net_error);
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
