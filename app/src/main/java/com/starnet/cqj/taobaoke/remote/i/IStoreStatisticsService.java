package com.starnet.cqj.taobaoke.remote.i;

import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.ResultWrapper;
import com.starnet.cqj.taobaoke.model.Statistics;

import io.reactivex.Observable;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 数据统计接口
 * Created by JohnChen on 2018/01/09.
 */

public interface IStoreStatisticsService {

    @POST("/shop/statistics")
    Observable<JsonCommon<ResultWrapper<Statistics>>> get(@Header("Authorization") String header, @Query("type") int type, @Query("page") int page);


    @POST("/shop/statistics")
    Observable<JsonCommon<Statistics>> get(@Header("Authorization") String header, @Query("type") int type,
                                                 @Query("start")String startDate,@Query("end")String endDate);

}
