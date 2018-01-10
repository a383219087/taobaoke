package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.Member;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;
import com.starnet.cqj.taobaoke.view.BaseApplication;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerBaseAdapter;
import com.starnet.cqj.taobaoke.view.adapter.viewholder.MemberHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 会员列表
 * Created by cqj on 2018/01/09.
 */

public class MemberListActivity extends BaseActivity {

    public static final String KEY_UID = "uid";
    public static final String KEY_NAME = "name";

    @BindView(R.id.edt_search)
    EditText mEdtSearch;
    @BindView(R.id.rv_member)
    RecyclerView mRvMember;

    private String mUid;
    private RecyclerBaseAdapter<Member, MemberHolder> mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_member_list;
    }

    @Override
    protected void init() {
        mUid = getIntent().getStringExtra(KEY_UID);
        String name = getIntent().getStringExtra(KEY_NAME);
        if (TextUtils.isEmpty(name)) {
            setTitleName(R.string.my_member_title);
        } else {
            setTitleName(getString(R.string.others_member_title, name));
        }
        mAdapter = new RecyclerBaseAdapter<>(R.layout.item_member, MemberHolder.class);
        mRvMember.setLayoutManager(new LinearLayoutManager(this));
        mRvMember.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        mRvMember.setAdapter(mAdapter);
        getData();
    }

    private void getData() {
        RemoteDataSourceBase.INSTANCE.getMemberService()
                .get(((BaseApplication) getApplication()).getToken(), TextUtils.isEmpty(mUid) ? "" : mUid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(this.<JsonCommon<List<Member>>>bindToLifecycle())
                .subscribe(new Consumer<JsonCommon<List<Member>>>() {
                    @Override
                    public void accept(JsonCommon<List<Member>> listJsonCommon) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        toast(R.string.net_error);
                    }
                });
    }

    @OnClick(R.id.btn_search)
    public void onViewClicked() {
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, MemberListActivity.class);
        context.startActivity(starter);
    }

    public static void start(Context context, String uid, String name) {
        Intent starter = new Intent(context, MemberListActivity.class);
        starter.putExtra(KEY_UID, uid);
        starter.putExtra(KEY_NAME, name);
        context.startActivity(starter);
    }

}
