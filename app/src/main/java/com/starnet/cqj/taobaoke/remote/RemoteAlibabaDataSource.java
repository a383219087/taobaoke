package com.starnet.cqj.taobaoke.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.starnet.cqj.taobaoke.remote.i.IProductImageService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public enum RemoteAlibabaDataSource {

    INSTANCE;

    private Retrofit mRetrofit;
    private IProductImageService mProductImageService;

    RemoteAlibabaDataSource() {
        Gson gson = new GsonBuilder()
                //配置你的Gson
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();
        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://hws.m.taobao.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mProductImageService = mRetrofit.create(IProductImageService.class);
    }

    public IProductImageService getProductImageService() {
        return mProductImageService;
    }
}
