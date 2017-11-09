package com.starnet.cqj.taobaoke.presenter;

/**
 * Created by Administrator on 2017/11/09.
 */

public interface ISearchPresenter extends BasePresenter{

    void getSearchHistory();

    void search(String search);

    interface IView extends BasePresenter.IView{

    }
}
