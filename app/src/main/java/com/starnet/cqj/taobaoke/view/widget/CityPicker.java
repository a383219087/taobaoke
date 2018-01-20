package com.starnet.cqj.taobaoke.view.widget;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.ResultWrapper;
import com.starnet.cqj.taobaoke.model.city.CityBean;
import com.starnet.cqj.taobaoke.model.city.ICityData;
import com.starnet.cqj.taobaoke.model.city.ProvinceBean;
import com.starnet.cqj.taobaoke.remote.CityData;
import com.starnet.cqj.taobaoke.utils.AppUtils;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerBaseAdapter;
import com.starnet.cqj.taobaoke.view.adapter.viewholder.PickerDataHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Administrator on 2017/12/26.
 */

public class CityPicker extends PopupWindow {

    @BindView(R.id.rv_province)
    RecyclerView mRvProvince;
    @BindView(R.id.rv_city)
    RecyclerView mRvCity;
    @BindView(R.id.rv_area)
    RecyclerView mRvArea;
    private Context mContext;
    private CompositeDisposable mDisposable = new CompositeDisposable();
    private final RecyclerBaseAdapter<ICityData, PickerDataHolder> mProvinceAdapter;
    private final RecyclerBaseAdapter<ICityData, PickerDataHolder> mCityAdapter;
    private final RecyclerBaseAdapter<ICityData, PickerDataHolder> mAreaAdapter;

    private String mProvince;
    private String mCity;
    private String mArea;

    public enum ShowLevel {
        PROVINCE,
        CITY,
        AREA
    }

    public CityPicker(Context context) {
        super(context);
        mContext = context;
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(500);
        setFocusable(true);
        AppUtils.hideInput((Activity) context);
        View contentView = LayoutInflater.from(context).inflate(R.layout.popup_city_picker, null, false);
        setContentView(contentView);
        ButterKnife.bind(this, contentView);
        mRvProvince.setLayoutManager(new LinearLayoutManager(context));
        mProvinceAdapter = new RecyclerBaseAdapter<>(R.layout.item_picker, PickerDataHolder.class);
        mRvProvince.setAdapter(mProvinceAdapter);
        mRvCity.setLayoutManager(new LinearLayoutManager(context));
        mCityAdapter = new RecyclerBaseAdapter<>(R.layout.item_picker, PickerDataHolder.class);
        mRvCity.setAdapter(mCityAdapter);
        mRvArea.setLayoutManager(new LinearLayoutManager(context));
        mAreaAdapter = new RecyclerBaseAdapter<>(R.layout.item_picker, PickerDataHolder.class);
        mRvArea.setAdapter(mAreaAdapter);
        getData();
        initEvent();
    }

    public void showLevel(ShowLevel level) {
        switch (level) {
            case PROVINCE:
                mRvCity.setVisibility(View.GONE);
                mRvArea.setVisibility(View.GONE);
                mRvProvince.setVisibility(View.VISIBLE);
                break;
            case CITY:
                mRvProvince.setVisibility(View.VISIBLE);
                mRvCity.setVisibility(View.VISIBLE);
                mRvArea.setVisibility(View.GONE);
                break;
            case AREA:
                mRvProvince.setVisibility(View.VISIBLE);
                mRvCity.setVisibility(View.VISIBLE);
                mRvArea.setVisibility(View.VISIBLE);
            default:
                break;
        }
    }

    private void initEvent() {
        mProvinceAdapter.itemClickObserve()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mDisposable.add(disposable);
                    }
                })
                .subscribe(new Consumer<ICityData>() {
                    @Override
                    public void accept(ICityData iCityData) throws Exception {
                        mProvince = iCityData.getName();
                        mCity = "";
                        mAreaAdapter.removeAll();
                        getCityData(iCityData, mCityAdapter);
                    }
                });
        mCityAdapter.itemClickObserve()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mDisposable.add(disposable);
                    }
                })
                .subscribe(new Consumer<ICityData>() {
                    @Override
                    public void accept(ICityData iCityData) throws Exception {
                        mCity = iCityData.getName();
                        mArea = "";
                        getCityData(iCityData, mAreaAdapter);
                    }
                });
        mAreaAdapter.itemClickObserve()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mDisposable.add(disposable);
                    }
                })
                .subscribe(new Consumer<ICityData>() {
                    @Override
                    public void accept(ICityData iCityData) throws Exception {
                        mArea = iCityData.getName();
                    }
                });
    }

    private void getCityData(ICityData iCityData, final RecyclerBaseAdapter<ICityData, PickerDataHolder> adapter) {
        adapter.removeAll();
        CityData.getCity(iCityData.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonCommon<ResultWrapper<CityBean>>>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                        mDisposable.add(disposable);
                    }

                    @Override
                    public void onNext(JsonCommon<ResultWrapper<CityBean>> resultWrapperJsonCommon) {
                        if ("200".equals(resultWrapperJsonCommon.getCode())) {
                            List<CityBean> provinceBeen = resultWrapperJsonCommon.getData().getList();
                            for (int i = 0; i < provinceBeen.size(); i++) {
                                adapter.add(provinceBeen.get(i));
                            }
                        } else {
                            Toast.makeText(mContext, resultWrapperJsonCommon.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void getData() {
        CityData.getProvince()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonCommon<ResultWrapper<ProvinceBean>>>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                        mDisposable.add(disposable);
                    }

                    @Override
                    public void onNext(JsonCommon<ResultWrapper<ProvinceBean>> resultWrapperJsonCommon) {
                        if ("200".equals(resultWrapperJsonCommon.getCode())) {
                            List<ProvinceBean> provinceBeen = resultWrapperJsonCommon.getData().getList();
                            for (int i = 0; i < provinceBeen.size(); i++) {
                                mProvinceAdapter.add(provinceBeen.get(i));
                            }
                        } else {
                            Toast.makeText(mContext, resultWrapperJsonCommon.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private PublishSubject<GetCityResult> resultSubject = PublishSubject.create();

    @OnClick({R.id.btn_cancel, R.id.btn_done})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.btn_done:
                dismiss();
//                resultSubject.onNext(mProvince+" "+mCity+" "+mArea);
                resultSubject.onNext(new GetCityResult(mProvince, mCity, mArea));
                break;
        }
    }

    public Observable<GetCityResult> resultObservable() {
        return resultSubject;
    }

    public void destroy() {
        mDisposable.dispose();
    }

    public class GetCityResult {
        public String province;
        public String city;
        public String area;

        GetCityResult(String province, String city, String area) {
            this.province = TextUtils.isEmpty(province) ? "" : province;
            this.city = TextUtils.isEmpty(city) ? "" : city;
            this.area = TextUtils.isEmpty(area) ? "" : area;
        }
    }
}
