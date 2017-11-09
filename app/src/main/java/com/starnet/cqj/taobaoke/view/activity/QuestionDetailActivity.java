package com.starnet.cqj.taobaoke.view.activity;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;

import butterknife.BindView;
import butterknife.OnClick;

public class QuestionDetailActivity extends BaseActivity {

    @BindView(R.id.title_rightimage)
    ImageButton mTitleRightimage;
    @BindView(R.id.tv_question_title)
    TextView mTvQuestionTitle;
    @BindView(R.id.iv_question_icon)
    ImageView mIvQuestionIcon;
    @BindView(R.id.tv_question_content)
    TextView mTvQuestionContent;

    @Override
    protected int getContentView() {
        return R.layout.activity_question_detail;
    }

    @Override
    protected void init() {
        setTitleName(R.string.question_detail_title);
        mTitleRightimage.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.title_rightimage)
    public void onViewClicked() {
    }
}
