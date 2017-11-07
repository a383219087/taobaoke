package com.starnet.cqj.taobaoke.presenter;

import io.reactivex.disposables.CompositeDisposable;


public class BasePresenterImpl {

    protected CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public void onDestroy(){
        mCompositeDisposable.dispose();
    }



}
