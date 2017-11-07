package com.starnet.cqj.taobaoke.presenter;

import com.starnet.cqj.taobaoke.model.User;

/**
 * Created by Administrator on 2017/11/07.
 */

public interface ILoginPresenter extends BasePresenter {

    void login(String mobile,String pwd);

    interface IView extends BasePresenter.IView{

        void onLoginSuccess(User user);
    }
}
