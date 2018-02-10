package com.starnet.cqj.taobaoke.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.BuyTip;
import com.starnet.cqj.taobaoke.model.HomePageBanner;
import com.starnet.cqj.taobaoke.model.MainMenu;
import com.starnet.cqj.taobaoke.model.Product;
import com.starnet.cqj.taobaoke.presenter.IHomePagePresenter;
import com.starnet.cqj.taobaoke.presenter.impl.HomePagePresenterImpl;
import com.starnet.cqj.taobaoke.view.BaseApplication;
import com.starnet.cqj.taobaoke.view.activity.HelpCenterActivity;
import com.starnet.cqj.taobaoke.view.activity.MessageListActivity;
import com.starnet.cqj.taobaoke.view.activity.ProductDetailActivity;
import com.starnet.cqj.taobaoke.view.activity.SearchActivity;
import com.starnet.cqj.taobaoke.view.adapter.HasHeaderSpaceDecoration;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerBaseAdapter;
import com.starnet.cqj.taobaoke.view.adapter.viewholder.ProductHolder;
import com.starnet.cqj.taobaoke.view.widget.RecyclerViewLoadMoreHelper;
import com.starnet.cqj.taobaoke.view.widget.topview.HomeTopView;

import java.net.URLDecoder;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class HomePageFragment extends BaseFragment implements IHomePagePresenter.IView {


    @BindView(R.id.home_search_edit)
    TextView mHomeSearchEdit;
    @BindView(R.id.rv_goods_recommend)
    RecyclerView mRvGoodsRecommend;
    @BindView(R.id.sr_refresh)
    SwipeRefreshLayout mSrRefresh;


    private RecyclerBaseAdapter<Product, ProductHolder> mRecommendAdapter;
    private IHomePagePresenter mHomePagePresenter;
    private HomeTopView mHomeTopView;
    private List<Product> mDataList;
    private RecyclerViewLoadMoreHelper mHelper;

    public static HomePageFragment newInstance() {
        return new HomePageFragment();
    }


    @Override
    int getContentRes() {
        return R.layout.fragment_homepage;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHomePagePresenter = new HomePagePresenterImpl(this);
        mHelper = new RecyclerViewLoadMoreHelper();
        init();
        initEvent();
    }

    private void init() {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        mRvGoodsRecommend.setLayoutManager(layoutManager);
        mRvGoodsRecommend.addItemDecoration(new HasHeaderSpaceDecoration(getActivity().getResources().getDimensionPixelOffset(R.dimen.product_item_padding)));
        mHomeTopView = new HomeTopView(getActivity());
        mHomeTopView.clearFocus();
        mHomeTopView.setFocusable(false);
        mHomeTopView.setFocusableInTouchMode(false);
        mRecommendAdapter = new RecyclerBaseAdapter<>(mHomeTopView, R.layout.item_product, ProductHolder.class);
        mRvGoodsRecommend.setAdapter(mRecommendAdapter);
        mHomeTopView.getData();
        mSrRefresh.setColorSchemeResources(R.color.main_color);
    }


    private void initEvent() {
        mRecommendAdapter.itemClickObserve()
                .compose(this.<Product>bindToLifecycle())
                .subscribe(new Consumer<Product>() {
                    @Override
                    public void accept(Product product) throws Exception {
                        String url = product.getUrl();
                        url = URLDecoder.decode(url);
                        ProductDetailActivity.start(getActivity(), url);
                    }
                });
        mSrRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSrRefresh.setRefreshing(true);
                mHomeTopView.getData();
            }
        });
        mHomeTopView.setCallback(new HomeTopView.Callback() {
            private boolean mIsShow = true;

            @Override
            public void refreshDone(boolean success) {
                if (success && mIsShow) {
                    mHelper.resetPage();
                    mHomePagePresenter.getRecommend(mHelper.getPage());
                } else {
                    mSrRefresh.setRefreshing(false);
                }
            }

            @Override
            public void tabChange(boolean isShow) {
                mIsShow = isShow;
                if (isShow) {
                    mRecommendAdapter.setAll(mDataList);
                } else {
                    mRecommendAdapter.removeAll();
                }
            }
        });
        mRvGoodsRecommend.addOnScrollListener(mHelper.getOnScrollListener());
        mHelper.setLoadMoreCallback(new RecyclerViewLoadMoreHelper.LoadMoreCallback() {
            @Override
            public void loadMore() {
                mHomePagePresenter.getRecommend(mHelper.getPage());
            }
        });
    }

    @OnClick(R.id.homepage_help)
    void toHelp() {
        HelpCenterActivity.start(getActivity());
    }

    @OnClick(R.id.homepage_message)
    void toMessage() {
        if (TextUtils.isEmpty(((BaseApplication) getActivity().getApplication()).getToken())) {
            return;
        }
        MessageListActivity.start(getActivity());
    }

    @OnClick(R.id.home_search_edit)
    void toSearch() {
        SearchActivity.start(getActivity());
    }


    @Override
    public void setCategoryList(List<MainMenu> mainMenuList) {
    }

    @Override
    public void setLookBuy(List<Product> productList) {
    }

    @Override
    public void setRecommend(List<Product> productList) {
        if (mHelper.isFirstPage()) {
            mRecommendAdapter.setAll(productList);
            mSrRefresh.setRefreshing(false);
            mDataList = productList;
        } else {
            mHelper.setNoMore(productList == null || productList.isEmpty());
            mRecommendAdapter.addAll(productList);
        }
    }

    @Override
    public void setBanner(HomePageBanner banner) {

    }

    @Override
    public void setTip(BuyTip tip) {

    }


    @Override
    public void toast(String res) {
        if (mSrRefresh != null) {
            mSrRefresh.setRefreshing(false);
        }
        Toast.makeText(getActivity(), res, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toast(@StringRes int res) {
        if (mSrRefresh != null) {
            mSrRefresh.setRefreshing(false);
        }
        Toast.makeText(getActivity(), res, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHomePagePresenter != null) {
            mHomePagePresenter.onDestroy();
        }
        if (mRvGoodsRecommend != null) {
            mRvGoodsRecommend.removeOnScrollListener(mHelper.getOnScrollListener());
        }
        if (mHomeTopView != null) {
            mHomeTopView.onDestroy();
        }
    }
}
