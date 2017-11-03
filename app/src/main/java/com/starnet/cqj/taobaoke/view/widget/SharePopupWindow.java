package com.starnet.cqj.taobaoke.view.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.starnet.cqj.taobaoke.R;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class SharePopupWindow extends PopupWindow {

    public SharePopupWindow(Context context) {
        super(context);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(context).inflate(R.layout.popup_share, null, false);
        setContentView(view);
        ButterKnife.bind(this, view);
        setBackgroundDrawable(new BitmapDrawable());
        setOutsideTouchable(true);
    }

    @OnClick({R.id.tv_share_wechat, R.id.tv_share_wechat_moments, R.id.tv_share_qq, R.id.tv_share_qq_zone, R.id.btn_share_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_share_wechat:
                break;
            case R.id.tv_share_wechat_moments:
                break;
            case R.id.tv_share_qq:
                break;
            case R.id.tv_share_qq_zone:
                break;
            case R.id.btn_share_cancel:
                dismiss();
                break;
        }
    }
}
