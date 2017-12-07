package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.starnet.cqj.taobaoke.R;

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
        String title = getIntent().getStringExtra(KEY_TITLE);
        setTitleName(title);
        mBtnRecord.setVisibility(View.VISIBLE);
        mBtnRecord.setText(R.string.award_record_text);
        initWebView();
        String url = getIntent().getStringExtra(KEY_ACTION_URL);
        mWebView.loadUrl(url);
    }

    @OnClick(R.id.title_rightbutton)
    void OnClick(View view){

    }

    public static void start(Context context, String url,String title) {
        Intent starter = new Intent(context, ActionWebActivity.class);
        starter.putExtra(KEY_ACTION_URL,url);
        starter.putExtra(KEY_TITLE,title);
        context.startActivity(starter);
    }
}
