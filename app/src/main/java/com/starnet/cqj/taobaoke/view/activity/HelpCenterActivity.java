package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.Question;
import com.starnet.cqj.taobaoke.view.adapter.LinearLayoutManagerWrapper;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerBaseAdapter;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerItemDecoration;
import com.starnet.cqj.taobaoke.view.adapter.viewholder.QuestionListHolder;

import butterknife.BindView;
import butterknife.OnClick;

public class HelpCenterActivity extends BaseActivity {

    @BindView(R.id.help_btn_general_question)
    TextView mTvGeneralQuestion;

    @BindView(R.id.help_btn_general_question_iv)
    ImageView mIvGeneralQuestion;

    @BindView(R.id.help_btn_integral_question)
    TextView mTvIntegralQuestion;

    @BindView(R.id.help_btn_integral_question_iv)
    ImageView mIvIntegralQuestion;

    @BindView(R.id.help_btn_how_shopping)
    TextView mTvHowShopping;

    @BindView(R.id.help_btn_how_shopping_iv)
    ImageView mIvHowShopping;

    @BindView(R.id.help_btn_other)
    TextView mTvOther;

    @BindView(R.id.help_btn_other_iv)
    ImageView mIvOther;

    @BindView(R.id.wrong_list)
    RecyclerView mRecyclerView;


    private RecyclerBaseAdapter<Question, QuestionListHolder> mAdapter;

    @Override
    protected void init() {
        setTitleName(R.string.help_center_title);
        mTvGeneralQuestion.setSelected(true);
        mIvGeneralQuestion.setSelected(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManagerWrapper(this));
        mAdapter = new RecyclerBaseAdapter<>(R.layout.item_question,QuestionListHolder.class);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new RecyclerItemDecoration());
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_help_center;
    }

    private void clearAllColor(){
        mTvGeneralQuestion.setSelected(false);
        mIvGeneralQuestion.setSelected(false);
        mTvIntegralQuestion.setSelected(false);
        mIvIntegralQuestion.setSelected(false);
        mTvHowShopping.setSelected(false);
        mIvHowShopping.setSelected(false);
        mTvOther.setSelected(false);
        mIvOther.setSelected(false);
    }

    @OnClick(R.id.help_btn_general_question)
    void generalQuestion(){
        clearAllColor();
        mTvGeneralQuestion.setSelected(true);
        mIvGeneralQuestion.setSelected(true);
    }

    @OnClick(R.id.help_btn_integral_question)
    void integralQuestion(){
        clearAllColor();
        mTvIntegralQuestion.setSelected(true);
        mIvIntegralQuestion.setSelected(true);
    }

    @OnClick(R.id.help_btn_how_shopping)
    void howShopping(){
        clearAllColor();
        mTvHowShopping.setSelected(true);
        mIvHowShopping.setSelected(true);
    }

    @OnClick(R.id.help_btn_other)
    void other(){
        clearAllColor();
        mTvOther.setSelected(true);
        mIvOther.setSelected(true);
    }

    private void getData(int type){

    }

    public static void start(Context context) {
        Intent starter = new Intent(context, HelpCenterActivity.class);
        context.startActivity(starter);
    }
}
