package com.starnet.cqj.taobaoke.view.widget;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * 倒计时button,获取验证码
 * @author ZhangMY
 *
 */
public class ButtonCountDown extends AppCompatButton{
	
	private StartCount mStarCount;

	public ButtonCountDown(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public ButtonCountDown(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ButtonCountDown(Context context) {
		super(context);
		init();
	}
	
	private void init(){
		mStarCount = new StartCount();
	}
	
	public void start(){
		mStarCount.start();
		setEnabled(false);
	}
	
	public void stop(){
		mStarCount.cancel();
		setEnabled(true);
	}
	
	class StartCount extends CountDownTimer{
		
		private static final long MillsInFuture = 60*1000;
		private static final long CountDownInterval = 1*1000;
		
		public StartCount(){
			this(MillsInFuture,CountDownInterval);
		}

		public StartCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			setText(String.valueOf(millisUntilFinished/1000)+"秒");
		}

		@Override
		public void onFinish() {
			setText("重新获取");
			setEnabled(true);
		}
		
	}
	

}
