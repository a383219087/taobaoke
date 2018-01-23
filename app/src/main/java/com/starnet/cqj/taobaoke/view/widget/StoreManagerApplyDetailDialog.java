package com.starnet.cqj.taobaoke.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.StoreManagerApplyDetail;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;
import com.starnet.cqj.taobaoke.view.BaseApplication;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/01/08.
 */

public class StoreManagerApplyDetailDialog extends Dialog {


    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.tv_remark)
    TextView mTvRemark;
    @BindView(R.id.tv_type_tip)
    TextView mTvTypeTip;
    @BindView(R.id.tv_area)
    TextView mTvArea;
    @BindView(R.id.ll_area)
    LinearLayout mLlArea;

    private RxAppCompatActivity mActivity;
    private boolean mIsArea;

    public StoreManagerApplyDetailDialog(@NonNull Context context, boolean isArea) {
        super(context, R.style.DialogActivity);
        mIsArea = isArea;
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_apply_detail, null, false);
        setContentView(contentView);
        ButterKnife.bind(this, contentView);
        mActivity = (RxAppCompatActivity) context;
        if (mIsArea) {
            mTvTypeTip.setText("代理类型：");
            mLlArea.setVisibility(View.VISIBLE);
        }
        initData();
    }

    private void initData() {
        getApplyDetail()
                .compose(mActivity.<JsonCommon<StoreManagerApplyDetail>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JsonCommon<StoreManagerApplyDetail>>() {
                    @Override
                    public void accept(JsonCommon<StoreManagerApplyDetail> storeManagerApplyDetailJsonCommon) throws Exception {
                        if ("200".equals(storeManagerApplyDetailJsonCommon.getCode())) {
                            StoreManagerApplyDetail data = storeManagerApplyDetailJsonCommon.getData();
                            mTvName.setText(data.getContact());
                            mTvPhone.setText(data.getPhone());
                            mTvRemark.setText(data.getRemark());
                            String province = data.getProvince();
                            if (!TextUtils.isEmpty(province)) {
                                String city = data.getCity();
                                String area = data.getArea();
                                mTvArea.setText(province + (TextUtils.isEmpty(city) ? "" : "," + city) + (TextUtils.isEmpty(area) ? "" : "," + area));
                            }
                            String shopType = getShopTypeText(mIsArea ? data.getAreaType() : data.getShopType());
                            mTvType.setText(shopType);
                        } else {
                            Toast.makeText(mActivity, storeManagerApplyDetailJsonCommon.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        Toast.makeText(mActivity, R.string.net_error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private Observable<JsonCommon<StoreManagerApplyDetail>> getApplyDetail() {
        if (mIsArea) {
            return RemoteDataSourceBase.INSTANCE.getAreaManagerService()
                    .get(((BaseApplication) mActivity.getApplication()).getToken());
        } else {
            return RemoteDataSourceBase.INSTANCE.getStoreManagerService()
                    .get(((BaseApplication) mActivity.getApplication()).getToken());
        }
    }

    private String getShopTypeText(String shopType) {
        String text = shopType;
        try {
            int type = Integer.parseInt(shopType);
            switch (type) {
                case 1:
                    text = mIsArea ? "省份代理" : "金牌店长";
                    break;
                case 2:
                    text = mIsArea ? "城市代理" : "银牌店长";
                    break;
                case 3:
                    text = mIsArea ? "区域代理" : "铜牌店长";
                    break;

            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return text;
    }

    @OnClick(R.id.btn_done)
    public void onViewClicked() {
        dismiss();
    }
}
