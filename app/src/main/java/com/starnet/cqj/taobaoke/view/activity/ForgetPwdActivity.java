package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;

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

    public static void start(Context context) {
        Intent starter = new Intent(context, ForgetPwdActivity.class);
        context.startActivity(starter);
    }
}
