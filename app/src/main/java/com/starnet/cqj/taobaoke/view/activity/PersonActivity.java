package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.User;

import butterknife.BindView;
import butterknife.OnClick;


public class PersonActivity extends BaseActivity {


    public static final String KEY_USER = "user";
    public static final int REQUEST_CODE = 101;
    public static final int EDIT_NICK_REQUEST_CODE = 102;
    @BindView(R.id.iv_avatar)
    ImageView mIvAvatar;
    @BindView(R.id.tv_nick_name)
    TextView mTvNickName;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;

    @Override
    protected int getContentView() {
        return R.layout.activity_person;
    }

    @Override
    protected void init() {
        setTitleName(R.string.person_title);
        User user = (User) getIntent().getSerializableExtra(KEY_USER);
        if (!TextUtils.isEmpty(user.getAvatar())) {
            Glide.with(this)
                    .load(user.getAvatar())
                    .into(mIvAvatar);
        }
        mTvNickName.setText(user.getNickname());
        mTvPhone.setText(user.getMobile());
    }

    @OnClick({R.id.ll_change_nick, R.id.ll_change_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_change_nick:
                EditNickNameActivity.start(this,mTvNickName.getText().toString(), EDIT_NICK_REQUEST_CODE);
                break;
            case R.id.ll_change_phone:
                BindPhoneActivity.start(this, REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                String phone = data.getStringExtra(BindPhoneActivity.KEY_RESULT);
                mTvPhone.setText(phone);
            }else if(requestCode == EDIT_NICK_REQUEST_CODE){
                String nickName = data.getStringExtra(EditNickNameActivity.KEY_RESULT);
                mTvNickName.setText(nickName);
            }
        }
    }

    public static void start(Context context, User user) {
        Intent starter = new Intent(context, PersonActivity.class);
        starter.putExtra(KEY_USER, user);
        context.startActivity(starter);
    }
}
