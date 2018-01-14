package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.view.fragment.StoreManagerCheckFragment;
import com.starnet.cqj.taobaoke.view.fragment.StoreManagerRegisterFragment;

import io.reactivex.functions.Consumer;

/**
 * 店长注册
 * Created by cqj on 2018/01/08.
 */

public class StoreManagerRegisterActivity extends BaseActivity {


    public static final String KEY_TYPE = "type";
    public static final String KEY_IS_AREA = "is_area";
    private boolean mIsArea;

    @Override
    protected int getContentView() {
        return R.layout.activity_store_manager_register;
    }

    @Override
    protected void init() {
        String type = getIntent().getStringExtra(KEY_TYPE);
        String isArea = getIntent().getStringExtra(KEY_IS_AREA);
        mIsArea = isArea.equals("1");
        if (mIsArea){
            setTitleName(R.string.register_area_title);
        }else {
            setTitleName(R.string.store_manager_register_title);
        }
        if("0".equals(type)){
            applyingFragment();
        }else{
            registerFragment();
        }
    }

    private void registerFragment() {
        StoreManagerRegisterFragment registerFragment = StoreManagerRegisterFragment.newInstance(mIsArea);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, registerFragment)
                .commit();
        registerFragment.doneObservable()
                .compose(this.<String>bindToLifecycle())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        applyingFragment();
                    }
                });
    }

    private void applyingFragment() {
        StoreManagerCheckFragment storeManagerCheckFragment = StoreManagerCheckFragment.newInstance();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, storeManagerCheckFragment)
                .commit();
    }

    public static void start(Context context,String status,String isArea) {
        Intent starter = new Intent(context, StoreManagerRegisterActivity.class);
        starter.putExtra(KEY_TYPE,status);
        starter.putExtra(KEY_IS_AREA,isArea);
        context.startActivity(starter);
    }

}
