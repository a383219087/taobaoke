package com.starnet.cqj.taobaoke.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.presenter.IBindPhonePresenter;
import com.starnet.cqj.taobaoke.presenter.impl.BindPhonePresenterImpl;
import com.starnet.cqj.taobaoke.view.BaseApplication;
import com.starnet.cqj.taobaoke.view.widget.ButtonCountDown;

import butterknife.BindView;
import butterknife.OnClick;

public class BindPhoneActivity extends BaseActivity implements IBindPhonePresenter.IView {

    public static final String KEY_RESULT = "phone";
    @BindView(R.id.bindp_username)
    EditText mBindpUsername;
    @BindView(R.id.bindp_code_edt)
    EditText mBindpCodeEdt;
    @BindView(R.id.bindp_getcode)
    ButtonCountDown mBindpGetcode;
    private IBindPhonePresenter mPresenter;

    @Override
    protected int getContentView() {
        return R.layout.activity_bind_phone;
    }

    @Override
    protected void init() {
        setTitleName(R.string.change_phone_title);
        mPresenter = new BindPhonePresenterImpl(this);
    }

    @OnClick({R.id.bindp_getcode, R.id.bindp_commit})
    public void onViewClicked(View view) {
        String phone = mBindpUsername.getText().toString().trim();
        if(TextUtils.isEmpty(phone)){
            toast("请输入要更换的手机号");
            return;
        }
        switch (view.getId()) {
            case R.id.bindp_getcode:
                mPresenter.getCode(phone);
                break;
            case R.id.bindp_commit:
                String code = mBindpCodeEdt.getText().toString().trim();
                if(TextUtils.isEmpty(code)){
                    toast("请输入验证码");
                    return;
                }
                mPresenter.bind(((BaseApplication) getApplication()).getToken(),phone,code);
                break;
        }
    }


    @Override
    public void onGetCode() {
        mBindpGetcode.start();
    }

    @Override
    public void onBind() {
        Intent intent = new Intent();
        intent.putExtra(KEY_RESULT,mBindpUsername.getText().toString().trim());
        setResult(RESULT_OK,intent);
        toast("更换成功");
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBindpGetcode != null) {
            mBindpGetcode.stop();
        }
    }

    public static void start(Activity context, int requestCode) {
        Intent starter = new Intent(context, BindPhoneActivity.class);
        context.startActivityForResult(starter, requestCode);
    }

}
