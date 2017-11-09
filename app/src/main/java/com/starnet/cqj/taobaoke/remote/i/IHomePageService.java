package com.starnet.cqj.taobaoke.remote.i;

import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.MainMenu;
import com.starnet.cqj.taobaoke.model.ProductResult;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by mini on 17/11/4.
 */

public interface IHomePageService {

    @POST("/category")
    Observable<JsonCommon<List<MainMenu>>> getCategory();

    @POST("/video")
    Observable<JsonCommon<ProductResult>> getLookBuy(@Query("page") int page);

    @POST("/pop")
    Observable<JsonCommon<ProductResult>> getRecommend(@Query("page") int page);
}
