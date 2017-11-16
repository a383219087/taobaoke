package com.starnet.cqj.taobaoke.presenter.impl;

import com.starnet.cqj.taobaoke.presenter.ISearchPresenter;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * Created by Administrator on 2017/11/09.
 */

public class SearchPresenterImpl implements ISearchPresenter {

    private IView mViewCallback;
    private RxAppCompatActivity mActivity;

    public SearchPresenterImpl(IView viewCallback) {
        mViewCallback = viewCallback;
        if(viewCallback instanceof RxAppCompatActivity){
            mActivity = (RxAppCompatActivity) viewCallback;
        }
    }

    @Override
    public void getSearchHistory() {

    }

    @Override
    public void search(String search) {

    }

    @Override
    public void onDestroy() {

    }
}
