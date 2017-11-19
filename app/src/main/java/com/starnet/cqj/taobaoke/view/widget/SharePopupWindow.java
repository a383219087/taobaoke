package com.starnet.cqj.taobaoke.view.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.starnet.cqj.taobaoke.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class SharePopupWindow extends PopupWindow {

    private Context mContext;

    public SharePopupWindow(Context context) {
        super(context);
        mContext = context;
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(context).inflate(R.layout.popup_share, null, false);
        setContentView(view);
        ButterKnife.bind(this, view);
        setBackgroundDrawable(new BitmapDrawable());
        setOutsideTouchable(true);

        setOnDismissListener(mOnDismissListener);
    }

    private OnDismissListener mOnDismissListener = new OnDismissListener() {
        @Override
        public void onDismiss() {
            backgroundAlpha(1);
        }
    };

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        backgroundAlpha(0.5f);
    }

    private void backgroundAlpha(float bgAlpha) {
        if (mContext instanceof Activity) {
            Activity activity = (Activity) mContext;
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            lp.alpha = bgAlpha; //0.0-1.0
            activity.getWindow().setAttributes(lp);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
    }

    @OnClick({R.id.tv_share_wechat, R.id.tv_share_wechat_moments, R.id.tv_share_qq, R.id.tv_share_qq_zone, R.id.btn_share_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_share_wechat:
                share(SHARE_MEDIA.WEIXIN,"你好在");
                break;
            case R.id.tv_share_wechat_moments:
                share(SHARE_MEDIA.WEIXIN_CIRCLE,"你好在");
                break;
            case R.id.tv_share_qq:
                share(SHARE_MEDIA.QQ,"你好在");
                break;
            case R.id.tv_share_qq_zone:
                share(SHARE_MEDIA.QZONE,"你好在");
                break;
            case R.id.btn_share_cancel:
                dismiss();
                break;
        }
    }

    private void share(SHARE_MEDIA media,String content) {
        if (!(mContext instanceof Activity)) {
            return;
        }
        Activity activity = (Activity) mContext;
        new ShareAction(activity)
                .setPlatform(media)//传入平台
                .withText(content)//分享内容
                .setCallback(shareListener)//回调监听器
                .share();
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(mContext,"分享成功",Toast.LENGTH_LONG).show();
            dismiss();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(mContext,"失败"+t.getMessage(),Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(mContext,"取消了",Toast.LENGTH_LONG).show();

        }
    };


}
