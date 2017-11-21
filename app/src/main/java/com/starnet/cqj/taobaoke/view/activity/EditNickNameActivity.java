package com.starnet.cqj.taobaoke.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;
import com.starnet.cqj.taobaoke.view.BaseApplication;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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
    private String mNickName;

    @Override
    protected int getContentView() {
        return R.layout.activity_edit_nick;
    }

    @Override
    protected void init() {
        setTitleName(R.string.edit_nick_title);
        mTitleRightbutton.setText("保存");
        mTitleRightbutton.setVisibility(View.VISIBLE);
        mNickName = getIntent().getStringExtra(KEY_NICK_NAME);
        mEdtNickName.setText(mNickName);
    }

    @OnClick(R.id.title_rightbutton)
    public void onViewClicked() {
        final String nickName = mEdtNickName.getText().toString().trim();
        if (TextUtils.isEmpty(nickName)) {
            toast("请输入昵称");
            return;
        }
        if (nickName.equals(mNickName)) {
            toast("昵称未修改");
            return;
        }
        RemoteDataSourceBase.INSTANCE.getUserService()
                .modify(((BaseApplication) getApplication()).token, nickName)
                .compose(this.<JsonCommon<Object>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<JsonCommon<Object>>() {
                    @Override
                    public void accept(JsonCommon<Object> objectJsonCommon) throws Exception {
                        if("200".equals(objectJsonCommon.getCode())){
                            toast("修改成功");
                            Intent intent = new Intent();
                            intent.putExtra(KEY_RESULT,nickName);
                            setResult(RESULT_OK,intent);
                            finish();
                        }else{
                            toast(objectJsonCommon.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }

    public static void start(Activity context, String nickName, int req) {
        Intent starter = new Intent(context, EditNickNameActivity.class);
        starter.putExtra(KEY_NICK_NAME, nickName);
        context.startActivityForResult(starter, req);
    }
}
