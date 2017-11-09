package com.starnet.cqj.taobaoke.view.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.presenter.ISearchPresenter;
import com.starnet.cqj.taobaoke.presenter.impl.SearchPresenterImpl;

import butterknife.BindView;
import butterknife.OnClick;


public class SearchActivity extends BaseActivity implements ISearchPresenter.IView{
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

    @Override
    protected int getContentView() {
        return R.layout.activity_search;
    }

    @Override
    protected void init() {
        mPresenter = new SearchPresenterImpl(this);
        mPresenter.getSearchHistory();
    }

    @OnClick({R.id.btn_search, R.id.tv_history_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_search:
                mPresenter.search(mEdtSearch.getText().toString());
                break;
            case R.id.tv_history_clear:
                break;
        }
    }
}
