package com.starnet.cqj.taobaoke.view.activity;


import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.starnet.cqj.taobaoke.model.User;
import com.starnet.cqj.taobaoke.model.WechatUser;
import com.starnet.cqj.taobaoke.presenter.IRegisterPresenter;
import com.starnet.cqj.taobaoke.presenter.impl.BindNewPresenterImpl;
import com.starnet.cqj.taobaoke.view.BaseApplication;

import cn.jpush.android.api.JPushInterface;

public class BindNewActivity extends RegisterActivity {

    public static final String KEY_WECHAT_USER = "wechat_user";
    private WechatUser mUser;

    @Override
    protected void init() {
        super.init();
        mLlNickName.setVisibility(View.GONE);
        mUser = (WechatUser) getIntent().getSerializableExtra(KEY_WECHAT_USER);
    }

    @Override
    protected IRegisterPresenter getPresenter() {
        String regId = JPushInterface.getRegistrationID(this);
        return new BindNewPresenterImpl(this, mUser, regId);
    }

    @Override
    public void onRegisterSuccess(User user) {
        if (user != null) {
            ((BaseApplication) getApplication()).token = user.getToken();
            MainActivity.startTop(this);
        }
        super.onRegisterSuccess(user);
    }

    @Override
    protected boolean checkNickName(String nickName) {
        return true;
    }

    public static void start(Context context, WechatUser user) {
        Intent starter = new Intent(context, BindNewActivity.class);
        starter.putExtra(KEY_WECHAT_USER, user);
        context.startActivity(starter);
    }
}
