package com.starnet.cqj.taobaoke.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Retrofit;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.email)
    EditText mEdtAccount;
    @BindView(R.id.password)
    EditText mEdtPwd;
    @BindView(R.id.email_sign_in_button)
    Button mBtnSignIn;

    @Override
    protected void init() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @OnClick(R.id.login_forget_pwd)
    void forgetPwd(){

    }

    @OnClick(R.id.email_sign_in_button)
    void login(){

    }
}
