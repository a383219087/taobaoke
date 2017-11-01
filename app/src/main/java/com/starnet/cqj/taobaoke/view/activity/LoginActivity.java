package com.starnet.cqj.taobaoke.view.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.starnet.cqj.taobaoke.R;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class LoginActivity extends BaseActivity {

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

    @Override
    protected void init() {
        mIvTitleBack.setVisibility(View.GONE);
        setTitleName(R.string.app_name);
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
        if(TextUtils.isEmpty(editable.toString())){
            mIvAccountClear.setVisibility(View.GONE);
        }else{
            mIvAccountClear.setVisibility(View.INVISIBLE);
        }
    }

    @OnTextChanged(value = R.id.login_pwd, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onPwdTextChanged(Editable editable) {
        if(TextUtils.isEmpty(editable.toString())){
            mIvPwdClear.setVisibility(View.GONE);
        }else{
            mIvPwdClear.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick(R.id.login_btn)
    void login(){

    }

    @OnClick(R.id.account_clear)
    void accountClear(){
        mEdtAccount.setText("");
    }

    @OnClick(R.id.pwd_clear)
    void pwdClear(){
        mEdtPwd.setText("");
    }

}
