package com.starnet.cqj.taobaoke.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;

import butterknife.BindView;
import butterknife.OnClick;


public class GetMedalDialogActivity extends BaseActivity {


    @BindView(R.id.iv_medal)
    ImageView mIvMedal;
    @BindView(R.id.tv_medal)
    TextView mTvMedal;

    @Override
    protected int getContentView() {
        return R.layout.dialog_get_medal;
    }

    @Override
    protected void init() {
    }

    @OnClick(R.id.btn_done)
    public void onViewClicked() {
        finish();
    }

    public static void start(Activity context) {
        Intent starter = new Intent(context, GetMedalDialogActivity.class);
        context.startActivity(starter);
    }

}
