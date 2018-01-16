package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.Account;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;
import com.starnet.cqj.taobaoke.view.BaseApplication;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerBaseAdapter;
import com.starnet.cqj.taobaoke.view.adapter.viewholder.AccountHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/01/14.
 */

public class AccountListActivity extends BaseActivity {

    public static final String KEY_UID = "uid";
    @BindView(R.id.title_rightimage)
    ImageButton mTitleRightimage;
    @BindView(R.id.rv_account)
    RecyclerView mRvAccount;
    private RecyclerBaseAdapter<Account, AccountHolder> mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_account_list;
    }

    @Override
    protected void init() {
        setTitleName(R.string.account_list_title);
        mTitleRightimage.setVisibility(View.VISIBLE);
        mTitleRightimage.setImageResource(R.drawable.add_icon);
        mAdapter = new RecyclerBaseAdapter<>(R.layout.item_account, AccountHolder.class);
        mRvAccount.setLayoutManager(new LinearLayoutManager(this));
        mRvAccount.setAdapter(mAdapter);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mAdapter.itemClickObserve()
                .compose(this.<Account>bindToLifecycle())
                .subscribe(new Consumer<Account>() {
                    @Override
                    public void accept(Account account) throws Exception {
                        getData();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        String uid = getIntent().getStringExtra(KEY_UID);
        RemoteDataSourceBase.INSTANCE.getUserService()
                .getAccount(((BaseApplication) getApplication()).getToken(), uid)
                .compose(this.<JsonCommon<List<Account>>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<JsonCommon<List<Account>>>() {
                    @Override
                    public void accept(JsonCommon<List<Account>> listJsonCommon) throws Exception {
                        if ("200".equals(listJsonCommon.getCode())) {
                            mAdapter.setAll(listJsonCommon.getData());
                        } else {
                            toast(listJsonCommon.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }

    @OnClick(R.id.title_rightimage)
    public void onViewClicked() {
    }

    public static void start(Context context, String uid) {
        Intent starter = new Intent(context, AccountListActivity.class);
        starter.putExtra(KEY_UID, uid);
        context.startActivity(starter);
    }
}
