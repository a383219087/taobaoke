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
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.ShareContent;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;
import com.starnet.cqj.taobaoke.view.BaseApplication;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class SharePopupWindow extends PopupWindow {

    private Context mContext;
    private ShareContent mShareContent;

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
        getShareData();
    }

    private void getShareData(){
        if (mContext instanceof RxAppCompatActivity) {
            RxAppCompatActivity activity = (RxAppCompatActivity) mContext;
            RemoteDataSourceBase.INSTANCE.getUserService()
                    .share(((BaseApplication) activity.getApplication()).token)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .compose(activity.<JsonCommon<ShareContent>>bindToLifecycle())
                    .subscribe(new Consumer<JsonCommon<ShareContent>>() {
                        @Override
                        public void accept(JsonCommon<ShareContent> shareContentJsonCommon) throws Exception {
                            if("200".equals(shareContentJsonCommon.getCode())){
                                mShareContent = shareContentJsonCommon.getData();
                            }else{
                                Toast.makeText(mContext, shareContentJsonCommon.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            throwable.printStackTrace();
                            Toast.makeText(mContext, "网络错误", Toast.LENGTH_SHORT).show();
                        }
                    });

        }
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
                share(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.tv_share_wechat_moments:
                share(SHARE_MEDIA.WEIXIN_CIRCLE);
                break;
            case R.id.tv_share_qq:
                share(SHARE_MEDIA.QQ);
                break;
            case R.id.tv_share_qq_zone:
                share(SHARE_MEDIA.QZONE);
                break;
            case R.id.btn_share_cancel:
                dismiss();
                break;
        }
    }

    private void share(SHARE_MEDIA media) {
        if (!(mContext instanceof Activity)) {
            return;
        }
        UMImage umImage = new UMImage(mContext,R.drawable.main_icon);
        UMWeb web = new UMWeb(mShareContent.getUrl(),mShareContent.getContent(),"",umImage);
        Activity activity = (Activity) mContext;
        new ShareAction(activity)
                .setPlatform(media)//传入平台
                .withMedia(web)
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
