package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.User;
import com.starnet.cqj.taobaoke.presenter.IRegisterPresenter;
import com.starnet.cqj.taobaoke.presenter.impl.RegisterPresenterImpl;
import com.starnet.cqj.taobaoke.utils.StringUtils;
import com.starnet.cqj.taobaoke.view.widget.ButtonCountDown;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity implements IRegisterPresenter.IView {


    @BindView(R.id.regist_username)
    EditText mRegistUsername;
    @BindView(R.id.regist_code_edt)
    EditText mRegistCodeEdt;
    @BindView(R.id.regist_get_code)
    ButtonCountDown mRegistGetCode;
    @BindView(R.id.regist_pwd)
    EditText mRegistPwd;
    @BindView(R.id.regist_pwdagain)
    EditText mRegistPwdagain;
    @BindView(R.id.regist_recomer)
    CheckBox mRegistRecomer;
    @BindView(R.id.regist_nick)
    EditText mEdtNickName;
    @BindView(R.id.ll_nick_name)
    LinearLayout mLlNickName;

    private IRegisterPresenter mRegisterPresenter;

    @Override
    protected void init() {
        setTitleName(R.string.register_title);
        mRegisterPresenter = getPresenter();
    }

    protected IRegisterPresenter getPresenter() {
        return new RegisterPresenterImpl(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_register;
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, RegisterActivity.class);
        context.startActivity(starter);
    }


    @OnClick({R.id.regist_get_code, R.id.user_agreement, R.id.regist_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.regist_get_code:
                String mobile = mRegistUsername.getText().toString();
                if (StringUtils.isPhone(mobile)) {
                    mRegisterPresenter.checkAccount(mobile);
                } else {
                    toast("请输入正确的电话号码");
                }
                break;
            case R.id.user_agreement:
                UserAgreementActivity.start(this);
                break;
            case R.id.regist_commit:
                register();
                break;
        }
    }

    private void register() {
        String mobile = mRegistUsername.getText().toString();
        String pwd = mRegistPwd.getText().toString();
        String pwdAgain = mRegistPwdagain.getText().toString();
        String code = mRegistCodeEdt.getText().toString();
        String nickName = mEdtNickName.getText().toString();
        if (TextUtils.isEmpty(mobile)) {
            toast("请输入电话号码");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            toast("请输入密码");
            return;
        }
        if (TextUtils.isEmpty(pwdAgain)) {
            toast("请再次输入密码");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            toast("请输入验证码");
            return;
        }
        if (!checkNickName(nickName)) return;
        if (!mRegistRecomer.isChecked()) {
            toast("请详细阅读并同意注册协议");
            return;
        }
        if (!pwd.equals(pwdAgain)) {
            toast("两次输入的密码不一致");
            return;
        }
        mRegisterPresenter.register(mobile, pwd, nickName, code);
    }

    protected boolean checkNickName(String nickName) {
        if (TextUtils.isEmpty(nickName)) {
            toast("请输入昵称");
            return false;
        }
        return true;
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
    public void onGetCode() {
        mRegistGetCode.start();
    }

    @Override
    public void onRegisterSuccess(User user) {
        toast("注册成功");
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRegisterPresenter.onDestroy();
        if (mRegistGetCode != null) {
            mRegistGetCode.stop();
        }
    }
}
