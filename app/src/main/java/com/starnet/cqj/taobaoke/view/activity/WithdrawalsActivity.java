package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by mini on 17/11/14.
 */

public class WithdrawalsActivity extends BaseActivity {


    public static final String KEY_SCORE = "score";
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

        String score = getIntent().getStringExtra(KEY_SCORE);
        mTvScore.setText(String.format("￥%s", score));
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
                break;
            case R.id.btn_withdrawals:
                break;
        }
    }

    public static void start(Context context, String score) {
        Intent starter = new Intent(context, WithdrawalsActivity.class);
        starter.putExtra(KEY_SCORE, score);
        context.startActivity(starter);
    }
}
