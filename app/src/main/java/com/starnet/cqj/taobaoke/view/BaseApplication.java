package com.starnet.cqj.taobaoke.view;

import android.app.Application;

import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

/**
 * Created by Administrator on 2017/11/07.
 */

public class BaseApplication extends Application {


    public String token;

    @Override
    public void onCreate() {
        super.onCreate();
        PlatformConfig.setWeixin("wxe30a5dd92858b191", "092d65fdb02ab67f86f764d5b8e6b21b");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        UMShareAPI.get(this);
    }

}
