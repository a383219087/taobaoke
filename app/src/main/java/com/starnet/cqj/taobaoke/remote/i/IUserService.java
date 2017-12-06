package com.starnet.cqj.taobaoke.remote.i;

import com.starnet.cqj.taobaoke.model.Address;
import com.starnet.cqj.taobaoke.model.CNCBKUser;
import com.starnet.cqj.taobaoke.model.ExchangeRecord;
import com.starnet.cqj.taobaoke.model.IntegralDetail;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.Medal;
import com.starnet.cqj.taobaoke.model.Message;
import com.starnet.cqj.taobaoke.model.Order;
import com.starnet.cqj.taobaoke.model.OrderShareScore;
import com.starnet.cqj.taobaoke.model.ResultWrapper;
import com.starnet.cqj.taobaoke.model.ShareContent;
import com.starnet.cqj.taobaoke.model.User;
import com.starnet.cqj.taobaoke.model.WithdrawalsRecord;
import com.starnet.cqj.taobaoke.model.city.CityBean;
import com.starnet.cqj.taobaoke.model.city.ProvinceBean;

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
    Observable<JsonCommon<Object>> register(@Query("mobile") String mobile, @Query("password") String password, @Query("password_confirm") String confirmPwd, @Query("nickname") String nickname, @Query("code") String code,@Query("resideprovince")String province,@Query("residecity")String city);

    @POST("/login")
    Observable<JsonCommon<User>> login(@Query("mobile") String mobile, @Query("password") String password, @Query("reg_id") String regId, @Query("is_wechat") String isWechat, @Query("openid") String openid);


    @POST("/resetPass")
    Observable<JsonCommon<Object>> resetPwd(@Query("mobile") String mobile, @Query("password") String password, @Query("password_confirm") String pwdAgain, @Query("code") String code);

    @POST("/person")
    Observable<JsonCommon<User>> person(@Header("Authorization") String header);

    @POST("/address")
    Observable<JsonCommon<ResultWrapper<Address>>> getAddress(@Header("Authorization") String header);

    @POST("/delAddr")
    Observable<JsonCommon<Object>> deleteAddress(@Header("Authorization") String header, @Query("id") int id);

    @POST("/saveAddress")
    Observable<JsonCommon<Object>> saveAddress(@Header("Authorization") String header,
                                                     @Query("id") int id,
                                                     @Query("area") String area,
                                                     @Query("address") String address,
                                                     @Query("is_default") int is_default,
                                                     @Query("phone") String phone,
                                                     @Query("name") String name
    );

    @POST("/integralOrder")
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
    Observable<JsonCommon<User>> bindUser(@Query("reg_id") String regId,@Query("mobile") String mobile, @Query("openid") String openid,
                                          @Query("nickname") String nickName,
                                          @Query("password") String password,
                                          @Query("password_confirm") String passwordConfirm,
                                          @Query("avatar") String avatar,
                                          @Query("unionid") String unionid,
                                          @Query("gender") String gender,
                                          @Query("code") String code,
                                          @Query("is_create") String isCreate,
                                          @Query("resideprovince")String province,
                                          @Query("residecity")String city);


    @POST("/bindPhone")
    Observable<JsonCommon<Object>> bindPhone(@Header("Authorization") String header, @Query("phone") String phone, @Query("code") String code);

    @POST("/isRegister")
    Observable<JsonCommon<Object>> isRegister(@Query("mobile") String phone);

    @POST("/medals")
    Observable<JsonCommon<List<Medal>>> getMedals(@Header("Authorization") String header, @Query("type") int type);

    @POST("/order")
    Observable<JsonCommon<ResultWrapper<Order>>> getOrder(@Header("Authorization") String header, @Query("q") String keyWord);

    @POST("/bindOrder")
    Observable<JsonCommon<Object>> bindOrder(@Header("Authorization") String header, @Query("order_id") String orderId);

    @POST("/djsCncbk")
    Observable<JsonCommon<ResultWrapper<IntegralDetail>>> djsCncbk(@Header("Authorization") String header, @Query("page") int page);


    @POST("/djsCncbk")
    Observable<JsonCommon<ResultWrapper<IntegralDetail>>> djsCncbk(@Header("Authorization") String header, @Query("page") int page, @Query("year") int year, @Query("month") int month);

    @POST("/djCncbk")
    Observable<JsonCommon<ResultWrapper<IntegralDetail>>> djCncbk(@Header("Authorization") String header, @Query("page") int page);

    @POST("/djCncbk")
    Observable<JsonCommon<ResultWrapper<IntegralDetail>>> djCncbk(@Header("Authorization") String header, @Query("page") int page, @Query("year") int year, @Query("month") int month);

    @POST("/bCncbk")
    Observable<JsonCommon<ResultWrapper<IntegralDetail>>> bCncbk(@Header("Authorization") String header, @Query("page") int page);

    @POST("/bCncbk")
    Observable<JsonCommon<ResultWrapper<IntegralDetail>>> bCncbk(@Header("Authorization") String header, @Query("page") int page, @Query("year") int year, @Query("month") int month);

    @POST("/sysmsg")
    Observable<JsonCommon<List<Message>>> sysmsg(@Header("Authorization") String header);

    @POST("/message")
    Observable<JsonCommon<Message>> message(@Header("Authorization") String header, @Query("id") String id);

    @POST("/delMsg")
    Observable<JsonCommon<Object>> delMsg(@Header("Authorization") String header, @Query("id") String id);


    @POST("/read")
    Observable<JsonCommon<Object>> msgRead(@Header("Authorization") String header);


    @POST("/modify")
    Observable<JsonCommon<Object>> modify(@Header("Authorization") String header, @Query("nickname") String nickname);


    @POST("/logout")
    Observable<JsonCommon<Object>> logout(@Header("Authorization") String header);


    @POST("/share")
    Observable<JsonCommon<ShareContent>> share(@Header("Authorization") String header);

    @POST("/refreshToken")
    Observable<JsonCommon<User>> refreshToken(@Query("reg_id") String regId);


    @POST("/modifyPass")
    Observable<JsonCommon<Object>> modifyPass(@Header("Authorization") String header,@Query("old_pass") String oldPass,
                                               @Query("new_pass") String newPass,
                                               @Query("confirm_pass") String confirmPass);


    @POST("/cashLog")
    Observable<JsonCommon<ResultWrapper<WithdrawalsRecord>>> cashLog(@Header("Authorization") String header, @Query("page") int page);

    @POST("/province")
    Observable<JsonCommon<ResultWrapper<ProvinceBean>>> province();

    @POST("/region")
    Observable<JsonCommon<ResultWrapper<CityBean>>> region(@Query("parent_id")String parentId);


    @POST("/shareRecord")
    Observable<JsonCommon<OrderShareScore>> shareRecord(@Header("Authorization") String header, @Query("order_id")String orderId);


    @POST("/promote")
    Observable<JsonCommon<Medal>> promote(@Header("Authorization") String header);
}
