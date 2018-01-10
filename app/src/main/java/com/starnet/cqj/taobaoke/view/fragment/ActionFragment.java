package com.starnet.cqj.taobaoke.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.OtherAction;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;
import com.starnet.cqj.taobaoke.view.BaseApplication;
import com.starnet.cqj.taobaoke.view.activity.ActionWebActivity;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerBaseAdapter;
import com.starnet.cqj.taobaoke.view.adapter.viewholder.OtherActionHolder;
import com.starnet.cqj.taobaoke.view.widget.topview.ActionTopView;

import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ActionFragment extends BaseFragment {
    @BindView(R.id.title_back)
    ImageView mTitleBack;
    @BindView(R.id.title_name)
    TextView mTitleName;
    @BindView(R.id.rv_action)
    RecyclerView mRvAction;
    @BindView(R.id.sr_refresh)
    SwipeRefreshLayout mSrRefresh;

    private RecyclerBaseAdapter<OtherAction, OtherActionHolder> mAdapter;

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
        ActionTopView actionTopView = new ActionTopView(getActivity());
        mRvAction.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new RecyclerBaseAdapter<>(actionTopView, R.layout.item_other_action, OtherActionHolder.class);
        mRvAction.setAdapter(mAdapter);
        mSrRefresh.setColorSchemeResources(R.color.main_color);
        initEvent();
        getData();
    }

    private void initEvent() {
        mAdapter.itemClickObserve()
                .compose(this.<OtherAction>bindToLifecycle())
                .subscribe(new Consumer<OtherAction>() {
                    @Override
                    public void accept(OtherAction otherAction) throws Exception {
                        ActionWebActivity.start(getActivity(),otherAction.getUrl(),otherAction.getId());
                    }
                });
        mSrRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSrRefresh.setRefreshing(true);
                getData();
            }
        });
    }

    private void getData() {
        String token = ((BaseApplication) getActivity().getApplication()).getToken(false);
        if(TextUtils.isEmpty(token)){
            toast("请登录");
            mSrRefresh.setRefreshing(false);
            return;
        }
        RemoteDataSourceBase.INSTANCE.getActionService()
                .activeItem(token)
                .compose(this.<JsonCommon<List<OtherAction>>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<JsonCommon<List<OtherAction>>>() {
                    @Override
                    public void accept(JsonCommon<List<OtherAction>> listJsonCommon) throws Exception {
                        mSrRefresh.setRefreshing(false);
                        if("200".equals(listJsonCommon.getCode())){
                            mAdapter.setAll(listJsonCommon.getData());
                        }else{
                            toast(listJsonCommon.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mSrRefresh.setRefreshing(false);
                        throwable.printStackTrace();
                        toast(R.string.net_error);
                    }
                });
    }


}
