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
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 店长注册
 * Created by cqj on 2018/01/08.
 */

public class StoreManagerRegisterActivity extends BaseActivity {


    public static final String KEY_TYPE = "type";
    public static final String KEY_IS_AREA = "is_area";
    private boolean mIsArea;
    private Disposable mDisposable;

    @Override
    protected int getContentView() {
        return R.layout.activity_store_manager_register;
    }

    @Override
    protected void init() {
        String type = getIntent().getStringExtra(KEY_TYPE);
        String isArea = getIntent().getStringExtra(KEY_IS_AREA);
        mIsArea = isArea.equals("1");
        if (mIsArea) {
            setTitleName("代理查询");
            getAreaApplyStatus();
        } else {
            setTitleName(R.string.store_manager_register_title);
            if ("0".equals(type)) {
                applyingFragment();
            } else {
                registerFragment();
            }
        }
    }

    private void getAreaApplyStatus() {
        RemoteDataSourceBase.INSTANCE.getAreaManagerService()
                .status(((BaseApplication) getApplication()).getToken())
                .compose(this.<JsonCommon<ApplyStatus>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JsonCommon<ApplyStatus>>() {
                    @Override
                    public void accept(JsonCommon<ApplyStatus> applyStatusJsonCommon) throws Exception {
                        if ("200".equals(applyStatusJsonCommon.getCode())) {
                            if ("0".equals(applyStatusJsonCommon.getData().getStatus())) {
                                setTitleName(R.string.register_area_title);
                                applyingFragment();
                            } else if ("1".equals(applyStatusJsonCommon.getData().getStatus())) {
                                AreaManagerHomepageActivity.start(StoreManagerRegisterActivity.this);
                                finish();
                            } else {
                                setTitleName(R.string.register_area_title);
                                registerFragment();
                            }
                        } else {
                            setTitleName(R.string.register_area_title);
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
        StoreManagerRegisterFragment registerFragment = StoreManagerRegisterFragment.newInstance(mIsArea);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, registerFragment)
                .commit();
        registerFragment.doneObservable()
                .doOnSubscribe(new Consumer<Disposable>() {

                    @Override
                    public void accept(Disposable disposable) throws Exception {

                        mDisposable = disposable;
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        applyingFragment();
                    }
                });
    }

    private void applyingFragment() {
        StoreManagerCheckFragment storeManagerCheckFragment = StoreManagerCheckFragment.newInstance(mIsArea);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, storeManagerCheckFragment)
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

    public static void start(Context context, String status, String isArea) {
        Intent starter = new Intent(context, StoreManagerRegisterActivity.class);
        starter.putExtra(KEY_TYPE, status);
        starter.putExtra(KEY_IS_AREA, isArea);
        context.startActivity(starter);
    }

}
