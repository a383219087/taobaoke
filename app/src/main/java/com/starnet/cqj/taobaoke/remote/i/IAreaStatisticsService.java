package com.starnet.cqj.taobaoke.remote.i;

import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.Statistics;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 数据统计接口
 * Created by JohnChen on 2018/01/09.
 */

public interface IAreaStatisticsService {

    @POST("/area/statistics")
    Observable<JsonCommon<List<Statistics>>> get(@Header("Authorization") String header, @Query("page") int page, @Query("type") int type);


    @POST("/area/statistics")
    Observable<JsonCommon<Statistics>> get(@Header("Authorization") String header, @Query("type") int type,
                                           @Query("start") String startDate, @Query("end") String endDate);

}
