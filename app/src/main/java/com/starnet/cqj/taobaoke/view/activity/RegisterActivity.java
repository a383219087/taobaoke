package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.User;
import com.starnet.cqj.taobaoke.model.city.CityResult;
import com.starnet.cqj.taobaoke.presenter.IRegisterPresenter;
import com.starnet.cqj.taobaoke.presenter.impl.RegisterPresenterImpl;
import com.starnet.cqj.taobaoke.utils.StringUtils;
import com.starnet.cqj.taobaoke.view.widget.ButtonCountDown;
import com.starnet.cqj.taobaoke.view.widget.CityPicker;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

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
    @BindView(R.id.tv_address)
    TextView mTvAddress;

    private IRegisterPresenter mRegisterPresenter;
//    private OptionsPickerView pvOptions;//地址选择器
    private String mProvince;
    private String mCity;
    private String mArea;
    private CityPicker mCityPicker;
    //    private boolean initCityDone;

    @Override
    protected void init() {
        setTitleName(R.string.register_title);
        mRegisterPresenter = getPresenter();
        mCityPicker = new CityPicker(this);
//        pvOptions = new OptionsPickerView(this);
//        mRegisterPresenter.initCity(getApplication());
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mCityPicker.resultObservable()
                .compose(this.<CityPicker.GetCityResult>bindToLifecycle())
                .subscribe(new Consumer<CityPicker.GetCityResult>() {
                    @Override
                    public void accept(CityPicker.GetCityResult s) throws Exception {
                        mProvince =s.province;
                        mCity = s.city;
                        mArea = s.area;
                        mTvAddress.setText(TextUtils.isEmpty(mArea) ? "" : mProvince + " " + mCity + " " + mArea);
                    }
                });
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


    @OnClick({R.id.regist_get_code, R.id.user_agreement, R.id.regist_commit, R.id.ll_choose_area})
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
            case R.id.ll_choose_area:
                mCityPicker.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
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
        if (TextUtils.isEmpty(mProvince)) {
            toast("请选择地址");
            return;
        }
        if (TextUtils.isEmpty(mCity)) {
            toast("请选择地址");
            return;
        }

        mRegisterPresenter.register(mobile, pwd, nickName, code, mProvince, mCity,mArea);

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
    public void onInitCity(final CityResult result) {
//        initCityDone = true;
//        pvOptions.setPicker(result.Provincestr, result.Citystr, true);
//        pvOptions.setTitle("选择城市");
//        //设置是否循环滚动
//        pvOptions.setCyclic(false, false, false);
//        //设置默认选中的三级项目
//        //监听确定选择按钮
//        pvOptions.setSelectOptions(0, 0);
//        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
//            @Override
//            public void onOptionsSelect(int options1, int option2, int options3) {
//                //返回的分别是三个级别的选中位置
//                String tx = result.options1Items.get(options1).getPro_name() + ","
//                        + result.options2Items.get(options1).get(option2).getName();
//                mProvince = result.options1Items.get(options1).getPro_name();
//                mCity = result.options2Items.get(options1).get(option2).getName();
//                mTvAddress.setText(tx);
//            }
//        });
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
