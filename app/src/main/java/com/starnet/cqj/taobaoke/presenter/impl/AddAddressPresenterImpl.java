package com.starnet.cqj.taobaoke.presenter.impl;

import android.app.Application;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.Address;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.city.CityResult;
import com.starnet.cqj.taobaoke.presenter.BasePresenterImpl;
import com.starnet.cqj.taobaoke.presenter.IAddAddressPresenter;
import com.starnet.cqj.taobaoke.remote.CityData;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddAddressPresenterImpl extends BasePresenterImpl implements IAddAddressPresenter {

    private IView mViewCallback;

    public AddAddressPresenterImpl(IView viewCallback) {
        mViewCallback = viewCallback;
    }

    @Override
    public void initCity(final Application application) {
        CityData.getCity(application)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<CityResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CityResult value) {
                        mViewCallback.onInitCity(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mViewCallback.toast(R.string.net_error);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void addAddress(String token, Address address) {

        int isDefault = 0;
        try {
            isDefault = Integer.parseInt(address.getIsDefault());
        } catch (NumberFormatException ignored) {
        }
        int id = 0;
        try {
            id = Integer.parseInt(address.getID());
        } catch (NumberFormatException ignored) {
        }
        RemoteDataSourceBase.INSTANCE.getUserService()
                .saveAddress(token, id, address.getArea(), address.getAddress(), isDefault, address.getPhone(), address.getName())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<JsonCommon<Object>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(JsonCommon<Object> value) {
                        if (isValidResult(value, mViewCallback)) {
                            mViewCallback.onAddAddress();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mViewCallback.toast(R.string.net_error);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onDestroy() {

    }
}
