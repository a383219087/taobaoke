package com.starnet.cqj.taobaoke.view.fragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.AgencyFee;
import com.starnet.cqj.taobaoke.model.AlipayRequest;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;
import com.starnet.cqj.taobaoke.utils.StoreManagerType;
import com.starnet.cqj.taobaoke.view.BaseApplication;
import com.starnet.cqj.taobaoke.view.widget.CityPicker;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
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
    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.tv_money)
    TextView mTvMoney;
    private PublishSubject<String> mDoneObservable = PublishSubject.create();
    private boolean mIsArea;
    private CityPicker mCityPicker;
    private String mProvince;
    private String mCity;
    private String mArea;
    private AgencyFee mAgencyFee;
    private boolean isSuccess;

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
            mTvType.setText("代理类型");
            mRbGold.setText("省份代理");
            mRbSilver.setText("城市代理");
            mRbCuprum.setText("区县代理");
            mTvRemarkTitle.setText("区域代理说明");
            mTvRegisterRemark.setText(R.string.area_register_remark);
            mCityPicker.showLevel(CityPicker.ShowLevel.PROVINCE);
        }
        initEvent();
        getPrice();
    }

    private void initEvent() {
        mRgStoreManagerType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_gold:
                        if (mIsArea) {
                            mCityPicker.showLevel(CityPicker.ShowLevel.PROVINCE);
                            setMoney(mAgencyFee == null ? "" : mAgencyFee.getProvince());
                        } else {
                            setMoney(mAgencyFee == null ? "" : mAgencyFee.getGold());
                        }

                        break;
                    case R.id.rb_silver:
                        if (mIsArea) {
                            mCityPicker.showLevel(CityPicker.ShowLevel.CITY);
                            setMoney(mAgencyFee == null ? "" : mAgencyFee.getCity());
                        } else {
                            setMoney(mAgencyFee == null ? "" : mAgencyFee.getSilver());
                        }
                        break;
                    case R.id.rb_cuprum:
                        if (mIsArea) {
                            mCityPicker.showLevel(CityPicker.ShowLevel.AREA);
                            setMoney(mAgencyFee == null ? "" : mAgencyFee.getRegion());
                        } else {
                            setMoney(mAgencyFee == null ? "" : mAgencyFee.getCopper());
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isSuccess) {
            mDoneObservable.onNext("");
        }
    }

    private void setMoney(String gold) {
        mTvMoney.setText(gold);
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
        if (TextUtils.isEmpty(remark)) {
            toast("请输入备注");
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
                .map(new Function<JsonCommon<AlipayRequest>, Pair<String,String>>() {
                    @Override
                    public Pair<String,String> apply(JsonCommon<AlipayRequest> alipayRequestJsonCommon) throws Exception {
                        if ("200".equals(alipayRequestJsonCommon.getCode())) {
                            PayTask alipay = new PayTask(getActivity());
                            Map<String, String> map = alipay.payV2(alipayRequestJsonCommon.getData().getAlipay(), true);
                            String resultStatus = map.get("resultStatus");
                            if(resultStatus.equals("9000")){
                                isSuccess =true;
                                return Pair.create(resultStatus,"支付成功");
                            }else{
                                return Pair.create(resultStatus,map.get("memo"));
                            }
                        } else {
                            return Pair.create("-1",alipayRequestJsonCommon.getMessage());
                        }
                    }
                })
                .compose(this.<Pair<String,String>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Pair<String,String>>() {
                    @Override
                    public void accept(Pair<String,String> result) throws Exception {
                        if(result.first.equals("9000")){
                            mDoneObservable.onNext(result.second);
                        }
                        toast(result.second);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        toast(R.string.net_error);
                    }
                });
    }

    private static final String TAG = "StoreManagerRegisterFra";

    private Observable<JsonCommon<AlipayRequest>> getApply(String name, String phone, String remark, int type) {
        if (mIsArea) {
            return RemoteDataSourceBase.INSTANCE.getAreaManagerService()
                    .orderApply(((BaseApplication) getActivity().getApplication()).getToken(), type, name, phone, remark, mTvMoney.getText().toString(), mProvince, mCity, mArea);
        }
        return RemoteDataSourceBase.INSTANCE.getStoreManagerService()
                .orderApply(((BaseApplication) getActivity().getApplication()).getToken(), type, name, phone, remark, mTvMoney.getText().toString());
    }

    private void getPrice() {
        RemoteDataSourceBase.INSTANCE.getStoreManagerService()
                .agencyFee(((BaseApplication) getActivity().getApplication()).getToken())
                .compose(this.<JsonCommon<AgencyFee>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<JsonCommon<AgencyFee>>() {
                    @Override
                    public void accept(JsonCommon<AgencyFee> objectJsonCommon) throws Exception {
                        if ("200".equals(objectJsonCommon.getCode())) {
                            mAgencyFee = objectJsonCommon.getData();
                            if (mIsArea) {
                                setMoney(mAgencyFee.getProvince());
                            } else {
                                setMoney(mAgencyFee.getGold());
                            }
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


    public Observable<String> doneObservable() {
        return mDoneObservable;
    }

    @OnClick(R.id.ll_proxy_area)
    public void onProxyViewClicked() {
        mCityPicker.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCityPicker != null) {
            mCityPicker.destroy();
        }
    }
}
