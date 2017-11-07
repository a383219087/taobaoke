package com.starnet.cqj.taobaoke.remote.i;

import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.User;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/10/31.
 */

public interface ILoginService {

    @GET("/register")
    Observable<JsonCommon<String>> register(@Query("mobile")String mobile, @Query("password")String password, @Query("nickname")String nickname);

    @GET("/login")
    Observable<JsonCommon<User>> login(@Query("mobile") String mobile, @Query("password")String password, @Query("is_wechat")String isWechat);
}
