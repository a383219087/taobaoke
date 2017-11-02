package com.starnet.cqj.taobaoke.view.widget.expandtabview;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.starnet.cqj.taobaoke.R;


public class ViewLeft extends RelativeLayout implements ViewBaseAction{

	private OnSelectListener mOnSelectListener;
	private String showText = getResources().getString(R.string.unlimited);
	private Button btn1,btn2,btn3,btn4,btn5;

	public String getShowText() {
		return showText;
	}

	public ViewLeft(Context context) {
		super(context);
		init(context);
	}

	public ViewLeft(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public ViewLeft(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.far_or_near, this, true);
		btn1 =(Button) findViewById(R.id.b1);
		btn1.setTag(0);
		btn2 =(Button) findViewById(R.id.b2);
		btn2.setTag(1);
		btn3 =(Button) findViewById(R.id.b3);
		btn3.setTag(2);
		btn4 =(Button) findViewById(R.id.b4);
		btn4.setTag(3);
		btn5 =(Button) findViewById(R.id.b5);
		btn5.setTag(5);
		btn1.setOnClickListener(mBtnClick);
		btn2.setOnClickListener(mBtnClick);
		btn3.setOnClickListener(mBtnClick);
		btn4.setOnClickListener(mBtnClick);
		btn5.setOnClickListener(mBtnClick);
	}
	
	private void clearBg(){
		btn1.setBackgroundResource(R.drawable.lifeguide_distance_select);
		btn2.setBackgroundResource(R.drawable.lifeguide_distance_select);
		btn3.setBackgroundResource(R.drawable.lifeguide_distance_select);
		btn4.setBackgroundResource(R.drawable.lifeguide_distance_select);
		btn5.setBackgroundResource(R.drawable.lifeguide_distance_select);
	}
	
	OnClickListener mBtnClick =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			clearBg();
			v.setBackgroundResource(R.drawable.lifeguide_distance_select_red);
			if (mOnSelectListener != null) {
				showText = ((Button)v).getText().toString();
				mOnSelectListener.getValue((Integer)((Button)v).getTag(), showText);
			}
		}
	};

	public void setOnSelectListener(OnSelectListener onSelectListener) {
		mOnSelectListener = onSelectListener;
	}

	public interface OnSelectListener {
		public void getValue(int distance, String showText);
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void show() {
		
	}

}
