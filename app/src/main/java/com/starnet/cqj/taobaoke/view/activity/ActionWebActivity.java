package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.view.BaseApplication;
import com.starnet.cqj.taobaoke.view.widget.SharePopupWindow;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Action;

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
        mBtnRecord.setVisibility(View.VISIBLE);
        mBtnRecord.setText(R.string.share_text);
        initWebView();
        String url = getIntent().getStringExtra(KEY_ACTION_URL);
        HashMap<String,String> header = new HashMap<>();
        header.put("Authorization", ((BaseApplication) getApplication()).getToken());
        mWebView.loadUrl(url,header);
    }

    @OnClick(R.id.title_rightbutton)
    void OnClick(View view){
        SharePopupWindow sharePopupWindow = new SharePopupWindow(this);
        sharePopupWindow.setDoneAction(new Action() {
            @Override
            public void run() throws Exception {
                mWebView.reload();
            }
        });
        sharePopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    public static void start(Context context, String url) {
        Intent starter = new Intent(context, ActionWebActivity.class);
        starter.putExtra(KEY_ACTION_URL,url);
        context.startActivity(starter);
    }
}
