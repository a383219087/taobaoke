package com.starnet.cqj.taobaoke.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;
import com.starnet.cqj.taobaoke.utils.StoreManagerType;
import com.starnet.cqj.taobaoke.view.BaseApplication;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * 申请成为店长
 * Created by cqj on 2018/01/08.
 */

public class StoreManagerRegisterFragment extends BaseFragment {

    @BindView(R.id.rg_store_manager_type)
    RadioGroup mRgStoreManagerType;
    @BindView(R.id.edt_name)
    EditText mEdtName;
    @BindView(R.id.edt_phone)
    EditText mEdtPhone;
    @BindView(R.id.edt_remark)
    EditText mEdtRemark;
    private PublishSubject<String> mDoneObservable = PublishSubject.create();

    public static StoreManagerRegisterFragment newInstance() {
        
        Bundle args = new Bundle();
        
        StoreManagerRegisterFragment fragment = new StoreManagerRegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    int getContentRes() {
        return R.layout.fragment_store_manager_register;
    }

    @OnClick(R.id.btn_register)
    public void onViewClicked() {
        String name = mEdtName.getText().toString();
        String phone = mEdtPhone.getText().toString();
        String remark = mEdtRemark.getText().toString();
        if (TextUtils.isEmpty(name)) {
            toast("请输入姓名");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            toast("请输入电话");
            return;
        }
        int type = 1;
        switch (mRgStoreManagerType.getCheckedRadioButtonId()) {
            case R.id.rb_gold:
                type = StoreManagerType.GOLD.getValue();
                break;
            case R.id.rb_silver:
                type = StoreManagerType.SILVER.getValue();
                break;
            case R.id.rb_cuprum:
                type = StoreManagerType.CUPRUM.getValue();
                break;
            default:
                break;
        }
        RemoteDataSourceBase.INSTANCE.getStoreManagerService()
                .apply(((BaseApplication) getActivity().getApplication()).getToken(), type, name, phone, remark)
                .compose(this.<JsonCommon<Object>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<JsonCommon<Object>>() {
                    @Override
                    public void accept(JsonCommon<Object> objectJsonCommon) throws Exception {
                        if ("200".equals(objectJsonCommon.getCode())) {
                            String s = "提交成功";
                            toast(s);
                            mDoneObservable.onNext(s);
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

    public Observable<String> doneObservable(){
        return mDoneObservable;
    }
}
