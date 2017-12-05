package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.Medal;

import butterknife.BindView;
import butterknife.OnClick;


public class GetMedalDialogActivity extends BaseActivity {


    public static final String KEY_MEDAL = "medal";
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
        Medal medal = (Medal) getIntent().getSerializableExtra(KEY_MEDAL);
        Glide.with(this)
                .load(medal.getPic())
                .into(mIvMedal);
        mTvMedal.setText(medal.getName());
    }

    @OnClick(R.id.btn_done)
    public void onViewClicked() {
        finish();
    }

    public static void start(Context context,Medal medal) {
        Intent starter = new Intent(context, GetMedalDialogActivity.class);
        starter.putExtra(KEY_MEDAL,medal);
        starter.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(starter);
    }

}
