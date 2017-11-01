package com.starnet.cqj.taobaoke.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.starnet.cqj.taobaoke.R;

public class ForgetPwdActivity extends BaseActivity {


    @Override
    protected void init() {
        setTitleName(R.string.forget_pwd);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_forgetpwd;
    }
}
