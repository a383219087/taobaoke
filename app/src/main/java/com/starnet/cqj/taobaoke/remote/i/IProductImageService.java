package com.starnet.cqj.taobaoke.remote.i;

import com.starnet.cqj.taobaoke.model.AliItemImage;
import com.starnet.cqj.taobaoke.model.JsonCommon;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/12/07.
 */

public interface IProductImageService {

    @GET("/cache/mtop.wdetail.getItemDescx/4.1")
    Observable<JsonCommon<AliItemImage>> getItemImage(@Query("data") String data, @Query("_") String time);
}
