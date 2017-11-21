package com.starnet.cqj.taobaoke.presenter.impl;

import com.starnet.cqj.taobaoke.model.Address;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.ResultWrapper;
import com.starnet.cqj.taobaoke.presenter.BasePresenterImpl;
import com.starnet.cqj.taobaoke.presenter.IAddressListPresenter;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by mini on 17/11/12.
 */
public class AddressListPresenterImpl extends BasePresenterImpl implements IAddressListPresenter {

    private IView mViewCallback;

    public AddressListPresenterImpl(IView viewCallback) {
        mViewCallback = viewCallback;
    }

    @Override
    public void getAddress(String token) {
        RemoteDataSourceBase.INSTANCE.getUserService()
                .getAddress(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonCommon<ResultWrapper<Address>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(JsonCommon<ResultWrapper<Address>> value) {
                        if (isValidResult(value, mViewCallback)) {
                            mViewCallback.onGetAddressDone(value.getData().getList());
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
    public void deleteAddress(String token, final Address address) {
        int addressID;
        try {
            addressID = Integer.parseInt(address.getID());
        } catch (NumberFormatException e) {
            mViewCallback.toast("数据错误，请重试");
            return;
        }
        RemoteDataSourceBase.INSTANCE.getUserService()
                .deleteAddress(token, addressID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonCommon<Object>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(JsonCommon<Object> value) {
                        if (isValidResult(value, mViewCallback)) {
                            mViewCallback.onDelete(address);
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
    public void editDefault(String token, Address address) {
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
                            mViewCallback.onEditDefault();
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
}
