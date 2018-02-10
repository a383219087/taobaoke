package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by John on 18/2/10.
 */

public class SearchDialogActivity extends BaseActivity {


    public static final String KEY_SEARCH_CONTENT = "clip_content";
    @BindView(R.id.tv_search_title)
    TextView mTvSearchTitle;

    @Override
    protected int getContentView() {
        return R.layout.activity_search_dialog;
    }

    @Override
    protected void init() {
        String searchContent = getIntent().getStringExtra(KEY_SEARCH_CONTENT);
        mTvSearchTitle.setText(searchContent);
    }


    @OnClick({R.id.btn_search_cancel, R.id.btn_search_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_search_cancel:
                finish();
                break;
            case R.id.btn_search_buy:
                SearchActivity.start(this, mTvSearchTitle.getText().toString());
                finish();
                break;
        }
    }

    public static void start(Context context, String searchContent) {
        Intent starter = new Intent(context, SearchDialogActivity.class);
        starter.putExtra(KEY_SEARCH_CONTENT, searchContent);
        context.startActivity(starter);
    }
}
