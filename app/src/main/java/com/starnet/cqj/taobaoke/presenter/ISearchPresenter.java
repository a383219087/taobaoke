package com.starnet.cqj.taobaoke.presenter;

import com.starnet.cqj.taobaoke.model.Product;

import java.util.List;

/**
 * Created by Administrator on 2017/11/09.
 */

public interface ISearchPresenter extends BasePresenter{

    void search(int page,String search);

    void superSearch(String key);

    interface IView extends BasePresenter.IView{
        void searchResult(List<Product> productList);

        void superResult(List<Product> productList);
    }
}
