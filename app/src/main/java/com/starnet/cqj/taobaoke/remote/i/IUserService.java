package com.starnet.cqj.taobaoke.remote.i;

import com.starnet.cqj.taobaoke.model.Address;
import com.starnet.cqj.taobaoke.model.CNCBKUser;
import com.starnet.cqj.taobaoke.model.ExchangeRecord;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.ResultWrapper;
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
    Observable<JsonCommon<User>> login(@Query("mobile") String mobile, @Query("password") String password, @Query("is_wechat") String isWechat,@Query("openid") String openid);


    @POST("/resetPass")
    Observable<JsonCommon<List<String>>> resetPwd(@Query("mobile") String mobile, @Query("password") String password, @Query("password_confirm") String pwdAgain, @Query("code") String code);

    @POST("/person")
    Observable<JsonCommon<User>> person(@Header("Authorization") String header);

    @POST("/address")
    Observable<JsonCommon<ResultWrapper<Address>>> getAddress(@Header("Authorization") String header);

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

    @POST("/integralOder")
    Observable<JsonCommon<Object>> integralOrder(@Header("Authorization") String header,
                                                 @Query("score") String score,
                                                 @Query("address") String address,
                                                 @Query("phone") String phone,
                                                 @Query("integral_id") String integralId,
                                                 @Query("name") String name
    );

    @POST("/integralRecord")
    Observable<JsonCommon<List<ExchangeRecord>>> integralRecord(@Header("Authorization") String header, @Query("page") int page);

    @POST("/cncbk")
    Observable<JsonCommon<CNCBKUser>> cncbk(@Header("Authorization") String header);

    @POST("/bindCNCBK")
    Observable<JsonCommon<Object>> bindCNCBK(@Header("Authorization") String header, @Query("username") String userName, @Query("phone") String phone);

    @POST("/cashCNCBK")
    Observable<JsonCommon<Object>> cashCNCBK(@Header("Authorization") String header, @Query("score") String score);

    @POST("/bindUser")
    Observable<JsonCommon<User>> bindUser(@Query("mobile") String mobile, @Query("openid") String openid,
                                            @Query("nickname") String nickName,
                                            @Query("password") String password,
                                            @Query("password_confirm") String passwordConfirm,
                                            @Query("avatar") String avatar,
                                            @Query("unionid") String unionid,
                                            @Query("gender") String gender,
                                            @Query("code") String code,
                                            @Query("is_create") String isCreate);

}
