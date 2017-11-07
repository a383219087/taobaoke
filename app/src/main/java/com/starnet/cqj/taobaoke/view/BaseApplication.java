package com.starnet.cqj.taobaoke.view;

import android.app.Application;

import com.starnet.cqj.taobaoke.model.User;

/**
 * Created by Administrator on 2017/11/07.
 */

public class BaseApplication extends Application {

    public User user;

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
