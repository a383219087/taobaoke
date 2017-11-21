package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.CNCBKUser;
import com.starnet.cqj.taobaoke.presenter.IWithdrawalsPresenter;
import com.starnet.cqj.taobaoke.presenter.impl.WithdrawalsPresenterImpl;
import com.starnet.cqj.taobaoke.view.BaseApplication;

import butterknife.BindView;
import butterknife.OnClick;


public class WithdrawalsActivity extends BaseActivity implements IWithdrawalsPresenter.IView {


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
    private IWithdrawalsPresenter mPresenter;

    @Override
    protected int getContentView() {
        return R.layout.activity_withdrawals;
    }

    @Override
    protected void init() {
        String tip = "<font color='#999999'>2.最低提取积分</font><font color='#6c75f8'>3000.需要整百提取，</font>" +
                "<font color='#999999'>非整百按照整百计算，如提取3111，实际到账3100</font>";
        mTvTip2.setText(Html.fromHtml(tip));
        String tip2 = "<font color='#999999'>3.</font><font color='#6c75f8'>账号绑定错误，</font>" +
                "<font color='#999999'>请联系客服修改后提现</font>";
        mTvTip3.setText(Html.fromHtml(tip2));
        mPresenter = new WithdrawalsPresenterImpl(this);
        mPresenter.getBindUser(((BaseApplication) getApplication()).token);
        mPresenter.getScore(((BaseApplication) getApplication()).token);
    }


    @OnClick({R.id.ll_add_account, R.id.btn_bind, R.id.btn_withdrawals})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
                mPresenter.bindUser(((BaseApplication) getApplication()).token, userName, phone);
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
                mPresenter.cashCNCBK(((BaseApplication) getApplication()).token, score);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }

    @Override
    public void setScore(String score) {
        mTvScore.setText(String.format("￥%s", score));
    }

    @Override
    public void onGetBindUser(CNCBKUser user) {
        if (user == null || (TextUtils.isEmpty(user.getName()) && TextUtils.isEmpty(user.getPhone()))) {
            mLlAddAccount.setClickable(true);
            mTvArrow.setVisibility(View.VISIBLE);
            mTvUserName.setText(R.string.cncbk_user_name);
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
        mPresenter.getScore(((BaseApplication) getApplication()).token);
    }


    public static void start(Context context) {
        Intent starter = new Intent(context, WithdrawalsActivity.class);
        context.startActivity(starter);
    }

}
