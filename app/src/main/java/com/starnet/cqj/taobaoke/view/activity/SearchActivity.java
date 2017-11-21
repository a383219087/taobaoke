package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.Product;
import com.starnet.cqj.taobaoke.presenter.ISearchPresenter;
import com.starnet.cqj.taobaoke.presenter.impl.SearchPresenterImpl;
import com.starnet.cqj.taobaoke.remote.Constant;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerBaseAdapter;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerItemDecoration;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerSpaceDecoration;
import com.starnet.cqj.taobaoke.view.adapter.viewholder.ProductHolder;
import com.starnet.cqj.taobaoke.view.adapter.viewholder.SearchHistoryHolder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;


public class SearchActivity extends BaseActivity implements ISearchPresenter.IView {
    public static final String KEY_SEARCH_HISTORY = "search_history";
    @BindView(R.id.edt_search)
    EditText mEdtSearch;
    @BindView(R.id.rv_search_result)
    RecyclerView mRvSearchResult;
    @BindView(R.id.ll_empty)
    LinearLayout mLlEmpty;
    @BindView(R.id.rv_search_history)
    RecyclerView mRvSearchHistory;
    @BindView(R.id.ll_history)
    LinearLayout mLlHistory;
    private ISearchPresenter mPresenter;
    private RecyclerBaseAdapter<Product, ProductHolder> mAdapter;
    private SharedPreferences mPreference;
    private RecyclerBaseAdapter<String, SearchHistoryHolder> mHistoryAdapter;
    private Set<String> mSet;
    private String mSearch;

    @Override
    protected int getContentView() {
        return R.layout.activity_search;
    }

    @Override
    protected void init() {
        initHistoryList();
        mLlHistory.setVisibility(View.VISIBLE);
        mPresenter = new SearchPresenterImpl(this);
        mRvSearchResult.setLayoutManager(new GridLayoutManager(this, 2));
        mRvSearchResult.addItemDecoration(new RecyclerSpaceDecoration(getResources().getDimensionPixelOffset(R.dimen.product_item_padding)));
        mAdapter = new RecyclerBaseAdapter<>(R.layout.item_product, ProductHolder.class);
        mRvSearchResult.setAdapter(mAdapter);

    }

    private void initHistoryList() {
        mPreference = getSharedPreferences(Constant.COMMON_PREFERENCE_NAME, Context.MODE_PRIVATE);
        mSet = mPreference.getStringSet(KEY_SEARCH_HISTORY, new HashSet<String>());
        List<String> historyList = new ArrayList<>();
        for (String s : mSet) {
            historyList.add(s);
        }
        mRvSearchHistory.setLayoutManager(new LinearLayoutManager(this));
        mRvSearchHistory.addItemDecoration(new RecyclerItemDecoration());
        mHistoryAdapter = new RecyclerBaseAdapter<>(R.layout.item_text, SearchHistoryHolder.class);
        mRvSearchHistory.setAdapter(mHistoryAdapter);
        mHistoryAdapter.setAll(historyList);
    }

    @OnClick({R.id.btn_search, R.id.tv_history_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_search:
                mSearch = mEdtSearch.getText().toString().trim();
                if (!TextUtils.isEmpty(mSearch)) {
                    mSet.add(mSearch);
                    applyHistory();
                }
                mPresenter.search(mSearch);
                break;
            case R.id.tv_history_clear:
                mSet.clear();
                applyHistory();
                break;
        }
    }

    private void applyHistory() {
        SharedPreferences.Editor edit = mPreference.edit();
        edit.putStringSet(KEY_SEARCH_HISTORY, mSet);
        edit.apply();
    }

    @Override
    public void searchResult(List<Product> productList) {
        mLlHistory.setVisibility(View.GONE);
        if (productList == null || productList.isEmpty()) {
            mLlEmpty.setVisibility(View.VISIBLE);
            mAdapter.removeAll();
            showToSuperSearch(mSearch);
        } else {
            mLlEmpty.setVisibility(View.GONE);
            mAdapter.setAll(productList);
        }
    }

    @Override
    public void superResult(List<Product> productList) {
        if (productList == null || productList.isEmpty()) {
            mLlEmpty.setVisibility(View.VISIBLE);
            mAdapter.removeAll();
        } else {
            mLlEmpty.setVisibility(View.GONE);
            mAdapter.setAll(productList);
        }
    }

    private void showToSuperSearch(final String key) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("是否使用超级搜索进行搜索？")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mPresenter.superSearch(key);
                    }
                })
                .create();
        alertDialog.show();
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, SearchActivity.class);
        context.startActivity(starter);
    }
}
