package com.starnet.cqj.taobaoke.view.widget.expandtabview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerBaseAdapter;
import com.starnet.cqj.taobaoke.view.adapter.viewholder.ClassifyHolder;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mini on 17/11/2.
 */

public class ClassifyView extends RelativeLayout implements ViewBaseAction {


    @BindView(R.id.rv_classify)
    RecyclerView mRvClassify;
    private Context mContext;


    public ClassifyView(Context context) {
        this(context, null);
    }

    public ClassifyView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ClassifyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_classify, this, true);
        ButterKnife.bind(this);
        RecyclerBaseAdapter<String, ClassifyHolder> adapter = new RecyclerBaseAdapter<>(R.layout.item_classify, ClassifyHolder.class);
        mRvClassify.setLayoutManager(new GridLayoutManager(context,4));
        mRvClassify.setAdapter(adapter);
        setData(adapter);
    }

    private void setData(RecyclerBaseAdapter<String, ClassifyHolder> adapter) {
        String[] stringArray = mContext.getResources().getStringArray(R.array.classify_items);
        adapter.setAll(Arrays.asList(stringArray));

    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }
}
