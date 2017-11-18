package com.starnet.cqj.taobaoke.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.starnet.cqj.taobaoke.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by mini on 17/11/18.
 */

public class EditNickNameActivity extends BaseActivity {

    public static final String KEY_RESULT = "result_data";
    private static final String KEY_NICK_NAME = "nick_name";
    @BindView(R.id.title_rightbutton)
    Button mTitleRightbutton;
    @BindView(R.id.edt_nick_name)
    EditText mEdtNickName;

    @Override
    protected int getContentView() {
        return R.layout.activity_edit_nick;
    }

    @Override
    protected void init() {
        setTitleName(R.string.edit_nick_title);
        mTitleRightbutton.setText("保存");
        mTitleRightbutton.setVisibility(View.VISIBLE);
        String nickName = getIntent().getStringExtra(KEY_NICK_NAME);
        mEdtNickName.setText(nickName);
    }

    @OnClick(R.id.title_rightbutton)
    public void onViewClicked() {
        //TODO
    }

    public static void start(Activity context, String nickName, int req) {
        Intent starter = new Intent(context, EditNickNameActivity.class);
        starter.putExtra(KEY_NICK_NAME, nickName);
        context.startActivityForResult(starter, req);
    }
}
