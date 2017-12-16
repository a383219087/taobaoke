package com.starnet.cqj.taobaoke.presenter;

import com.starnet.cqj.taobaoke.model.Article;
import com.starnet.cqj.taobaoke.model.Banner;
import com.starnet.cqj.taobaoke.model.HotItem;

import java.util.List;

/**
 * Created by mini on 17/11/18.
 */

public interface IHotPresenter extends BasePresenter {

    void getItem();

    void getList(String itemId);

    void getMoreList(int page, String itemId);

    interface IView extends BasePresenter.IView {
        void onGetItem(List<HotItem> hotItemList);

        void setBanner(List<Banner> bannerList);

        void setArticleList(List<Article> articleList);

    }
}
