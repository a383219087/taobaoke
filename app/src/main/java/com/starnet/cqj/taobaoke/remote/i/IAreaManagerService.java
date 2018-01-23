package com.starnet.cqj.taobaoke.remote.i;

import com.starnet.cqj.taobaoke.model.AlipayRequest;
import com.starnet.cqj.taobaoke.model.ApplyStatus;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.StoreIndex;

import io.reactivex.Observable;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/01/08.
 */

public interface IAreaManagerService {

    @POST("/area/apply")
    Observable<JsonCommon<Object>> apply(@Header("Authorization") String header, @Query("area_type") int type,
                                         @Query("contact") String contact, @Query("phone") String phone, @Query("remark") String remark,
                                         @Query("province") String province, @Query("city") String city, @Query("area") String area);


    @POST("/area/orderApply")
    Observable<JsonCommon<AlipayRequest>> orderApply(@Header("Authorization") String header, @Query("area_type") int type,
                                                     @Query("contact") String contact, @Query("phone") String phone, @Query("remark") String remark, @Query("money") String money,
                                                     @Query("province") String province, @Query("city") String city, @Query("area") String area);

    @POST("/area/status")
    Observable<JsonCommon<ApplyStatus>> status(@Header("Authorization") String header);


    @POST("/area/index")
    Observable<JsonCommon<StoreIndex>> index(@Header("Authorization") String header);
}
