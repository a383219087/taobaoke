package com.starnet.cqj.taobaoke.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
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

    private RxAppCompatActivity mActivity;

    public StoreManagerApplyDetailDialog(@NonNull Context context) {
        super(context,R.style.DialogActivity);
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_apply_detail, null, false);
        setContentView(contentView);
        ButterKnife.bind(this, contentView);
        mActivity = (RxAppCompatActivity) context;
        initData();
    }

    private void initData() {
        RemoteDataSourceBase.INSTANCE.getStoreManagerService()
                .get(((BaseApplication) mActivity.getApplication()).getToken())
                .compose(mActivity.<JsonCommon<StoreManagerApplyDetail>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JsonCommon<StoreManagerApplyDetail>>() {
                    @Override
                    public void accept(JsonCommon<StoreManagerApplyDetail> storeManagerApplyDetailJsonCommon) throws Exception {
                        if("200".equals(storeManagerApplyDetailJsonCommon.getCode())){
                            StoreManagerApplyDetail data = storeManagerApplyDetailJsonCommon.getData();
                            mTvName.setText(data.getContact());
                            mTvPhone.setText(data.getPhone());
                            mTvRemark.setText(data.getRemark());
                        }else{
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

    @OnClick(R.id.btn_done)
    public void onViewClicked() {
        dismiss();
    }
}
