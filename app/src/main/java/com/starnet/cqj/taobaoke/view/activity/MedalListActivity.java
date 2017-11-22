package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.Medal;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;
import com.starnet.cqj.taobaoke.view.BaseApplication;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerBaseAdapter;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerSpaceDecoration;
import com.starnet.cqj.taobaoke.view.adapter.viewholder.MedalItemHolder;

import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class MedalListActivity extends BaseActivity {
    @BindView(R.id.tabs)
    TabLayout mTabs;
    @BindView(R.id.rv_medal)
    RecyclerView mRvMedal;
    private RecyclerBaseAdapter<Medal, MedalItemHolder> mAdapter;
    private int mType;

    @Override
    protected int getContentView() {
        return R.layout.activity_medal_list;
    }

    @Override
    protected void init() {
        setTitleName(R.string.my_medal_title);
        mTabs.addTab(mTabs.newTab().setText("普通勋章"));
        mTabs.addTab(mTabs.newTab().setText("特别勋章"));
        mAdapter = new RecyclerBaseAdapter<>(R.layout.item_medal, MedalItemHolder.class);
        mRvMedal.setLayoutManager(new GridLayoutManager(this, 3));
        mRvMedal.addItemDecoration(new RecyclerSpaceDecoration(getResources().getDimensionPixelOffset(R.dimen.product_item_padding)));
        mRvMedal.setAdapter(mAdapter);
        getData();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (mTabs.getTabAt(0) == tab) {
                    mType = 0;
                } else {
                    mType = 1;
                }
                getData();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mAdapter.itemClickObserve()
                .compose(this.<Medal>bindToLifecycle())
                .subscribe(new Consumer<Medal>() {
                    @Override
                    public void accept(Medal medal) throws Exception {
                        MedalDetailActivity.start(MedalListActivity.this, medal);
                    }
                });
    }


    private void getData() {
        RemoteDataSourceBase.INSTANCE.getUserService()
                .getMedals(((BaseApplication) getApplication()).getToken(), mType)
                .compose(this.<JsonCommon<List<Medal>>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<JsonCommon<List<Medal>>>() {
                    @Override
                    public void accept(JsonCommon<List<Medal>> objectJsonCommon) throws Exception {
                        if ("200".equals(objectJsonCommon.getCode())) {
                            mAdapter.setAll(objectJsonCommon.getData());
                        } else {
                            toast(objectJsonCommon.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, MedalListActivity.class);
        context.startActivity(starter);
    }

}
