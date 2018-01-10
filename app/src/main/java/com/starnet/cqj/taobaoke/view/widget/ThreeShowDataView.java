package com.starnet.cqj.taobaoke.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;

/**
 * Created by Administrator on 2018/01/10.
 */

public class ThreeShowDataView extends LinearLayout {

    private TextView mTvOneValue;
    private TextView mTvTwoValue;
    private TextView mTvThreeValue;

    public ThreeShowDataView(Context context) {
        this(context,null,0);
    }

    public ThreeShowDataView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ThreeShowDataView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        setBackgroundResource(R.color.white);
        LayoutInflater.from(context).inflate(R.layout.view_three_show_data,this,true);
        TextView tvOneTip = (TextView) findViewById(R.id.tv_tip_one);
        TextView tvTwoTip = (TextView) findViewById(R.id.tv_tip_two);
        TextView tvThreeTip = (TextView) findViewById(R.id.tv_tip_three);
        mTvOneValue = (TextView) findViewById(R.id.tv_value_one);
        mTvTwoValue = (TextView) findViewById(R.id.tv_value_two);
        mTvThreeValue = (TextView) findViewById(R.id.tv_value_three);
        if(attrs!=null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ThreeShowDataView);
            if (typedArray != null) {
                String oneTip = typedArray.getString(R.styleable.ThreeShowDataView_one_tip);
                tvOneTip.setText(oneTip);
                String twoTip = typedArray.getString(R.styleable.ThreeShowDataView_two_tip);
                tvTwoTip.setText(twoTip);
                String threeTip = typedArray.getString(R.styleable.ThreeShowDataView_three_tip);
                tvThreeTip.setText(threeTip);
                typedArray.recycle();
            }
        }
    }

    public void setOneValue(String value){
        mTvOneValue.setText(value);
    }

    public void setTwoValue(String twoValue) {
        mTvTwoValue.setText(twoValue);
    }

    public void setThreeValue(String threeValue) {
        mTvThreeValue.setText(threeValue);
    }
}
