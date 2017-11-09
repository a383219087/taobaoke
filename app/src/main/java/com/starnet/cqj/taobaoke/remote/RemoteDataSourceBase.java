package com.starnet.cqj.taobaoke.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.starnet.cqj.taobaoke.remote.i.ICommonService;
import com.starnet.cqj.taobaoke.remote.i.IHomePageService;
import com.starnet.cqj.taobaoke.remote.i.ISearchService;
import com.starnet.cqj.taobaoke.remote.i.IUserService;

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

    public IUserService getUserService() {
        return mRetrofit.create(IUserService.class);
    }

    public IHomePageService getHomePageService() {
        return mRetrofit.create(IHomePageService.class);
    }

    public ICommonService getCommonService() {
        return mRetrofit.create(ICommonService.class);
    }

    public ISearchService getSearchService() {
        return mRetrofit.create(ISearchService.class);
    }

}
