package com.starnet.cqj.taobaoke.presenter;

/**
 * Created by mini on 17/11/4.
 */

public interface IForgetPwdPresenter extends BasePresenter {

    void sendSMS(String mobile);

    void reset(String mobile, String pwd, String pwdAgain, String code);


    interface IView extends BasePresenter.IView {

        void onGetCode();

        void onResetSuccess();

    }
}
