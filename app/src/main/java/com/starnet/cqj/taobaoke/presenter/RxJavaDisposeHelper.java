package com.starnet.cqj.taobaoke.presenter;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by mini on 17/11/4.
 */

public class RxJavaDisposeHelper {

    protected CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public void onDestroy(){
        mCompositeDisposable.dispose();
    }



}
