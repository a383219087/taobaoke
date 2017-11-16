package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.WechatUser;
import com.starnet.cqj.taobaoke.view.widget.ButtonCountDown;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by mini on 17/11/16.
 */

public class BindExistActivity extends BaseActivity {


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

    @Override
    protected int getContentView() {
        return R.layout.activity_bind_exist;
    }

    @Override
    protected void init() {
        setTitleName(R.string.bind_exist_title);
    }

    @OnClick({R.id.btn_getcode, R.id.user_agreement, R.id.login_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_getcode:
                break;
            case R.id.user_agreement:
                break;
            case R.id.login_btn:
                break;
        }
    }

    public static void start(Context context, WechatUser user) {
        Intent starter = new Intent(context, BindExistActivity.class);
        starter.putExtra(KEY_WECHAT_USER,user);
        context.startActivity(starter);
    }
}
