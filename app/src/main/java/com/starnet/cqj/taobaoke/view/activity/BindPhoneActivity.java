package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;

import com.starnet.cqj.taobaoke.R;

public class BindPhoneActivity extends BaseActivity {

    @Override
    protected void init() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_bind_phone;
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, BindPhoneActivity.class);
        context.startActivity(starter);
    }
}
