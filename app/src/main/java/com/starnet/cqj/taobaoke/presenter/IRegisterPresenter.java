package com.starnet.cqj.taobaoke.presenter;

import android.app.Application;

import com.starnet.cqj.taobaoke.model.User;
import com.starnet.cqj.taobaoke.model.city.CityResult;

/**
 * Created by mini on 17/11/4.
 */

public interface IRegisterPresenter extends BasePresenter {

    void checkAccount(String mobile);

    void initCity(Application application);

    void sendSMS(String mobile);

    void register(String mobile, String pwd, String nickName,String code);

//    void verifyCode(String mobile,String code);

    interface IView extends BasePresenter.IView {

        void onInitCity(CityResult result);

        void onGetCode();

//        void onVerifySuccess();

        void onRegisterSuccess(User user);

    }
}
