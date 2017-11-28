package com.starnet.cqj.taobaoke.presenter;

import com.starnet.cqj.taobaoke.model.User;

/**
 * Created by mini on 17/11/4.
 */

public interface IRegisterPresenter extends BasePresenter {

    void checkAccount(String mobile);

    void sendSMS(String mobile);

    void register(String mobile, String pwd, String nickName,String code);

//    void verifyCode(String mobile,String code);

    interface IView extends BasePresenter.IView {

        void onGetCode();

//        void onVerifySuccess();

        void onRegisterSuccess(User user);

    }
}
