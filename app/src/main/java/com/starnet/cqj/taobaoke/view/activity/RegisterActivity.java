package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;

import com.starnet.cqj.taobaoke.R;

import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {


    @Override
    protected void init() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_register;
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, RegisterActivity.class);
        context.startActivity(starter);
    }

    @OnClick(R.id.user_agreement)
    void toUserAgreement(){
        UserAgreementActivity.start(this);
    }
}
