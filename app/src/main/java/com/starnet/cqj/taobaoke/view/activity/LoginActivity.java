package com.starnet.cqj.taobaoke.view.activity;

import android.support.annotation.StringRes;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.User;
import com.starnet.cqj.taobaoke.presenter.ILoginPresenter;
import com.starnet.cqj.taobaoke.presenter.impl.LoginPresenterImpl;
import com.starnet.cqj.taobaoke.view.BaseApplication;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class LoginActivity extends BaseActivity implements ILoginPresenter.IView {

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
    private ILoginPresenter mPresenter;

    @Override
    protected void init() {
        if (mIvTitleBack != null) {
            mIvTitleBack.setVisibility(View.GONE);
        }
        setTitleName(R.string.app_name);
        mPresenter = new LoginPresenterImpl(this);
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
            mIvAccountClear.setVisibility(View.INVISIBLE);
        }
    }

    @OnTextChanged(value = R.id.login_pwd, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onPwdTextChanged(Editable editable) {
        if (TextUtils.isEmpty(editable.toString())) {
            mIvPwdClear.setVisibility(View.GONE);
        } else {
            mIvPwdClear.setVisibility(View.INVISIBLE);
        }
    }


    @OnClick({R.id.account_clear, R.id.pwd_clear, R.id.login_btn, R.id.forgetpwd, R.id.regist, R.id.wechat_login})
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
                mPresenter.login(mobile, pwd);
                break;
            case R.id.forgetpwd:
                ForgetPwdActivity.start(this);
                break;
            case R.id.regist:
                RegisterActivity.start(this);
                break;
            case R.id.wechat_login:
                break;
        }
    }

    @Override
    public void toast(String res) {
        Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toast(@StringRes int res) {
        Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginSuccess(User user) {
        ((BaseApplication) getApplication()).user = user;
        MainActivity.start(this);
        finish();
    }
}
