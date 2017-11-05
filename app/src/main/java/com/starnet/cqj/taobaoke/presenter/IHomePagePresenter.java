package com.starnet.cqj.taobaoke.presenter;

import com.starnet.cqj.taobaoke.model.MainMenu;

import java.util.List;

/**
 * Created by mini on 17/11/4.
 */

public interface IHomePagePresenter extends BasePresenter{

    void getCategory();

    interface IView extends BasePresenter.IView{

        void setCategoryList(List<MainMenu> mainMenuList);

    }
}
