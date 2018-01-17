package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.remote.Constant;
import com.starnet.cqj.taobaoke.view.BaseApplication;

import java.util.HashMap;


public class ServiceCustomerActivity extends WebViewActivity {

    public static final String URL = Constant.BASE_URL + "kefu";

    @Override
    protected void init() {
        setTitleName(R.string.customer_service_title);
        initWebView();
        HashMap<String, String> header = new HashMap<>();
        header.put("Authorization", ((BaseApplication) getApplication()).getToken());
        mWebView.loadUrl(URL, header);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, ServiceCustomerActivity.class);
        context.startActivity(starter);
    }
}
