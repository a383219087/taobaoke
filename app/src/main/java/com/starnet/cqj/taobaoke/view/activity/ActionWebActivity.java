package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.view.BaseApplication;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/07.
 */

public class ActionWebActivity extends WebViewActivity {


    public static final String KEY_ACTION_URL = "action_url";
    public static final String KEY_TITLE = "title";

    @BindView(R.id.title_rightbutton)
    Button mBtnRecord;

    @Override
    protected void init() {
        setTitleName(R.string.action_detail_title);
//        String title = getIntent().getStringExtra(KEY_TITLE);
//        setTitleName(title);
//        mBtnRecord.setVisibility(View.VISIBLE);
        mBtnRecord.setText(R.string.award_record_text);
        initWebView();
        String url = getIntent().getStringExtra(KEY_ACTION_URL);
        HashMap<String,String> header = new HashMap<>();
        header.put("Authorization", ((BaseApplication) getApplication()).getToken());
        mWebView.loadUrl(url,header);
    }

    @OnClick(R.id.title_rightbutton)
    void OnClick(View view){

    }

    public static void start(Context context, String url) {
        Intent starter = new Intent(context, ActionWebActivity.class);
        starter.putExtra(KEY_ACTION_URL,url);
        context.startActivity(starter);
    }
}
