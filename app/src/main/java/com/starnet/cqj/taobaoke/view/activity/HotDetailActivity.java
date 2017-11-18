package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.Article;

import butterknife.BindView;

public class HotDetailActivity extends BaseActivity {

    public static final String KEY_DETAIL = "detail";
    @BindView(R.id.tv_detail_title)
    TextView mTvDetailTitle;
    @BindView(R.id.tv_detail_time)
    TextView mTvDetailTime;
    @BindView(R.id.tv_detail_see)
    TextView mTvDetailSee;
    @BindView(R.id.tv_detail_content)
    TextView mTvDetailContent;
    private Article mArticle;

    @Override
    protected int getContentView() {
        return R.layout.activity_hot_detail;
    }

    @Override
    protected void init() {
        setTitleName(R.string.hot_detail_title);
        mArticle = (Article) getIntent().getSerializableExtra(KEY_DETAIL);
        mTvDetailTitle.setText(mArticle.getTitle());
        mTvDetailSee.setText(mArticle.getView());
        mTvDetailContent.setText(Html.fromHtml(mArticle.getDec()));
        mTvDetailTime.setText(mArticle.getTime());
    }


    public static void start(Context context, Article article) {
        Intent starter = new Intent(context, HotDetailActivity.class);
        starter.putExtra(KEY_DETAIL,article);
        context.startActivity(starter);
    }

}
