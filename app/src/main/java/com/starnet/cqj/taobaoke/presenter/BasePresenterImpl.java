package com.starnet.cqj.taobaoke.presenter;

import com.starnet.cqj.taobaoke.model.JsonCommon;

import io.reactivex.disposables.CompositeDisposable;


public class BasePresenterImpl {

    protected CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public void onDestroy() {
        mCompositeDisposable.dispose();
    }

    public <T> boolean isValidResult(JsonCommon<T> jsonCommon, BasePresenter.IView viewCallback) {
        if (jsonCommon.getCode().equals("200")) {
            return true;
        } else {
            viewCallback.toast(jsonCommon.getMessage());
            return false;
        }
    }

}
