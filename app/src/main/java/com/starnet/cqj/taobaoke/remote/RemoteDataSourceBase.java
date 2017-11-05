package com.starnet.cqj.taobaoke.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Administrator on 2017/10/31.
 */

public enum RemoteDataSourceBase {

    INSTANCE;

    private Retrofit mRetrofit;

    RemoteDataSourceBase() {
        Gson gson = new GsonBuilder()
                //配置你的Gson
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public ILoginService getLoginService() {
        return mRetrofit.create(ILoginService.class);
    }

    public IHomePageService getHomePageService() {
        return mRetrofit.create(IHomePageService.class);
    }

    public ICommonService getCommonService() {
        return mRetrofit.create(ICommonService.class);
    }

}
