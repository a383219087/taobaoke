package com.starnet.cqj.taobaoke.view.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.starnet.cqj.taobaoke.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Administrator on 2017/10/31.
 */

public abstract class BaseActivity extends AppCompatActivity{

    @Nullable @BindView(R.id.title_back)
    ImageView mIvTitleBack;
    @Nullable @BindView(R.id.title_name)
    TextView mTvTitleName;

    protected CompositeDisposable mCompositeDisposable = new CompositeDisposable();

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.dispose();
    }

    public void toast(String res) {
        Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
    }

    public void toast(@StringRes int res) {
        Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
    }
}
