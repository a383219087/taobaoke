package com.starnet.cqj.taobaoke.remote.i;

import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.Member;
import com.starnet.cqj.taobaoke.model.MemberDetailItem;
import com.starnet.cqj.taobaoke.model.ResultWrapper;

import io.reactivex.Observable;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/01/09.
 */

public interface IMemberService {

    @POST("/shop/member")
    Observable<JsonCommon<ResultWrapper<Member>>> get(@Header("Authorization") String header, @Query("uid") String uid, @Query("keyword") String keyword);

    @POST("/shop/detail")
    Observable<JsonCommon<Member>> detail(@Header("Authorization") String header, @Query("uid") String uid);


    @POST("/shop/record")
    Observable<JsonCommon<ResultWrapper<MemberDetailItem>>> record(@Header("Authorization") String header, @Query("uid") String uid);

}
