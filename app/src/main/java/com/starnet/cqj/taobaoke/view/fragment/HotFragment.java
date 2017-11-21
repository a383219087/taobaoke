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
import com.starnet.cqj.taobaoke.view.adapter.LinearLayoutManagerWrapper;
import com.starnet.cqj.taobaoke.view.adapter.MyViewPagerAdapter;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerBaseAdapter;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerItemDecoration;
import com.starnet.cqj.taobaoke.view.adapter.viewholder.HotArticleHolder;
import com.starnet.cqj.taobaoke.view.widget.AutoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2017/11/03.
 */

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
    private int mPage = 1;
    private boolean mHasMore;
    private String mItemId;

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
        mTitleName.setText(R.string.action_title);
        mAdapter = new RecyclerBaseAdapter<>(R.layout.item_hot_article, HotArticleHolder.class);
        mRvHotList.addItemDecoration(new RecyclerItemDecoration());
        mRvHotList.setLayoutManager(new LinearLayoutManagerWrapper(getActivity()));
        mRvHotList.setAdapter(mAdapter);
        mPresenter = new HotPresenterImpl(this);
        mPresenter.getItem();
        initEvent();
    }

    private void initEvent() {
        mRvHotList.addOnScrollListener(mOnScrollListener);
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
                mPage = 1;
                mPresenter.getList(mItemId);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                int lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                if (mHasMore && lastPosition == recyclerView.getLayoutManager().getItemCount() - 1) {
                    mPage++;
                    mPresenter.getMoreList(mPage, mItemId);
                }
            }
        }
    };

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
        mHotAutoBanner.startAutoScroll();

    }

    @Override
    public void setArticleList(List<Article> articleList) {
        if (mPage == 1) {
            mAdapter.setAll(articleList);
        } else {
            if (articleList == null || articleList.isEmpty()) {
                mHasMore = false;
            }
            mAdapter.addAll(articleList);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHotAutoBanner != null) {
            mHotAutoBanner.stopAutoScroll();
        }
    }
}
