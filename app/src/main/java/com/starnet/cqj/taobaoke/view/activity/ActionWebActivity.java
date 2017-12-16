package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;
import com.starnet.cqj.taobaoke.view.BaseApplication;
import com.starnet.cqj.taobaoke.view.widget.SharePopupWindow;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/12/07.
 */

public class ActionWebActivity extends WebViewActivity {


    public static final String KEY_ACTION_URL = "action_url";
    public static final String KEY_TITLE = "title";
    public static final String KEY_ACTIVE_ID = "active_id";

    @BindView(R.id.title_rightbutton)
    Button mBtnRecord;
    private String mToken;

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
        mToken = ((BaseApplication) getApplication()).getToken();
        header.put("Authorization", mToken);
        mWebView.loadUrl(url,header);
    }

    @OnClick(R.id.title_rightbutton)
    void OnClick(View view){
        SharePopupWindow sharePopupWindow = new SharePopupWindow(this);
//        sharePopupWindow.setDoneAction(new Action() {
//            @Override
//            public void run() throws Exception {
//                addCount();
//            }
//        });
        sharePopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    private void addCount() {
        String activeId = getIntent().getStringExtra(KEY_ACTIVE_ID);
        RemoteDataSourceBase.INSTANCE.getActionService()
                .addTimes(mToken,activeId)
                .compose(this.<JsonCommon<Object>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JsonCommon<Object>>() {
                    @Override
                    public void accept(JsonCommon<Object> objectJsonCommon) throws Exception {
                        if("200".equals(objectJsonCommon.getCode())){
                            mWebView.reload();
                        }else{
                            toast(objectJsonCommon.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        toast(R.string.net_error);
                    }
                });
    }

    public static void start(Context context, String url,String activeId) {
        Intent starter = new Intent(context, ActionWebActivity.class);
        starter.putExtra(KEY_ACTION_URL,url);
        starter.putExtra(KEY_ACTIVE_ID,activeId);
        context.startActivity(starter);
    }
}
