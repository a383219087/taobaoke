package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.starnet.cqj.taobaoke.R;
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
    @BindView(R.id.user_agreement)
    TextView mUserAgreement;
    @BindView(R.id.recommend_id)
    EditText mRecommendId;
    @BindView(R.id.recom_ln)
    LinearLayout mRecomLn;
    @BindView(R.id.regist_commit)
    Button mRegistCommit;

    private IRegisterPresenter mRegisterPresenter;

    @Override
    protected void init() {
        setTitleName(R.string.register_title);
        mRegisterPresenter = new RegisterPresenterImpl(this);
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
                    mRegisterPresenter.sendSMS(mobile);
                    mRegistGetCode.start();
                } else {
                    toast("请输入正确的电话号码");
                }
                break;
            case R.id.user_agreement:
                UserAgreementActivity.start(this);
                break;
            case R.id.regist_commit:
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
    public void getSMSCode(String code) {

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
