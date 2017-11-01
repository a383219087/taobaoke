package com.starnet.cqj.taobaoke.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Retrofit;

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

    }

    @OnClick(R.id.regist)
    void register(){

    }

    @OnClick(R.id.email_sign_in_button)
    void login(){

    }

    @OnClick(R.id.wechat_login)
    void weChatLogin(){

    }
}
