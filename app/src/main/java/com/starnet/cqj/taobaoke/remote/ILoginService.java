package com.starnet.cqj.taobaoke.remote;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2017/10/31.
 */

public interface ILoginService {

    @GET("versions/")
    Observable<List<String>> forgetPwd();

    @GET("versions/{id}")
    Observable<String> login(@Path("id")int id);
}
