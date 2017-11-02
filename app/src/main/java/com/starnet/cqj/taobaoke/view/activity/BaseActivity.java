package com.starnet.cqj.taobaoke.view.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/31.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Nullable @BindView(R.id.title_back)
    ImageView mIvTitleBack;
    @Nullable @BindView(R.id.title_name)
    TextView mTvTitleName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        ButterKnife.bind(this);
        init();
        initEvent();
    }

    protected void initEvent() {
        if (mIvTitleBack != null) {
            mIvTitleBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    public void setTitleName(@StringRes int name) {
        if (mTvTitleName != null) {
            mTvTitleName.setText(name);
        }
    }

    @LayoutRes
    protected abstract int getContentView();

    protected abstract void init();
}
