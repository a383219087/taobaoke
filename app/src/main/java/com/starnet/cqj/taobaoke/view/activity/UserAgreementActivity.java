package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.starnet.cqj.taobaoke.R;

public class UserAgreementActivity extends BaseActivity {


    @Override
    protected void init() {
        setTitleName(R.string.user_agreement);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_user_agreement;
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, UserAgreementActivity.class);
        context.startActivity(starter);
    }
}
