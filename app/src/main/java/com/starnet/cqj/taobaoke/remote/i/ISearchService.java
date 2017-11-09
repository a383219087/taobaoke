package com.starnet.cqj.taobaoke.remote.i;

import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.User;

import io.reactivex.Observable;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/11/09.
 */

public interface ISearchService {

    @POST("/search")
    Observable<JsonCommon<User>> person(@Header("Authorization") String header);
}
