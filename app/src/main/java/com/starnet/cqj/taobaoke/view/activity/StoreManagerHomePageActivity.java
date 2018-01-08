package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.ApplyStatus;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;
import com.starnet.cqj.taobaoke.view.BaseApplication;
import com.starnet.cqj.taobaoke.view.fragment.StoreManagerCheckFragment;
import com.starnet.cqj.taobaoke.view.fragment.StoreManagerRegisterFragment;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 店长主页
 * Created by cqj on 2018/01/08.
 */

public class StoreManagerHomePageActivity extends BaseActivity {


    @Override
    protected int getContentView() {
        return R.layout.activity_store_manager;
    }

    @Override
    protected void init() {
        setTitleName(R.string.store_manager_home_page_title);
        RemoteDataSourceBase.INSTANCE.getStoreManagerService()
                .status(((BaseApplication) getApplication()).getToken())
                .compose(this.<JsonCommon<ApplyStatus>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<JsonCommon<ApplyStatus>>() {
                    @Override
                    public void accept(JsonCommon<ApplyStatus> applyStatusJsonCommon) throws Exception {
                        if ("200".equals(applyStatusJsonCommon.getCode())) {
                            ApplyStatus status = applyStatusJsonCommon.getData();
                            if("0".equals(status.getStatus())){
                                applyingFragment();
                            }else{
                                registerFragment();
                            }
                        }else{
                            registerFragment();
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

    private void registerFragment() {
        setTitleName(R.string.store_manager_register_title);
        StoreManagerRegisterFragment registerFragment = StoreManagerRegisterFragment.newInstance();
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
        setTitleName(R.string.store_manager_applying_title);
        StoreManagerCheckFragment storeManagerCheckFragment = StoreManagerCheckFragment.newInstance();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, storeManagerCheckFragment)
                .commit();
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, StoreManagerHomePageActivity.class);
        context.startActivity(starter);
    }

}
