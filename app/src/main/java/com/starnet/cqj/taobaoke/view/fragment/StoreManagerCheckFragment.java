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

    public static StoreManagerCheckFragment newInstance() {
        
        Bundle args = new Bundle();
        
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
        StoreManagerApplyDetailDialog detailDialog = new StoreManagerApplyDetailDialog(getActivity());
        detailDialog.show();
    }
}
