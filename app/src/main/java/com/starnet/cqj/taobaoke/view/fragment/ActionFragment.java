package com.starnet.cqj.taobaoke.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.Action;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;
import com.starnet.cqj.taobaoke.utils.RxBus;
import com.starnet.cqj.taobaoke.utils.event.ToHomePageEvent;
import com.starnet.cqj.taobaoke.view.activity.UserSignActivity;
import com.starnet.cqj.taobaoke.view.widget.SharePopupWindow;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ActionFragment extends BaseFragment {
    @BindView(R.id.title_back)
    ImageView mTitleBack;
    @BindView(R.id.title_name)
    TextView mTitleName;
    @BindView(R.id.tv_action_name)
    TextView mTvActionName;
    @BindView(R.id.tv_start_time)
    TextView mTvStartTime;
    @BindView(R.id.tv_end_time)
    TextView mTvEndTime;
    @BindView(R.id.tv_action_msg)
    TextView mTvActionMsg;

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
        getData();
    }

    private void getData() {
        RemoteDataSourceBase.INSTANCE.getActionService()
                .active()
                .compose(this.<JsonCommon<Action>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<JsonCommon<Action>>() {
                    @Override
                    public void accept(JsonCommon<Action> actionJsonCommon) throws Exception {
                        if ("200".equals(actionJsonCommon.getCode())) {
                            mTvActionName.setText(actionJsonCommon.getData().getName());
                            mTvStartTime.setText(actionJsonCommon.getData().getStime());
                            mTvEndTime.setText(actionJsonCommon.getData().getEtime());
                            mTvActionMsg.setText(actionJsonCommon.getData().getMsg());
                        } else {
                            toast(actionJsonCommon.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }

    @OnClick({R.id.ll_action_sign, R.id.action_share, R.id.action_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_action_sign:
                UserSignActivity.start(getActivity());
                break;
            case R.id.action_share:
                SharePopupWindow sharePopupWindow = new SharePopupWindow(getActivity());
                sharePopupWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.action_buy:
                RxBus.getInstance().send(new ToHomePageEvent());
                break;
        }
    }
}
