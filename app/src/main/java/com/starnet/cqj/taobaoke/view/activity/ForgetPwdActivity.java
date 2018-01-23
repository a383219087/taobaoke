package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.presenter.IForgetPwdPresenter;
import com.starnet.cqj.taobaoke.presenter.impl.ForgetPwdPresenterImpl;
import com.starnet.cqj.taobaoke.utils.StringUtils;
import com.starnet.cqj.taobaoke.view.widget.ButtonCountDown;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgetPwdActivity extends BaseActivity implements IForgetPwdPresenter.IView {


    @BindView(R.id.forgetpwd_username)
    EditText mForgetpwdUsername;
    @BindView(R.id.forgetpwd_code_edt)
    EditText mForgetpwdCodeEdt;
    @BindView(R.id.forgetpwd_getcode)
    ButtonCountDown mForgetpwdGetcode;
    @BindView(R.id.newpwd)
    EditText mNewpwd;
    @BindView(R.id.newpwdagain)
    EditText mNewpwdagain;
    private IForgetPwdPresenter mPresenter;

    @Override
    protected void init() {
        setTitleName(R.string.forget_pwd);
        mPresenter = new ForgetPwdPresenterImpl(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_forgetpwd;
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, ForgetPwdActivity.class);
        context.startActivity(starter);
    }

    @OnClick({R.id.forgetpwd_getcode, R.id.forgetpwd_commit})
    public void onViewClicked(View view) {
        String mobile = mForgetpwdUsername.getText().toString();
        switch (view.getId()) {
            case R.id.forgetpwd_getcode:
                if (StringUtils.isPhone(mobile)) {
                    mPresenter.sendSMS(mobile);
                } else {
                    toast("请输入正确的电话号码");
                }
                break;
            case R.id.forgetpwd_commit:
                String code = mForgetpwdCodeEdt.getText().toString();
                String newPwd = mNewpwd.getText().toString();
                String newPwdAgain = mNewpwdagain.getText().toString();
                if (TextUtils.isEmpty(mobile)) {
                    toast("请输入电话号码");
                    return;
                }
                if (TextUtils.isEmpty(newPwd)) {
                    toast("请输入密码");
                    return;
                }
                if(newPwd.length()<6){
                    toast("密码长度不够");
                    return;
                }
                if (TextUtils.isEmpty(newPwdAgain)) {
                    toast("请再次输入密码");
                    return;
                }
                if (TextUtils.isEmpty(code)) {
                    toast("请输入验证码");
                    return;
                }
                if (!newPwd.equals(newPwdAgain)) {
                    toast("两次输入的密码不一致");
                    return;
                }
                mPresenter.reset(mobile, newPwd, newPwdAgain, code);
                break;
        }
    }


    @Override
    public void onGetCode() {
        mForgetpwdGetcode.start();
    }

    @Override
    public void onResetSuccess() {
        toast("重置成功");
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        if (mForgetpwdGetcode != null) {
            mForgetpwdGetcode.stop();
        }
    }
}
