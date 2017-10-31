package com.starnet.cqj.taobaoke.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.all)
    TextView text;

    @Override
    protected void init() {

    }

    @OnClick(R.id.basic) void commit(){

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }
}
