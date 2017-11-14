package com.starnet.cqj.taobaoke.presenter.impl;

import com.starnet.cqj.taobaoke.model.Address;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.ProductResult;
import com.starnet.cqj.taobaoke.presenter.BasePresenterImpl;
import com.starnet.cqj.taobaoke.presenter.IntegralProductDetailPresenter;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class IntegralProductDetailPresenterImpl extends BasePresenterImpl implements IntegralProductDetailPresenter {

    private IView mViewCallback;

    public IntegralProductDetailPresenterImpl(IView viewCallback) {
        mViewCallback = viewCallback;
    }

    @Override
    public void getAddress(String header) {
        RemoteDataSourceBase.INSTANCE.getUserService()
                .getAddress(header)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonCommon<ProductResult<Address>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(JsonCommon<ProductResult<Address>> value) {
                        if (isValidResult(value, mViewCallback)) {
                            List<Address> list = value.getData().getList();
                            if (list != null && !list.isEmpty()) {
                                mViewCallback.onGetAddress(list.get(0));
                                return;
                            }
                            mViewCallback.onGetAddress(null);
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
    public void exchange(String header, String score, String address, String phone, String integralId, String name) {
        RemoteDataSourceBase.INSTANCE.getUserService()
                .integralOrder(header, score, address, phone, integralId, name)
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
                            mViewCallback.toast("兑换成功");
                            mViewCallback.onExchange();
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
    public void onDestroy() {
        super.onDestroy();
    }
}
