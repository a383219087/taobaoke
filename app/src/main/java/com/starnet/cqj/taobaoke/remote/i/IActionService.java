package com.starnet.cqj.taobaoke.remote.i;

import com.starnet.cqj.taobaoke.model.BuyAction;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.OtherAction;
import com.starnet.cqj.taobaoke.model.ResultWrapper;
import com.starnet.cqj.taobaoke.model.SignResult;

import java.util.List;

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

    @POST("/active")
    Observable<JsonCommon<BuyAction>> active();

    @POST("/activeItem")
    Observable<JsonCommon<List<OtherAction>>> activeItem(@Header("Authorization") String header);

}
