package com.starnet.cqj.taobaoke.remote.i;

import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.ResultWrapper;
import com.starnet.cqj.taobaoke.model.SignResult;

import io.reactivex.Observable;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by mini on 17/11/18.
 */

public interface IActionService {


    @POST("/signIn")
    Observable<JsonCommon<SignResult>> signIn(@Header("Authorization") String header);

    @POST("/signList")
    Observable<JsonCommon<ResultWrapper<String>>> signList(@Header("Authorization") String header);


}
