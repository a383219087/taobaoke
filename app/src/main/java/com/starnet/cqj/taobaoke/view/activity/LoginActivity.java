package com.starnet.cqj.taobaoke.view.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.starnet.cqj.taobaoke.R;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_username)
    EditText mEdtAccount;
    @BindView(R.id.login_pwd)
    EditText mEdtPwd;
    @BindView(R.id.login_btn)
    Button mBtnSignIn;

    @Override
    protected void init() {
        mIvTitleBack.setVisibility(View.GONE);
        setTitleName(R.string.app_name);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @OnClick(R.id.forgetpwd)
    void forgetPwd(){
        ForgetPwdActivity.start(this);
    }

    @OnClick(R.id.regist)
    void register(){
        RegisterActivity.start(this);
    }

    @OnClick(R.id.login_btn)
    void login(){

    }

    @OnClick(R.id.wechat_login)
    void weChatLogin(){

    }
}
