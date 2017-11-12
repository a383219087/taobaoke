package com.starnet.cqj.taobaoke.remote.i;

import com.starnet.cqj.taobaoke.model.Address;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.ProductResult;
import com.starnet.cqj.taobaoke.model.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/10/31.
 */

public interface IUserService {

    @POST("/register")
    Observable<JsonCommon<List<String>>> register(@Query("mobile") String mobile, @Query("password") String password, @Query("password_confirm") String confirmPwd, @Query("nickname") String nickname, @Query("code") String code);

    @POST("/login")
    Observable<JsonCommon<User>> login(@Query("mobile") String mobile, @Query("password") String password, @Query("is_wechat") String isWechat);


    @POST("/resetPass")
    Observable<JsonCommon<List<String>>> resetPwd(@Query("mobile") String mobile, @Query("password") String password, @Query("password_confirm") String pwdAgain, @Query("code") String code);

    @POST("/person")
    Observable<JsonCommon<User>> person(@Header("Authorization") String header);

    @POST("/address")
    Observable<JsonCommon<ProductResult<Address>>> getAddress(@Header("Authorization") String header);

    @POST("/delAddr")
    Observable<JsonCommon<List<String>>> deleteAddress(@Header("Authorization") String header, @Query("id") int id);

    @POST("/saveAddress")
    Observable<JsonCommon<List<String>>> saveAddress(@Header("Authorization") String header,
                                               @Query("id") int id,
                                               @Query("area") String area,
                                               @Query("address") String address,
                                               @Query("is_default") int is_default,
                                               @Query("phone") String phone,
                                               @Query("name") String name
                                               );
}
