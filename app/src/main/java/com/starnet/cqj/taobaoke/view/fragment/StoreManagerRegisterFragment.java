package com.starnet.cqj.taobaoke.view.fragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;
import com.starnet.cqj.taobaoke.utils.StoreManagerType;
import com.starnet.cqj.taobaoke.view.BaseApplication;
import com.starnet.cqj.taobaoke.view.widget.CityPicker;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
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

    public static final String KEY_IS_AREA = "is_area";
    @BindView(R.id.rg_store_manager_type)
    RadioGroup mRgStoreManagerType;
    @BindView(R.id.edt_name)
    EditText mEdtName;
    @BindView(R.id.edt_phone)
    EditText mEdtPhone;
    @BindView(R.id.edt_remark)
    EditText mEdtRemark;
    @BindView(R.id.edt_area)
    TextView mEdtArea;
    @BindView(R.id.ll_proxy_area)
    LinearLayout mLlProxyArea;
    @BindView(R.id.rb_gold)
    RadioButton mRbGold;
    @BindView(R.id.rb_silver)
    RadioButton mRbSilver;
    @BindView(R.id.rb_cuprum)
    RadioButton mRbCuprum;
    @BindView(R.id.tv_remark_title)
    TextView mTvRemarkTitle;
    @BindView(R.id.tv_register_remark)
    TextView mTvRegisterRemark;
    Unbinder unbinder;
    private PublishSubject<String> mDoneObservable = PublishSubject.create();
    private boolean mIsArea;
    private CityPicker mCityPicker;
    private String mProvince;
    private String mCity;
    private String mArea;

    public static StoreManagerRegisterFragment newInstance(boolean isArea) {

        Bundle args = new Bundle();
        args.putBoolean(KEY_IS_AREA, isArea);

        StoreManagerRegisterFragment fragment = new StoreManagerRegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    int getContentRes() {
        return R.layout.fragment_store_manager_register;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mIsArea = getArguments().getBoolean(KEY_IS_AREA);
        mCityPicker = new CityPicker(getActivity());
        mCityPicker.resultObservable()
                .compose(this.<CityPicker.GetCityResult>bindToLifecycle())
                .subscribe(new Consumer<CityPicker.GetCityResult>() {
                    @Override
                    public void accept(CityPicker.GetCityResult getCityResult) throws Exception {
                        mProvince = getCityResult.province;
                        mCity = getCityResult.city;
                        mArea = getCityResult.area;
                        mEdtArea.setText(mProvince + (TextUtils.isEmpty(mCity) ? "" : ",") + mCity + (TextUtils.isEmpty(mArea) ? "" : ",") + mArea);
                    }
                });
        mLlProxyArea.setVisibility(mIsArea ? View.VISIBLE : View.GONE);
        if (mIsArea) {
            mRbGold.setText("省份代理");
            mRbSilver.setText("城市代理");
            mRbCuprum.setText("区县代理");
            mTvRemarkTitle.setText("区域代理说明");
            mTvRegisterRemark.setText(R.string.area_register_remark);
        }
    }

    private void initEvent() {
        mRgStoreManagerType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_gold:
                        mCityPicker.showLevel(CityPicker.ShowLevel.PROVINCE);
                        break;
                    case R.id.rb_silver:
                        mCityPicker.showLevel(CityPicker.ShowLevel.CITY);
                        break;
                    case R.id.rb_cuprum:
                        mCityPicker.showLevel(CityPicker.ShowLevel.AREA);
                        break;
                    default:
                        break;
                }
            }
        });
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
        getApply(name, phone, remark, type)
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

    private Observable<JsonCommon<Object>> getApply(String name, String phone, String remark, int type) {
        if (mIsArea) {
            return RemoteDataSourceBase.INSTANCE.getAreaManagerService()
                    .apply(((BaseApplication) getActivity().getApplication()).getToken(), type, name, phone, remark, mProvince, mCity, mArea);
        }
        return RemoteDataSourceBase.INSTANCE.getStoreManagerService()
                .apply(((BaseApplication) getActivity().getApplication()).getToken(), type, name, phone, remark);
    }

    public Observable<String> doneObservable() {
        return mDoneObservable;
    }

    @OnClick(R.id.ll_proxy_area)
    public void onProxyViewClicked() {
        mCityPicker.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

    }

}
