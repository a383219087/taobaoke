package com.starnet.cqj.taobaoke.view;

import android.app.Application;

import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2017/11/07.
 */

public class BaseApplication extends Application {


    public String token;

    @Override
    public void onCreate() {
        super.onCreate();
        PlatformConfig.setWeixin("wxe30a5dd92858b191", "092d65fdb02ab67f86f764d5b8e6b21b");
        PlatformConfig.setQQZone("1106545614", "F0ZZ3xJYvvNKrxY8");
        UMShareAPI.get(this);
        JPushInterface.init(this);
        JPushInterface.setDebugMode(true);
    }

}
