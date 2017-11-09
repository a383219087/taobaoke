package com.starnet.cqj.taobaoke.view.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.Question;
import com.starnet.cqj.taobaoke.view.adapter.BaseHolder;
import com.starnet.cqj.taobaoke.view.adapter.IParamContainer;

import java.util.List;

import butterknife.BindView;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by mini on 17/11/1.
 */

public class QuestionListHolder extends BaseHolder<Question>{

    @BindView(R.id.question_item_title)
    TextView mTvTitle;

    @BindView(R.id.question_item_detail)
    TextView mTvDetail;


    public QuestionListHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(List<Question> data, int position, IParamContainer container, final PublishSubject<Question> itemClick) {
        final Question question = data.get(position);
        mTvTitle.setText(question.getName());
        mTvDetail.setText(question.getDetail());
        mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onNext(question);
            }
        });
    }
}
