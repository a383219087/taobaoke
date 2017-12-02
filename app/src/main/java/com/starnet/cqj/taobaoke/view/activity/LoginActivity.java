package com.starnet.cqj.taobaoke.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.StringRes;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.User;
import com.starnet.cqj.taobaoke.model.WechatUser;
import com.starnet.cqj.taobaoke.presenter.ILoginPresenter;
import com.starnet.cqj.taobaoke.presenter.impl.LoginPresenterImpl;
import com.starnet.cqj.taobaoke.remote.Constant;
import com.starnet.cqj.taobaoke.view.BaseApplication;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import cn.jpush.android.api.JPushInterface;

public class LoginActivity extends BaseActivity implements ILoginPresenter.IView {

    public static final String KEY_ACCOUNT = "account";
    @BindView(R.id.login_username)
    EditText mEdtAccount;
    @BindView(R.id.login_pwd)
    EditText mEdtPwd;
    @BindView(R.id.login_btn)
    Button mBtnSignIn;
    @BindView(R.id.account_clear)
    ImageView mIvAccountClear;
    @BindView(R.id.pwd_clear)
    ImageView mIvPwdClear;

    private ProgressDialog mProgressDialog;

    private ILoginPresenter mPresenter;
    private SharedPreferences mSharedPreferences;
    private WechatUser mWechatUser;


    @Override
    protected void init() {
        setTitleName(R.string.login_title);
        mPresenter = new LoginPresenterImpl(this);
        mSharedPreferences = getSharedPreferences(Constant.COMMON_PREFERENCE_NAME, Context.MODE_PRIVATE);
        String account = mSharedPreferences.getString(KEY_ACCOUNT, "");
        if (!TextUtils.isEmpty(account)) {
            mEdtAccount.setText(account);
            mEdtPwd.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mEdtPwd.requestFocus();
                }
            }, 100);
        }
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("登录中……");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initEvent() {
        super.initEvent();
    }

    @OnTextChanged(value = R.id.login_username, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onAccountTextChanged(Editable editable) {
        if (TextUtils.isEmpty(editable.toString())) {
            mIvAccountClear.setVisibility(View.GONE);
        } else {
            mIvAccountClear.setVisibility(View.VISIBLE);
        }
    }

    @OnTextChanged(value = R.id.login_pwd, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onPwdTextChanged(Editable editable) {
        if (TextUtils.isEmpty(editable.toString())) {
            mIvPwdClear.setVisibility(View.GONE);
        } else {
            mIvPwdClear.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);//完成回调
    }

    @OnClick({R.id.account_clear, R.id.pwd_clear, R.id.login_btn, R.id.forgetpwd, R.id.regist, R.id.wechat_login,
            R.id.iv_wechat_login, R.id.tv_wechat_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.account_clear:
                mEdtAccount.setText("");
                break;
            case R.id.pwd_clear:
                mEdtPwd.setText("");
                break;
            case R.id.login_btn:
                String mobile = mEdtAccount.getText().toString();
                String pwd = mEdtPwd.getText().toString();
                if (TextUtils.isEmpty(mobile)) {
                    toast("请输入账号");
                    return;
                }
                if (TextUtils.isEmpty(pwd)) {
                    toast("请输入密码");
                    return;
                }
                mProgressDialog.show();
                String regId = JPushInterface.getRegistrationID(this);
                mPresenter.login(mobile, pwd, regId);
                break;
            case R.id.forgetpwd:
                ForgetPwdActivity.start(this);
                break;
            case R.id.regist:
                RegisterActivity.start(this);
                break;
            case R.id.wechat_login:
            case R.id.iv_wechat_login:
            case R.id.tv_wechat_login:
                mProgressDialog.show();
                UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.WEIXIN, new UMAuthListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                        Log.e(TAG, "onStart: ");
                    }

                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                        Log.e(TAG, "onComplete: " + map.toString());
                        mWechatUser = new WechatUser();
                        mWechatUser.setOpenid(map.get("openid"));
                        mWechatUser.setAvatar(map.get("iconurl"));
                        mWechatUser.setGender(map.get("gender"));
                        mWechatUser.setNickname(map.get("name"));
                        mWechatUser.setUnionId(map.get("unionid"));
                        String regId = JPushInterface.getRegistrationID(LoginActivity.this);
                        mPresenter.wechatLogin(mWechatUser.getOpenid(), regId);
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                        Log.e(TAG, "onError: ", throwable);
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {
                        Log.e(TAG, "onCancel: ");
                    }
                });
                break;
        }
    }

    private static final String TAG = "LoginActivity";


    @Override
    public void onLoginSuccess(User user) {
        mProgressDialog.dismiss();
        toast("登录成功");
        ((BaseApplication) getApplication()).setToken(user.getToken());
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString(KEY_ACCOUNT, mEdtAccount.getText().toString());
        edit.apply();
        finish();
    }

    @Override
    public void noBind() {
        mProgressDialog.dismiss();
        BindActivity.start(this, mWechatUser);
    }

    @Override
    public void toast(String res) {
        super.toast(res);
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void toast(@StringRes int res) {
        super.toast(res);
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        starter.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(starter);
    }
}
