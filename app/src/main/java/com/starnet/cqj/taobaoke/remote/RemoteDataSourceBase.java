package com.starnet.cqj.taobaoke.remote;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/10/31.
 */

public enum  RemoteDataSourceBase {

    INSTANCE;

    private Retrofit mRetrofit;

    RemoteDataSourceBase() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public ILoginService getLoginService(){
        return mRetrofit.create(ILoginService.class);
    }

}
