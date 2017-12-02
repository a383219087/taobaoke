package com.starnet.cqj.taobaoke.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.Medal;

import butterknife.BindView;
import butterknife.OnClick;


public class MedalDetailActivity extends BaseActivity {
    public static final String KEY_MEDAL = "medal";
    @BindView(R.id.dialog_iv_medal)
    ImageView mDialogIvMedal;
    @BindView(R.id.dialog_tv_name)
    TextView mDialogTvName;
    @BindView(R.id.dialog_tv_time)
    TextView mDialogTvTime;
    @BindView(R.id.dialog_tv_remark)
    TextView mDialogTvRemark;

    @Override
    protected int getContentView() {
        return R.layout.dialog_medal;
    }

    @Override
    protected void init() {
        Medal medal = (Medal) getIntent().getSerializableExtra(KEY_MEDAL);
        Glide.with(this).load(medal.getPic()).into(mDialogIvMedal);
        mDialogTvName.setText(medal.getName());
        mDialogTvTime.setText("0".equals(medal.getIsGet()) ? "未获得" : medal.getTime());
        mDialogTvRemark.setText(medal.getConditions() + "即可获得\n\n" + medal.getDesc());
    }

    @OnClick(R.id.btn_done)
    public void onViewClicked() {
        finish();
    }

    public static void start(Activity context, Medal medal) {
        Intent starter = new Intent(context, MedalDetailActivity.class);
        starter.putExtra(KEY_MEDAL, medal);
        context.startActivity(starter);
    }
}
