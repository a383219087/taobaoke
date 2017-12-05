package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.starnet.cqj.taobaoke.R;

import butterknife.BindView;


public class WebViewActivity extends BaseActivity {

    public static final String KEY_URL = "url";
    @BindView(R.id.webview1)
    WebView mWebView;
    @BindView(R.id.progressBar1)
    ProgressBar mProgressBar;

    @Override
    protected int getContentView() {
        return R.layout.activity_webview;
    }

    @Override
    protected void init() {
        setTitleName(R.string.hot_detail_title);
        String url = getIntent().getStringExtra(KEY_URL);
        initWebView();
        mWebView.loadUrl(url);
    }

    protected void initWebView() {

        mWebView.setWebViewClient(new WebViewClient() {
            //覆写shouldOverrideUrlLoading实现内部显示网页
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                WebView.HitTestResult hitTestResult = view.getHitTestResult();
                //hitTestResult==null解决重定向问题
                if (!TextUtils.isEmpty(url) && hitTestResult == null) {
                    view.loadUrl(url);
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
            public void onPageFinished(WebView view, String url) {
                CookieManager cookieManager = CookieManager.getInstance();
                String CookieStr = cookieManager.getCookie(url);
                if (CookieStr != null) {
                    Log.i("cookie", CookieStr);
                }
                super.onPageFinished(view, url);
            }
        });
        initWebViewSettings();
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    mProgressBar.setProgress(newProgress);//设置进度值
                }

            }

        });

    }

    private void initWebViewSettings(){
        // 设置可以访问文件
        mWebView.getSettings().setAllowFileAccess(true);
        //如果访问的页面中有Javascript，则webview必须设置支持Javascript
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setDatabaseEnabled(true);
    }

//    private void syncCookie(Context context, String url){
//        try{
//            CookieSyncManager.createInstance(context);
//            CookieManager cookieManager = CookieManager.getInstance();
//            cookieManager.setAcceptCookie(true);
//            cookieManager.removeSessionCookie();//移除
//            cookieManager.setCookie(url, cookies);//cookies是在HttpClient中获得的cookie
//            CookieSyncManager.getInstance().sync();
//        }catch(Exception ignored){
//        }
//    }

    //设置返回键动作（防止按返回键直接退出程序)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {//当webview不是处于第一页面时，返回上一个页面
                mWebView.goBack();
                return true;
            } else {//当webview处于第一页面时,直接退出程序
                finish();
            }


        }
        return super.onKeyDown(keyCode, event);
    }


    public static void start(Context context, String url) {
        Intent starter = new Intent(context, WebViewActivity.class);
        starter.putExtra(KEY_URL, url);
        context.startActivity(starter);
    }

}
