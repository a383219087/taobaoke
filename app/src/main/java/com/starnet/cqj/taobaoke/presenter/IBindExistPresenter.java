package com.starnet.cqj.taobaoke.presenter;

import com.starnet.cqj.taobaoke.model.User;
import com.starnet.cqj.taobaoke.model.WechatUser;

/**
 * Created by Administrator on 2017/11/17.
 */

public interface IBindExistPresenter extends BasePresenter {

    void checkAccount(String mobile);

    void getCode(String mobile);

    void bind(WechatUser user,String regId);

    interface IView extends BasePresenter.IView{
        void onGetCode();

        void onBind(User user);
    }
}
