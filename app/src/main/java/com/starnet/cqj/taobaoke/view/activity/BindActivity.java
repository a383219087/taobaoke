package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.WechatUser;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by mini on 17/11/16.
 */

public class BindActivity extends BaseActivity {


    public static final String KEY_ICON_URL = "icon_url";
    public static final String KEY_USER = "user";
    @BindView(R.id.iv_avatar)
    ImageView mIvAvatar;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.btn_bind_exist)
    Button mBtnBindExist;
    @BindView(R.id.btn_bind_new)
    Button mBtnBindNew;
    private WechatUser mWechatUser;

    @Override
    protected int getContentView() {
        return R.layout.activity_bind;
    }

    @Override
    protected void init() {
        setTitleName(R.string.account_bind_title);
        mWechatUser = (WechatUser) getIntent().getSerializableExtra(KEY_USER);
        mTvName.setText(TextUtils.isEmpty(mWechatUser.getNickname()) ? "" : mWechatUser.getNickname());
        String iconUrl = mWechatUser.getAvatar();
        if (!TextUtils.isEmpty(iconUrl) && !"/0".equals(iconUrl)) {
            Glide.with(this)
                    .load(iconUrl)
                    .into(mIvAvatar);

        }

    }

    @OnClick({R.id.btn_bind_exist, R.id.btn_bind_new})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_bind_exist:
                BindExistActivity.start(this, mWechatUser);
                break;
            case R.id.btn_bind_new:
                break;
        }
    }

    public static void start(Context context, WechatUser user) {
        Intent starter = new Intent(context, BindActivity.class);
        starter.putExtra(KEY_USER, user);
        context.startActivity(starter);
    }
}
