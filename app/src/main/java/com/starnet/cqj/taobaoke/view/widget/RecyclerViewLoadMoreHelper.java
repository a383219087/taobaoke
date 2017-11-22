package com.starnet.cqj.taobaoke.view.widget;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Administrator on 2017/11/22.
 */

public class RecyclerViewLoadMoreHelper {

    private int mPage = 1;
    private boolean noMore;
    private boolean isLoading;
    private LoadMoreCallback mLoadMoreCallback;

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            int lastPosition = 0;
            if (layoutManager instanceof GridLayoutManager) {
                //通过LayoutManager找到当前显示的最后的item的position
                lastPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else if (layoutManager instanceof LinearLayoutManager) {
                lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            }
            int itemCount = recyclerView.getLayoutManager().getItemCount();
            if (!isLoading && !noMore
                    && (lastPosition == itemCount - 3
                    || lastPosition == itemCount - 2
                    || lastPosition == itemCount - 4)) {
                isLoading = true;
                mPage++;
                if (mLoadMoreCallback != null) {
                    mLoadMoreCallback.loadMore();
                }
            }
        }
    };



    public RecyclerView.OnScrollListener getOnScrollListener() {
        return mOnScrollListener;
    }

    public void resetPage() {
        mPage = 1;
    }

    public int getPage() {
        return mPage;
    }

    public boolean isFirstPage() {
        return mPage == 1;
    }

    public void setNoMore(boolean noMore) {
        this.noMore = noMore;
        setLoading(false);
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public void setLoadMoreCallback(LoadMoreCallback loadMoreCallback) {
        mLoadMoreCallback = loadMoreCallback;
    }

    public interface LoadMoreCallback {
        void loadMore();
    }

}
