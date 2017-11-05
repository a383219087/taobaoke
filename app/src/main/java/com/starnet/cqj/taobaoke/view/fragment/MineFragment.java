package com.starnet.cqj.taobaoke.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;

import butterknife.BindView;
import butterknife.OnClick;


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

    public MineFragment(){
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
    }

    @OnClick({R.id.ib_setting, R.id.iv_avatar,
            R.id.btn_ice, R.id.btn_recheck, R.id.btn_to_money,
            R.id.ll_order, R.id.ll_integral, R.id.ll_medal, R.id.ll_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_setting:
                break;
            case R.id.iv_avatar:
                break;
            case R.id.btn_ice:
                break;
            case R.id.btn_recheck:
                break;
            case R.id.btn_to_money:
                break;
            case R.id.ll_order:
                break;
            case R.id.ll_integral:
                break;
            case R.id.ll_medal:
                break;
            case R.id.ll_share:
                break;
        }
    }

}
