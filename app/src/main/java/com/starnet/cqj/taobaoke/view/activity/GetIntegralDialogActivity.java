package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;

import butterknife.BindView;
import butterknife.OnClick;


public class GetIntegralDialogActivity extends BaseActivity {

    @BindView(R.id.dialog_tv_name)
    TextView mTvIntegral;

    @Override
    protected int getContentView() {
        return R.layout.dialog_get_integral;
    }

    @Override
    protected void init() {
    }

    @OnClick(R.id.btn_done)
    public void onViewClicked() {
        finish();
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, GetIntegralDialogActivity.class);
        context.startActivity(starter);
    }

}
