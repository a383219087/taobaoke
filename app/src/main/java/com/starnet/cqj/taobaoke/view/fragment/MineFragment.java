package com.starnet.cqj.taobaoke.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.User;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;
import com.starnet.cqj.taobaoke.view.BaseApplication;
import com.starnet.cqj.taobaoke.view.activity.IceIntegralDetailActivity;
import com.starnet.cqj.taobaoke.view.activity.IntegralStoreActivity;
import com.starnet.cqj.taobaoke.view.activity.OrderListActivity;
import com.starnet.cqj.taobaoke.view.activity.PersonActivity;
import com.starnet.cqj.taobaoke.view.activity.ReCheckIntegralDetailActivity;
import com.starnet.cqj.taobaoke.view.activity.StoreManagerHomePageActivity;
import com.starnet.cqj.taobaoke.view.activity.UseIntegralDetailActivity;
import com.starnet.cqj.taobaoke.view.activity.WithdrawalsActivity;
import com.starnet.cqj.taobaoke.view.widget.SharePopupWindow;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class MineFragment extends BaseFragment {

    @BindView(R.id.iv_avatar)
    ImageView mIvAvatar;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.cv_one)
    CardView mCvOne;
    @BindView(R.id.cv_two)
    CardView mCvTwo;
    @BindView(R.id.cv_three)
    CardView mCvThree;
    @BindView(R.id.tv_ice)
    TextView mTvIce;
    @BindView(R.id.tv_recheck)
    TextView mTvRecheck;
    @BindView(R.id.tv_to_money)
    TextView mTvToMoney;
    private User mUser;
    private BaseApplication mApplication;

    public MineFragment() {
        //empty
    }

    public static MineFragment newInstance() {

        Bundle args = new Bundle();

        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    int getContentRes() {
        return R.layout.fragment_mine;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCvOne.setCardElevation(8);
        mCvTwo.setCardElevation(8);
        mCvThree.setCardElevation(8);
        mApplication = (BaseApplication) getActivity().getApplication();
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        if (!TextUtils.isEmpty(mApplication.getToken(false))) {
            getData();
        } else {
            mTvToMoney.setText("0");
            mTvPhone.setText("");
            mTvName.setText("请登录");
            mTvIce.setText("0");
            mTvRecheck.setText("0");
            mIvAvatar.setImageResource(R.drawable.default_avatar);
        }
    }

    @OnClick({R.id.ib_setting, R.id.iv_avatar,
            R.id.btn_ice, R.id.btn_recheck, R.id.btn_to_money,
            R.id.ll_order, R.id.ll_integral, R.id.ll_medal, R.id.ll_share,
            R.id.ll_bind_cncbk})
    public void onViewClicked(View view) {
        if (TextUtils.isEmpty(mApplication.getToken())) {
            return;
        }
        switch (view.getId()) {
            case R.id.ib_setting:
                if (mUser == null) {
                    return;
                }
                PersonActivity.start(getActivity(), mUser);
                break;
            case R.id.btn_ice:
                IceIntegralDetailActivity.start(getActivity());
                break;
            case R.id.btn_recheck:
                ReCheckIntegralDetailActivity.start(getActivity());
                break;
            case R.id.btn_to_money:
                UseIntegralDetailActivity.start(getActivity());
                break;
            case R.id.ll_order:
                OrderListActivity.start(getActivity());
                break;
            case R.id.ll_integral:
                IntegralStoreActivity.start(getActivity());
                break;
            case R.id.ll_medal:
                StoreManagerHomePageActivity.start(getActivity());
                break;
            case R.id.ll_share:
                SharePopupWindow sharePopupWindow = new SharePopupWindow(getActivity());
                sharePopupWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.ll_bind_cncbk:
                WithdrawalsActivity.start(getActivity());
                break;
        }
    }

    private void getData() {
        String token = mApplication.getToken();
        RemoteDataSourceBase.INSTANCE.getUserService()
                .person(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<JsonCommon<User>>bindToLifecycle())
                .subscribe(new Consumer<JsonCommon<User>>() {
                    @Override
                    public void accept(JsonCommon<User> userJsonCommon) throws Exception {
                        if ("200".equals(userJsonCommon.getCode())) {
                            mUser = userJsonCommon.getData();
                            mTvName.setText(mUser.getNickname());
                            mTvPhone.setText(mUser.getMobile());
                            mTvToMoney.setText(String.valueOf(mUser.getCredit1()));
                            mTvIce.setText(String.valueOf(mUser.getCredit2()));
                            mTvRecheck.setText(String.valueOf(mUser.getCredit3()));
                            if (!TextUtils.isEmpty(mUser.getAvatar())) {
                                Glide.with(getActivity())
                                        .load(mUser.getAvatar())
                                        .into(mIvAvatar);
                            }
                        } else {
                            Toast.makeText(getActivity(), userJsonCommon.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }

}
