package com.starnet.cqj.taobaoke.view.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.Article;
import com.starnet.cqj.taobaoke.view.adapter.BaseHolder;
import com.starnet.cqj.taobaoke.view.adapter.IParamContainer;

import java.util.List;

import butterknife.BindView;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by mini on 17/11/18.
 */

public class HotArticleHolder extends BaseHolder<Article> {

    @BindView(R.id.iv_article)
    ImageView mIvArticle;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_view)
    TextView mTvView;

    public HotArticleHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(List<Article> data, int position, IParamContainer container, final PublishSubject<Article> itemClick) {
        final Article article = data.get(position);
        if (article != null) {
            Glide.with(itemView.getContext())
                    .load(article.getPic())
                    .into(mIvArticle);
            mTvTitle.setText(article.getTitle());
            mTvTime.setText(article.getTime());
            mTvView.setText(article.getView());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClick.onNext(article);
                }
            });
        }
    }
}
