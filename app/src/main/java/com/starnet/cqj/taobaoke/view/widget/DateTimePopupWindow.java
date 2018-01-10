package com.starnet.cqj.taobaoke.view.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.PopupWindow;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.utils.AppUtils;

import java.util.Calendar;
import java.util.Date;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class DateTimePopupWindow extends PopupWindow {

    private DatePicker datePicker;
    private PublishSubject<Date> dateObserver = PublishSubject.create();
    private View mView;

    public DateTimePopupWindow(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.popupwindow_date_time, null, false);
        setContentView(view);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());
        init(view);
    }

    private void init(View view) {
        mView = view;
        datePicker = (DatePicker) view.findViewById(R.id.date_picker);
        int defaultYear = Calendar.getInstance().get(Calendar.YEAR);
        int defaultMonth = Calendar.getInstance().get(Calendar.MONTH);
        int defaultDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        datePicker.init(defaultYear, defaultMonth, defaultDay, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);
                Date date = calendar.getTime();
                dateObserver.onNext(date);
            }
        });
    }

    @Override
    public void showAsDropDown(View anchor) {
        autoShow(anchor, mView);
    }

    private static final String TAG = "DateTimePopupWindow";

    /**
     * 计算出来的位置，y方向就在anchorView的上面和下面对齐显示，x方向就是与屏幕右边对齐显示
     * 如果anchorView的位置有变化，就可以适当自己额外加入偏移来修正
     *
     * @param anchorView  呼出window的view
     * @param contentView window的内容布局
     * @return window显示的左上角的xOff, yOff坐标
     */
    private void autoShow(final View anchorView, final View contentView) {
        final int windowPos[] = new int[2];
        final int anchorLoc[] = new int[2];
        // 获取锚点View在屏幕上的左上角坐标位置
        anchorView.getLocationOnScreen(anchorLoc);
        final int anchorHeight = anchorView.getHeight();
        // 获取屏幕的高宽
        final int screenHeight = AppUtils.getScreenHeight(anchorView.getContext());
        final int screenWidth = AppUtils.getScreenWidth(anchorView.getContext());
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // 计算contentView的高宽
        final int windowHeight = contentView.getMeasuredHeight();
        final int windowWidth = contentView.getMeasuredWidth();
        final boolean isNeedShowLeft = (screenWidth - anchorLoc[0] < windowWidth);
        int x = 0;
        int y = 0;
        if (isNeedShowLeft) {
            x = -windowWidth;
        }
        // 判断需要向上弹出还是向下弹出显示
        final boolean isNeedShowUp = (screenHeight - anchorLoc[1] - anchorHeight < windowHeight);
        if (isNeedShowUp) {
            y = -(windowHeight + anchorHeight);
        }
        showAsDropDown(anchorView, x, y);
    }

    public Observable<Date> dateObservable() {
        return dateObserver;
    }


    public void setDefaultDate(int year, int month, int day) {
        datePicker.updateDate(year, month, day);
    }


}
