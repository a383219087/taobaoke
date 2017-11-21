package com.starnet.cqj.taobaoke.presenter;

import com.starnet.cqj.taobaoke.model.CNCBKUser;

/**
 * Created by mini on 17/11/15.
 */

public interface IWithdrawalsPresenter extends BasePresenter {

    void getScore(String header);

    void getBindUser(String header);

    void bindUser(String header, String userName, String phone);

    void cashCNCBK(String header,String score);

    interface IView extends BasePresenter.IView{

        void setScore(String score);

        void onGetBindUser(CNCBKUser user);

        void onCash();

    }
}
