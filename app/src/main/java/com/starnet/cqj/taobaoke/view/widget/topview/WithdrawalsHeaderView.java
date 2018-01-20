package com.starnet.cqj.taobaoke.view.widget.topview;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.StringRes;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.CNCBKUser;
import com.starnet.cqj.taobaoke.presenter.IWithdrawalsPresenter;
import com.starnet.cqj.taobaoke.presenter.impl.WithdrawalsPresenterImpl;
import com.starnet.cqj.taobaoke.view.BaseApplication;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.starnet.cqj.taobaoke.R.id.home_tab_rb1;


public class WithdrawalsHeaderView extends LinearLayout implements IWithdrawalsPresenter.IView {

    private static final String TAG = "WithdrawalsHeaderView";

    @BindView(R.id.icon)
    ImageView mIcon;
    @BindView(R.id.tv_score)
    TextView mTvScore;
    @BindView(R.id.tv_arrow)
    TextView mTvArrow;
    @BindView(R.id.edt_bind_account)
    EditText mEdtBindAccount;
    @BindView(R.id.edt_bind_phone)
    EditText mEdtBindPhone;
    @BindView(R.id.btn_bind)
    Button mBtnBind;
    @BindView(R.id.ll_bind)
    LinearLayout mLlBind;
    @BindView(R.id.edt_withdrawals_integral)
    EditText mEdtWithdrawalsIntegral;
    @BindView(R.id.tv_tip_2)
    TextView mTvTip2;
    @BindView(R.id.tv_tip_3)
    TextView mTvTip3;
    @BindView(R.id.btn_withdrawals)
    Button mBtnWithdrawals;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.ll_add_account)
    LinearLayout mLlAddAccount;
    @BindView(home_tab_rb1)
    RadioButton mRbWithdrawalsCNCBK;
    @BindView(R.id.home_tab_rb2)
    RadioButton mRbWithdrawalsMoney;
    @BindView(R.id.home_tab_rg)
    RadioGroup mRg;
    @BindView(R.id.ll_withdrawals_cncbk)
    LinearLayout mLlWithdrawalsCncbk;
    @BindView(R.id.edt_alipay_name)
    EditText mEdtAlipayName;
    @BindView(R.id.edt_alipay_account)
    EditText mEdtAlipayAccount;
    @BindView(R.id.edt_withdrawals_integral_to_money)
    EditText mEdtWithdrawalsIntegralToMoney;
    @BindView(R.id.tv_money)
    TextView mTvMoney;
    @BindView(R.id.ll_withdrawals_money)
    LinearLayout mLlWithdrawalsMoney;
    private IWithdrawalsPresenter mPresenter;
    private String mToken;
    private String mScore;

    public WithdrawalsHeaderView(Context context) {
        super(context);
        init();
    }

    public WithdrawalsHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WithdrawalsHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_withdrawals_header, this, true);
        ButterKnife.bind(this);
        String tip = "<font color='#999999'>2.最低提取积分</font><font color='#6c75f8'>3000.需要整百提取，</font>" +
                "<font color='#999999'>非整百按照整百计算，如提取3111，实际到账3100</font>";
        mTvTip2.setText(Html.fromHtml(tip));
        String tip2 = "<font color='#999999'>3.</font><font color='#6c75f8'>账号绑定错误，</font>" +
                "<font color='#999999'>请联系客服修改后提现</font>";
        mTvTip3.setText(Html.fromHtml(tip2));
        mPresenter = new WithdrawalsPresenterImpl(this);
        if (getContext() instanceof Activity) {
            mToken = ((BaseApplication) ((Activity) getContext()).getApplication()).getToken();
        } else {
            Log.e(TAG, "init failed,please use in activity");
            return;
        }
        mPresenter.getBindUser(mToken);
        mPresenter.getScore(mToken);
        initEvent();
    }

    private void initEvent() {
        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.home_tab_rb1://mRbWithdrawalsCNCBK.getId():

                        mLlWithdrawalsCncbk.setVisibility(VISIBLE);
                        mLlWithdrawalsMoney.setVisibility(GONE);
                        break;
                    case R.id.home_tab_rb2://mRbWithdrawalsMoney.getId()
                        mLlWithdrawalsCncbk.setVisibility(GONE);
                        mLlWithdrawalsMoney.setVisibility(VISIBLE);

                        break;
                }
            }
        });
    }

    @OnClick({R.id.title_back, R.id.ll_add_account, R.id.btn_bind, R.id.btn_withdrawals})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                ((Activity) getContext()).finish();
                break;
            case R.id.ll_add_account:
                if (mLlBind.getVisibility() == View.VISIBLE) {
                    mTvArrow.setSelected(false);
                    mLlBind.setVisibility(View.GONE);
                } else {
                    mTvArrow.setSelected(true);
                    mLlBind.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.btn_bind:
                String userName = mEdtBindAccount.getText().toString();
                if (TextUtils.isEmpty(userName)) {
                    toast("请输入用户名");
                    mEdtBindAccount.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mEdtBindAccount.requestFocus();
                        }
                    }, 50);
                    return;
                }
                String phone = mEdtBindPhone.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    toast("请输入电话");
                    mEdtBindPhone.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mEdtBindPhone.requestFocus();
                        }
                    }, 50);
                    return;
                }
                mPresenter.bindUser(mToken, userName, phone);
                break;
            case R.id.btn_withdrawals:
                String score = mEdtWithdrawalsIntegral.getText().toString();
                if (TextUtils.isEmpty(score)) {
                    toast("请输入要提取的积分");
                    mEdtWithdrawalsIntegral.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mEdtWithdrawalsIntegral.requestFocus();
                        }
                    }, 50);
                    return;
                }
                mPresenter.cashCNCBK(mToken, score);
                break;
        }
    }


    @Override
    public void setScore(String score) {
        mScore = score;
        mTvScore.setText(String.format("￥%s", score));
    }

    @Override
    public void onGetBindUser(CNCBKUser user) {
        if (user == null || (TextUtils.isEmpty(user.getName()) && TextUtils.isEmpty(user.getPhone()))) {
            mLlAddAccount.setClickable(true);
            mTvArrow.setVisibility(View.VISIBLE);
            mTvUserName.setText(R.string.cncbk_user_name);
            mTvArrow.setSelected(true);
            mLlBind.setVisibility(View.VISIBLE);
        } else {
            mLlBind.setVisibility(View.GONE);
            mLlAddAccount.setClickable(false);
            mTvArrow.setVisibility(View.GONE);
            mTvUserName.setText(user.getName() + "（" + user.getPhone() + "）");
        }
    }

    @Override
    public void onCash() {
        toast("提取成功");
        mEdtWithdrawalsIntegral.setText("");
        mEdtWithdrawalsIntegralToMoney.setText("");
        mTvMoney.setText(getContext().getResources().getString(R.string.money_unit,0));
        mPresenter.getScore(mToken);
    }

    @Override
    public void toast(String res) {
        Toast.makeText(getContext(), res, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toast(@StringRes int res) {
        Toast.makeText(getContext(), res, Toast.LENGTH_SHORT).show();
    }

    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }

    @OnClick({R.id.btn_get_money,R.id.btn_withdrawals_to_money})
    public void onWithdrawalMoneyClick(View view) {
        switch (view.getId()){
            case R.id.btn_get_money:

                try {
                    double useIntegral = Double.parseDouble(mScore);
                    double integral = Double.parseDouble(mEdtWithdrawalsIntegralToMoney.getText().toString());
                    if(integral>useIntegral){
                        toast("您没有那么多积分");
                        mEdtWithdrawalsIntegralToMoney.setText("");
                        return;
                    }
                    int integralToMoney = (int) (integral /1000);
                    mTvMoney.setText(getContext().getResources().getString(R.string.money_unit,integralToMoney));
                } catch (NumberFormatException e) {
                    toast("请输入正确的积分");
                }


                break;
            case R.id.btn_withdrawals_to_money:
                String account = mEdtAlipayAccount.getText().toString();
                String name = mEdtAlipayName.getText().toString();
                if (TextUtils.isEmpty(account)||account.length()<11) {
                    toast("请输入正确的账号");
                    return;
                }
                if(TextUtils.isEmpty(name)){
                    toast("请输入正确的真实姓名");
                    return;
                }
                String money = mTvMoney.getText().toString().replace("元", "");
                if(money.equals("0")){
                    toast("现金为0");
                    return;
                }

                mPresenter.cashMoney(mToken,name,account,money);

                break;
        }
    }
}
