package com.starnet.cqj.taobaoke.remote.i;

import com.starnet.cqj.taobaoke.model.IntegralProduct;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.ResultWrapper;

import io.reactivex.Observable;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by mini on 17/11/11.
 */

public interface IntegralService {

    @POST("/integral")
    Observable<JsonCommon<ResultWrapper<IntegralProduct>>> integral(@Header("Authorization")String header);
}
