package com.starnet.cqj.taobaoke.view;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.widget.Toast;

import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.User;
import com.starnet.cqj.taobaoke.remote.Constant;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;
import com.starnet.cqj.taobaoke.utils.DateUtils;
import com.starnet.cqj.taobaoke.view.activity.LoginActivity;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BaseApplication extends Application {


    private String token;

    @Override
    public void onCreate() {
        super.onCreate();
        PlatformConfig.setWeixin("wxe30a5dd92858b191", "092d65fdb02ab67f86f764d5b8e6b21b");
        PlatformConfig.setQQZone("1106545614", "F0ZZ3xJYvvNKrxY8");
        UMShareAPI.get(this);
        JPushInterface.init(this);
        JPushInterface.setDebugMode(true);
    }

    public String getToken(){
        return getToken(true);
    }

    public String getToken(boolean isShowLogin) {
        if (TextUtils.isEmpty(token)) {
            SharedPreferences sharedPreferences = getSharedPreferences(Constant.COMMON_PREFERENCE_NAME, Context.MODE_PRIVATE);
            token = sharedPreferences.getString(Constant.KEY_TOKEN, "");
            if (TextUtils.isEmpty(token)) {
                if(isShowLogin) {
                    LoginActivity.start(getApplicationContext());
                }
                return null;
            } else {
                String tokenDate = sharedPreferences.getString(Constant.KEY_TOKEN_DATE, "");
                if (DateUtils.is10DayLater(tokenDate)) {
                    String registrationID = JPushInterface.getRegistrationID(getApplicationContext());
                    RemoteDataSourceBase.INSTANCE.getUserService()
                            .refreshToken(registrationID)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(new Observer<JsonCommon<User>>() {
                                @Override
                                public void onSubscribe(Disposable disposable) {

                                }

                                @Override
                                public void onNext(JsonCommon<User> userJsonCommon) {
                                    if ("200".equals(userJsonCommon.getCode())) {
                                        setToken(userJsonCommon.getData().getToken());
                                    } else {
                                        Toast.makeText(BaseApplication.this, userJsonCommon.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onError(Throwable throwable) {
                                    throwable.printStackTrace();
                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }
            }
        }
        return token;
    }

    public void setToken(String pToken) {
        this.token = TextUtils.isEmpty(pToken) ? "" : Constant.HEADER_PREFIX + pToken;
        SharedPreferences.Editor editor = getSharedPreferences(Constant.COMMON_PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(Constant.KEY_TOKEN, token);
        editor.putString(Constant.KEY_TOKEN_DATE, DateUtils.getToday());
        editor.apply();
    }
}
