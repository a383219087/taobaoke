package com.starnet.cqj.taobaoke.presenter;

import com.starnet.cqj.taobaoke.model.BuyTip;
import com.starnet.cqj.taobaoke.model.Product;

import java.util.List;

/**
 * Created by Administrator on 2017/11/21.
 */

public interface IProductListPresenter extends BasePresenter{

    void getData(int page,String key,String typeName,String minFee,String maxFee,String cateId);

    void getTip();

    interface IView extends BasePresenter.IView{
        void setResult(List<Product> productList);

        void setTip(BuyTip tip);
    }
}
