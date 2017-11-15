package com.starnet.cqj.taobaoke.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.view.activity.UserCheckActivity;
import com.starnet.cqj.taobaoke.view.widget.SharePopupWindow;

import butterknife.BindView;
import butterknife.OnClick;

public class ActionFragment extends BaseFragment {
    @BindView(R.id.title_back)
    ImageView mTitleBack;
    @BindView(R.id.title_name)
    TextView mTitleName;

    public ActionFragment() {
        // Required empty public constructor
    }

    public static ActionFragment newInstance() {
        ActionFragment fragment = new ActionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    int getContentRes() {
        return R.layout.fragment_action;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTitleBack.setVisibility(View.GONE);
        mTitleName.setText(R.string.action_title);
    }


    @OnClick({R.id.ll_action_sign, R.id.action_share, R.id.action_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_action_sign:
                UserCheckActivity.start(getActivity());
                break;
            case R.id.action_share:
                SharePopupWindow sharePopupWindow = new SharePopupWindow(getActivity());
                sharePopupWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.action_buy:
                break;
        }
    }
}
