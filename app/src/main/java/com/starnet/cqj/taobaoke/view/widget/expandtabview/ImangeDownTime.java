package com.starnet.cqj.taobaoke.view.widget.expandtabview;

import android.os.CountDownTimer;
import android.widget.ImageView;


public class ImangeDownTime extends CountDownTimer {
	private ImageView imgClick;

	public ImangeDownTime(long millisInFuture, long countDownInterval, ImageView imgClick) {
		super(millisInFuture, countDownInterval);
		this.imgClick = imgClick;
	}

	@Override
	public void onFinish() {
		
		imgClick.setEnabled(true);
		imgClick.setClickable(true);
	}

	@Override
	public void onTick(long avaliable) {
		long s = avaliable / 1000;
		long day = s / 86400;// ʣ������
		long a = s % 86400;
		long hour = (int) (a / 3600);// ʣ���Сʱ��
		long b = a % 3600;
		long minute = b / 60;// ʣ�����
		long c = b % 120;
		long second = c;// ʣ������
		imgClick.setEnabled(false);
	}

}
