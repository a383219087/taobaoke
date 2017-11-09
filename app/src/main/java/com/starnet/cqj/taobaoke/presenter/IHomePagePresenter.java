package com.starnet.cqj.taobaoke.presenter;

import com.starnet.cqj.taobaoke.model.MainMenu;
import com.starnet.cqj.taobaoke.model.Product;

import java.util.List;

/**
 * Created by mini on 17/11/4.
 */

public interface IHomePagePresenter extends BasePresenter{

    void getCategory();

    void getLookBuy();

    void getRecommend();

    interface IView extends BasePresenter.IView{

        void setCategoryList(List<MainMenu> mainMenuList);

        void setLookBuy(List<Product> productList);

        void setRecommend(List<Product> productList);

    }
}
