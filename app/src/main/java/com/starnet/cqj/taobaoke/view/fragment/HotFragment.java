package com.starnet.cqj.taobaoke.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.Article;
import com.starnet.cqj.taobaoke.model.Banner;
import com.starnet.cqj.taobaoke.model.HotItem;
import com.starnet.cqj.taobaoke.presenter.IHotPresenter;
import com.starnet.cqj.taobaoke.presenter.impl.HotPresenterImpl;
import com.starnet.cqj.taobaoke.remote.Constant;
import com.starnet.cqj.taobaoke.view.activity.WebViewActivity;
import com.starnet.cqj.taobaoke.view.adapter.MyViewPagerAdapter;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerBaseAdapter;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerItemDecoration;
import com.starnet.cqj.taobaoke.view.adapter.viewholder.HotArticleHolder;
import com.starnet.cqj.taobaoke.view.widget.AutoScrollViewPager;
import com.starnet.cqj.taobaoke.view.widget.RecyclerViewLoadMoreHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class HotFragment extends BaseFragment implements IHotPresenter.IView {

    @BindView(R.id.title_back)
    ImageView mTitleBack;
    @BindView(R.id.title_name)
    TextView mTitleName;
    @BindView(R.id.hot_auto_banner)
    AutoScrollViewPager mHotAutoBanner;
    @BindView(R.id.tabs)
    TabLayout mTabs;
    @BindView(R.id.rv_hot_list)
    RecyclerView mRvHotList;

    private IHotPresenter mPresenter;
    private RecyclerBaseAdapter<Article, HotArticleHolder> mAdapter;
    private String mItemId;
    private RecyclerViewLoadMoreHelper mHelper;

    public static HotFragment newInstance() {

        Bundle args = new Bundle();

        HotFragment fragment = new HotFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    int getContentRes() {
        return R.layout.fragment_hot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTitleBack.setVisibility(View.GONE);
        mTitleName.setText(R.string.hot_title);
        mAdapter = new RecyclerBaseAdapter<>(R.layout.item_hot_article, HotArticleHolder.class);
        mRvHotList.addItemDecoration(new RecyclerItemDecoration());
        mRvHotList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvHotList.setAdapter(mAdapter);
        mHelper = new RecyclerViewLoadMoreHelper();
        mPresenter = new HotPresenterImpl(this);
        mPresenter.getItem();
        initEvent();
    }

    private void initEvent() {
        mRvHotList.addOnScrollListener(mHelper.getOnScrollListener());
        mAdapter.itemClickObserve()
                .compose(this.<Article>bindToLifecycle())
                .subscribe(new Consumer<Article>() {
                    @Override
                    public void accept(Article article) throws Exception {
                        WebViewActivity.start(getActivity(), Constant.HOT_DETAIL_PREFIX + article.getId());
                    }
                });
        mTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mItemId = (String) tab.getTag();
                mHelper.resetPage();
                mPresenter.getList(mItemId);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mHelper.setLoadMoreCallback(new RecyclerViewLoadMoreHelper.LoadMoreCallback() {
            @Override
            public void loadMore() {
                mPresenter.getMoreList(mHelper.getPage(), mItemId);
            }
        });
    }

    @Override
    public void onGetItem(List<HotItem> hotItemList) {
        if (hotItemList == null || hotItemList.isEmpty()) {
            return;
        }
        for (int i = 0; i < hotItemList.size(); i++) {
            HotItem hotItem = hotItemList.get(i);
            TabLayout.Tab tab = mTabs.newTab();
            tab.setText(TextUtils.isEmpty(hotItem.getName()) ? "默认" + i : hotItem.getName());
            tab.setTag(hotItem.getId());
            mTabs.addTab(tab);
            if (i == 0) {
                mItemId = hotItem.getId();
                mPresenter.getList(mItemId);
            }
        }
    }

    @Override
    public void setBanner(List<Banner> bannerList) {
        List<View> viewList = new ArrayList<>();
        for (int i = 0; i < bannerList.size(); i++) {
            final Banner banner = bannerList.get(i);
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(this)
                    .load(banner.getPic())
                    .into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    WebViewActivity.start(getActivity(), Constant.HOT_DETAIL_PREFIX + banner.getId());
                }
            });
            viewList.add(imageView);
        }
        mHotAutoBanner.setAdapter(new MyViewPagerAdapter(viewList));
        mHotAutoBanner.startAutoScroll(Constant.BANNER_AUTO_TIME);

    }

    @Override
    public void setArticleList(List<Article> articleList) {
        if (mHelper.isFirstPage()) {
            mAdapter.setAll(articleList);
        } else {
            mHelper.setNoMore(articleList == null || articleList.isEmpty());
            mAdapter.addAll(articleList);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHotAutoBanner != null) {
            mHotAutoBanner.stopAutoScroll();
        }
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }
}
