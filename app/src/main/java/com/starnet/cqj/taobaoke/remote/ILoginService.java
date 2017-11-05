package com.starnet.cqj.taobaoke.remote;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/10/31.
 */

public interface ILoginService {

    @GET("register/")
    Observable<List<String>> register(@Query("mobile")String mobile,@Query("password")String password,@Query("nickname")String nickname);

    @GET("login/")
    Observable<String> login(@Query("mobile") String mobile, @Query("password")String password, @Query("is_wechat")String isWechat);
}
