package com.starnet.cqj.taobaoke.view.adapter;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by ztt on 2017-05-22.
 */
public class RecyclerSpaceDecoration extends RecyclerView.ItemDecoration {
    private int mPadding;


    public RecyclerSpaceDecoration(int padding) {
        mPadding = padding;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(mPadding, mPadding, mPadding, mPadding);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }
}
