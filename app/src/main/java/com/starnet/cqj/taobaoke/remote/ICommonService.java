package com.starnet.cqj.taobaoke.remote;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by mini on 17/11/4.
 */

public interface ICommonService {

    @POST("/sendSMS")
    Observable<String> sendSMS(@Query("mobile") String mobile);
}
