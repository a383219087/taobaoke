package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
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


public class EditPwdActivity extends BaseActivity {
    @BindView(R.id.edt_origin_pwd)
    EditText mEdtOriginPwd;
    @BindView(R.id.edt_new_pwd)
    EditText mEdtNewPwd;
    @BindView(R.id.edt_new_pwd_again)
    EditText mEdtNewPwdAgain;

    @Override
    protected int getContentView() {
        return R.layout.activity_edit_pwd;
    }

    @Override
    protected void init() {
        setTitleName(R.string.edit_pwd_title);
    }

    @OnClick(R.id.btn_edit)
    public void onViewClicked() {
        String oldPwd = mEdtOriginPwd.getText().toString().trim();
        String newPwd = mEdtNewPwd.getText().toString().trim();
        String newConfirm = mEdtNewPwdAgain.getText().toString().trim();
        if (TextUtils.isEmpty(oldPwd)) {
            toast("请输入原密码");
            return;
        }
        if (TextUtils.isEmpty(newPwd)) {
            toast("请输入新密码");
            return;
        }
        if (TextUtils.isEmpty(newConfirm)) {
            toast("请再次输入新密码");
            return;
        }
        if (!newConfirm.equals(newPwd)) {
            toast("两次输入的密码不一致");
            return;
        }
        RemoteDataSourceBase.INSTANCE.getUserService()
                .modifyPass(((BaseApplication) getApplication()).getToken(), oldPwd, newPwd, newPwd)
                .compose(this.<JsonCommon<Object>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<JsonCommon<Object>>() {
                    @Override
                    public void accept(JsonCommon<Object> objectJsonCommon) throws Exception {
                        if ("200".equals(objectJsonCommon.getCode())) {
                            toast("修改成功");
                            finish();
                        } else {
                            toast(objectJsonCommon.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        toast(R.string.net_error);
                    }
                });
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, EditPwdActivity.class);
        context.startActivity(starter);
    }
}
