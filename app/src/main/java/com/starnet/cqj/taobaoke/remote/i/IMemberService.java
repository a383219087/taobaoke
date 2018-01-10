package com.starnet.cqj.taobaoke.remote.i;

import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.Member;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/01/09.
 */

public interface IMemberService {

    @POST("/shop/member/{uid}")
    Observable<JsonCommon<List<Member>>> get(@Header("Authorization") String header, @Query("uid")String uid);

}
