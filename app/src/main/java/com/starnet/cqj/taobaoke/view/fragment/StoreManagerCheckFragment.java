package com.starnet.cqj.taobaoke.view.fragment;

import android.os.Bundle;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.view.widget.StoreManagerApplyDetailDialog;

import butterknife.OnClick;

/**
 * 店长审核中界面
 * Created by Administrator on 2018/01/08.
 */

public class StoreManagerCheckFragment extends BaseFragment {

    public static final String KEY_IS_AREA = "is_area";
    private boolean mIsArea;

    public static StoreManagerCheckFragment newInstance(boolean isArea) {

        Bundle args = new Bundle();
        args.putBoolean(KEY_IS_AREA, isArea);
        StoreManagerCheckFragment fragment = new StoreManagerCheckFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    int getContentRes() {
        return R.layout.fragment_store_manager_check;
    }


    @OnClick(R.id.btn_apply_info)
    public void onViewClicked() {
        mIsArea = getArguments().getBoolean(KEY_IS_AREA, false);
        StoreManagerApplyDetailDialog detailDialog = new StoreManagerApplyDetailDialog(getActivity(),mIsArea);
        detailDialog.show();
    }
}
