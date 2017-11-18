package com.starnet.cqj.taobaoke.remote.i;

import com.starnet.cqj.taobaoke.model.Article;
import com.starnet.cqj.taobaoke.model.HelpDetail;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.ResultWrapper;
import com.starnet.cqj.taobaoke.model.ResultWithBanner;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by mini on 17/11/4.
 */

public interface ICommonService {

    @POST("/sendSMS")
    Observable<JsonCommon<List<String>>> sendSMS(@Query("mobile") String mobile);

    @POST("/verifySMS")
    Observable<JsonCommon<String>> verifySMS(@Query("mobile") String mobile,@Query("code") String code);

    @POST("help/pages")
    Observable<JsonCommon<ResultWrapper<Article>>> getHelpListPage(@Query("page") int page, @Query("type") int type);

    @POST("/article")
    Observable<JsonCommon<ResultWithBanner<Article>>> getHelpList(@Query("type") int type);

    @POST("/article/{id}")
    Observable<JsonCommon<HelpDetail>> getHelpContent(@Query("id") String id);


}
