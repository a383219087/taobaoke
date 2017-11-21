package com.starnet.cqj.taobaoke.remote.i;

import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.Product;
import com.starnet.cqj.taobaoke.model.ResultWrapper;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ISearchService {

    /**
     *
     * @param key
     * @param page
     * @param stype
     * @param type
     * stype=0&type=3 智能排序
        stype=0&type=2 销量最高排序
        stype=0&type=5 天猫优先
        stype=5&type=0 只看券
     * @return
     */
    @POST("/superSearch")
    Observable<JsonCommon<ResultWrapper<Product>>> superSearch(@Query("keyword") String key, @Query("page") int page, @Query("stype") int stype, @Query("type") int type);

    /**
     *
     * @param key 搜索关键字
     * @param typename 默认：moren：综合搜索，ishot：销量排序，ishit：优惠排序，isfee：价格排序
     * @param minfee 价格区间最小值
     * @param maxfee 价格区间最大值
     * @return
     */
    @POST("/site")
    Observable<JsonCommon<ResultWrapper<Product>>> site(@Query("keywords") String key, @Query("typename") String typename, @Query("minfee") String minfee, @Query("maxfee") String maxfee,@Query("cate_id")String cateId);


    @POST("/jhs")
    Observable<JsonCommon<ResultWrapper<Product>>> jhs(@Query("page") int page, @Query("typename") String typename, @Query("minfee") String minfee, @Query("maxfee") String maxfee,@Query("cate_id") String cateId);

    @POST("/tqg")
    Observable<JsonCommon<ResultWrapper<Product>>> tqg(@Query("page") int page, @Query("typename") String typename, @Query("minfee") String minfee, @Query("maxfee") String maxfee,@Query("cate_id") String cateId);

    @POST("/today")
    Observable<JsonCommon<ResultWrapper<Product>>> today(@Query("page") int page, @Query("typename") String typename, @Query("minfee") String minfee, @Query("maxfee") String maxfee,@Query("cate_id") String cateId);

    @POST("/ppq")
    Observable<JsonCommon<ResultWrapper<Product>>> ppq(@Query("page") int page, @Query("typename") String typename, @Query("minfee") String minfee, @Query("maxfee") String maxfee,@Query("cate_id") String cateId);

}
