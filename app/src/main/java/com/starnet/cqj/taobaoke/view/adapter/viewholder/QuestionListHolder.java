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
 * Created by mini on 17/11/1.
 */

public class QuestionListHolder extends BaseHolder<Article> {

    @BindView(R.id.question_item_title)
    TextView mTvTitle;

    @BindView(R.id.question_item_detail)
    TextView mTvDetail;

    @BindView(R.id.iv_article)
    ImageView mIvArticle;


    public QuestionListHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(List<Article> data, int position, IParamContainer container, final PublishSubject<Article> itemClick) {
        final Article question = data.get(position);
        mTvTitle.setText(question.getTitle());
        mTvDetail.setText(question.getDec());
        Glide.with(itemView.getContext())
                .load(question.getPic())
                .into(mIvArticle);
        mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onNext(question);
            }
        });
    }
}
