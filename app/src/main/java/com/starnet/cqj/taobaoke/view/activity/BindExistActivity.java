package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.User;
import com.starnet.cqj.taobaoke.model.WechatUser;
import com.starnet.cqj.taobaoke.presenter.IBindExistPresenter;
import com.starnet.cqj.taobaoke.presenter.impl.BindExistPresenterImpl;
import com.starnet.cqj.taobaoke.view.BaseApplication;
import com.starnet.cqj.taobaoke.view.widget.ButtonCountDown;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;


public class BindExistActivity extends BaseActivity implements IBindExistPresenter.IView {


    public static final String KEY_WECHAT_USER = "wechat_user";
    @BindView(R.id.edt_username)
    EditText mEdtUsername;
    @BindView(R.id.edt_code)
    EditText mEdtCode;
    @BindView(R.id.btn_getcode)
    ButtonCountDown mBtnGetcode;
    @BindView(R.id.cb_agreement)
    CheckBox mCbAgreement;
    @BindView(R.id.user_agreement)
    TextView mUserAgreement;
    @BindView(R.id.login_btn)
    Button mLoginBtn;
    private IBindExistPresenter mPresenter;
    private WechatUser mWechatUser;

    @Override
    protected int getContentView() {
        return R.layout.activity_bind_exist;
    }

    @Override
    protected void init() {
        setTitleName(R.string.bind_exist_title);
        mPresenter = new BindExistPresenterImpl(this);
        mWechatUser = (WechatUser) getIntent().getSerializableExtra(KEY_WECHAT_USER);
    }

    @OnClick({R.id.btn_getcode, R.id.user_agreement, R.id.login_btn})
    public void onViewClicked(View view) {
        String phone = mEdtUsername.getText().toString().trim();
        switch (view.getId()) {
            case R.id.btn_getcode:
                if (TextUtils.isEmpty(phone)) {
                    toast("请输入已有账号（手机号）");
                    return;
                }
                mPresenter.getCode(phone);
                break;
            case R.id.user_agreement:
                UserAgreementActivity.start(this);
                break;
            case R.id.login_btn:
                if (TextUtils.isEmpty(phone)) {
                    toast("请输入已有账号（手机号）");
                    return;
                }
                String code = mEdtCode.getText().toString().trim();
                if (TextUtils.isEmpty(code)) {
                    toast("请输入验证码");
                    return;
                }
                mWechatUser.setMobile(phone);
                mWechatUser.setCode(code);
                String regId = JPushInterface.getRegistrationID(this);
                mPresenter.bind(mWechatUser,regId);
                break;
        }
    }

    @Override
    public void onGetCode() {
        mBtnGetcode.start();
    }

    @Override
    public void onBind(User user) {
        ((BaseApplication) getApplication()).setToken(user.getToken());
        MainActivity.start(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBtnGetcode.stop();
    }

    public static void start(Context context, WechatUser user) {
        Intent starter = new Intent(context, BindExistActivity.class);
        starter.putExtra(KEY_WECHAT_USER, user);
        context.startActivity(starter);
    }
}
