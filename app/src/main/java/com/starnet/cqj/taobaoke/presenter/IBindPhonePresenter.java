package com.starnet.cqj.taobaoke.presenter;

public interface IBindPhonePresenter extends BasePresenter {

    void checkAccount(String mobile);

    void getCode(String mobile);

    void bind(String token, String phone, String code);

    interface IView extends BasePresenter.IView {
        void onGetCode();

        void onBind();
    }
}
