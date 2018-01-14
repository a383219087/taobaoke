package com.starnet.cqj.taobaoke.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/01/10.
 */

public class ThreeShowDataView extends LinearLayout {

    @BindView(R.id.ll_one)
    LinearLayout mLlOne;
    @BindView(R.id.line_one)
    View mLineOne;
    @BindView(R.id.ll_two)
    LinearLayout mLlTwo;
    @BindView(R.id.line_two)
    View mLineTwo;
    @BindView(R.id.ll_three)
    LinearLayout mLlThree;
    private TextView mTvOneValue;
    private TextView mTvTwoValue;
    private TextView mTvThreeValue;
    private TextView mTvOneTip;
    private TextView mTvTwoTip;
    private TextView mTvThreeTip;

    public ThreeShowDataView(Context context) {
        this(context, null, 0);
    }

    public ThreeShowDataView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThreeShowDataView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        setBackgroundResource(R.color.white);
        LayoutInflater.from(context).inflate(R.layout.view_three_show_data, this, true);
        ButterKnife.bind(this);
        mTvOneTip = (TextView) findViewById(R.id.tv_tip_one);
        mTvTwoTip = (TextView) findViewById(R.id.tv_tip_two);
        mTvThreeTip = (TextView) findViewById(R.id.tv_tip_three);
        mTvOneValue = (TextView) findViewById(R.id.tv_value_one);
        mTvTwoValue = (TextView) findViewById(R.id.tv_value_two);
        mTvThreeValue = (TextView) findViewById(R.id.tv_value_three);
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ThreeShowDataView);
            if (typedArray != null) {
                String oneTip = typedArray.getString(R.styleable.ThreeShowDataView_one_tip);
                mTvOneTip.setText(oneTip);
                String twoTip = typedArray.getString(R.styleable.ThreeShowDataView_two_tip);
                mTvTwoTip.setText(twoTip);
                String threeTip = typedArray.getString(R.styleable.ThreeShowDataView_three_tip);
                mTvThreeTip.setText(threeTip);
                typedArray.recycle();
            }
        }
    }

    public void setOneValue(String value) {
        mTvOneValue.setText(value);
    }

    public void setTwoValue(String twoValue) {
        mTvTwoValue.setText(twoValue);
    }

    public void setThreeValue(String threeValue) {
        mTvThreeValue.setText(threeValue);
    }

    public void setOneTip(String tip){
        mTvOneTip.setText(tip);
    }

    public void setTwoTip(String tip){
        mTvTwoTip.setText(tip);
    }

    public void setThreeTip(String tip){
        mTvThreeTip.setText(tip);
    }

    public void setValueCount(int count){
        switch (count){
            case 1:
                mLineOne.setVisibility(GONE);
                mLlThree.setVisibility(GONE);
                mLineTwo.setVisibility(GONE);
                mLlTwo.setVisibility(GONE);
                break;
            case 2:
                mLineTwo.setVisibility(GONE);
                mLlThree.setVisibility(GONE);
                break;
            default:
                break;
        }
    }
}
