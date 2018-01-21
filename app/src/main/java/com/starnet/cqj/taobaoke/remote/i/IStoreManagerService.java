package com.starnet.cqj.taobaoke.remote.i;

import com.starnet.cqj.taobaoke.model.AgencyFee;
import com.starnet.cqj.taobaoke.model.AlipayRequest;
import com.starnet.cqj.taobaoke.model.ApplyStatus;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.StoreIndex;
import com.starnet.cqj.taobaoke.model.StoreManagerApplyDetail;

import io.reactivex.Observable;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/01/08.
 */

public interface IStoreManagerService {

    @POST("/shop/apply")
    Observable<JsonCommon<Object>> apply(@Header("Authorization") String header, @Query("shop_type") int type,
                                         @Query("contact") String contact,@Query("phone") String phone,@Query("remark") String remark);


    @POST("/shop/orderApply")
    Observable<JsonCommon<AlipayRequest>> orderApply(@Header("Authorization") String header, @Query("shop_type") int type,
                                                     @Query("contact") String contact, @Query("phone") String phone, @Query("remark") String remark, @Query("money") String money);

    @POST("/shop/get")
    Observable<JsonCommon<StoreManagerApplyDetail>> get(@Header("Authorization") String header);

    @POST("/shop/status")
    Observable<JsonCommon<ApplyStatus>> status(@Header("Authorization") String header);


    @POST("/shop/index")
    Observable<JsonCommon<StoreIndex>> index(@Header("Authorization") String header);


    @POST("/withdraw")
    Observable<JsonCommon<Object>> withdraw(@Header("Authorization") String header,@Query("name") String name,@Query("username") String username,
                                                @Query("money") String money,@Query("type") int type,@Query("bank") String bank);


    @POST("/agency_fee")
    Observable<JsonCommon<AgencyFee>> agencyFee(@Header("Authorization") String header);
}
