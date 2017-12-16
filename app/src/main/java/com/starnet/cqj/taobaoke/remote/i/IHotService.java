package com.starnet.cqj.taobaoke.remote.i;

import com.starnet.cqj.taobaoke.model.Article;
import com.starnet.cqj.taobaoke.model.HotItem;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.ResultWithBanner;
import com.starnet.cqj.taobaoke.model.ResultWrapper;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by mini on 17/11/18.
 */

public interface IHotService {


    @POST("/hot/item")
    Observable<JsonCommon<ResultWrapper<HotItem>>> getItem();

    @POST("/hot/article")
    Observable<JsonCommon<ResultWithBanner<Article>>> getHotArticle(@Query("type") String type);

    @POST("/hot/pages")
    Observable<JsonCommon<ResultWrapper<Article>>> getMoreArticle(@Query("page") int page, @Query("type") String type);
}
