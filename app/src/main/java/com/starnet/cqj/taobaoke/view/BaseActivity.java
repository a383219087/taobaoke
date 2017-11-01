package com.starnet.cqj.taobaoke.view;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/10/31.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @BindView(R.id.title_back)
    ImageView mIvTitleBack;
    @BindView(R.id.title_name)
    TextView mTvTitleName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        ButterKnife.bind(this);
        init();
    }

    public void setTitleName(@StringRes int name) {
        if (mTvTitleName != null) {
            mTvTitleName.setText(name);
        }
    }

    protected abstract void init();

    @LayoutRes
    protected abstract int getContentView();
}
