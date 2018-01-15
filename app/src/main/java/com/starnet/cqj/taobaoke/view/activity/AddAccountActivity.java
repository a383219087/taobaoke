package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.Account;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;
import com.starnet.cqj.taobaoke.view.BaseApplication;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/01/14.
 */

public class AddAccountActivity extends BaseActivity {

    public static final String KEY_ACCOUNT = "account";
    @BindView(R.id.tab)
    TabLayout mTab;
    @BindView(R.id.edt_alipay_name)
    EditText mEdtAlipayName;
    @BindView(R.id.edt_alipay_account)
    EditText mEdtAlipayAccount;
    @BindView(R.id.ll_alipay)
    LinearLayout mLlAlipay;
    @BindView(R.id.ll_choose_bank_type)
    LinearLayout mLlChooseBankType;
    @BindView(R.id.edt_bank_type)
    EditText mEdtBankType;
    @BindView(R.id.edt_name)
    EditText mEdtName;
    @BindView(R.id.edt_bank_account)
    EditText mEdtBankAccount;
    @BindView(R.id.ll_bank_card)
    LinearLayout mLlBankCard;
    private Account mAccount;
    private String mType = "1";

    @Override
    protected int getContentView() {
        return R.layout.activity_add_account;
    }

    @Override
    protected void init() {
        TabLayout.Tab alipayTab = mTab.newTab();
        alipayTab.setText("支付宝");
        alipayTab.setTag("1");
        mTab.addTab(alipayTab);

        TabLayout.Tab bankTab = mTab.newTab();
        bankTab.setText("银行卡账号");
        bankTab.setTag("2");
        mTab.addTab(bankTab);

        Serializable value = getIntent().getSerializableExtra(KEY_ACCOUNT);
        if (value != null) {
            mAccount = (Account) value;
            if (mAccount.getType().equals("1")) {
                mTab.getTabAt(0).select();
                mEdtAlipayName.setText(mAccount.getName());
                mEdtAlipayAccount.setText(mAccount.getUserName());
            } else {
                mTab.getTabAt(1).select();
                mEdtName.setText(mAccount.getName());
                mEdtBankAccount.setText(mAccount.getUserName());
            }
        }
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if ("1".equals(tab.getTag())) {
                    mType = "1";
                    mLlAlipay.setVisibility(View.VISIBLE);
                    mLlBankCard.setVisibility(View.GONE);
                } else {
                    mType = "2";
                    mLlAlipay.setVisibility(View.GONE);
                    mLlBankCard.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @OnClick(R.id.btn_save)
    public void onViewClicked() {
        String name ="";
        String account ="";
        if("1".equals(mType)){
            name = mEdtAlipayName.getText().toString();
            account = mEdtAlipayAccount.getText().toString();
            if(TextUtils.isEmpty(name)){
                toast("请输入姓名");
                return;
            }
            if(account.length()<11){
                toast("请输入正确的账号");
                return;
            }
        }else{
            name = mEdtName.getText().toString();
            account = mEdtBankAccount.getText().toString();
            if(TextUtils.isEmpty(name)){
                toast("请输入姓名");
                return;
            }
            if(account.length()<16){
                toast("请输入正确的账号");
                return;
            }
        }
        RemoteDataSourceBase.INSTANCE.getUserService()
                .withdrawAccount(((BaseApplication) getApplication()).getToken(),name,account,mType,mEdtBankType.getText().toString())
                .compose(this.<JsonCommon<Object>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<JsonCommon<Object>>() {
                    @Override
                    public void accept(JsonCommon<Object> objectJsonCommon) throws Exception {
                        if("200".equals(objectJsonCommon.getCode())){
                            toast("添加成功");
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

    public static void start(Context context) {
        Intent starter = new Intent(context, AddAccountActivity.class);
        context.startActivity(starter);
    }

    public static void start(Context context, Account account) {
        Intent starter = new Intent(context, AddAccountActivity.class);
        starter.putExtra(KEY_ACCOUNT, account);
        context.startActivity(starter);
    }
}
