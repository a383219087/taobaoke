package com.starnet.cqj.taobaoke.view.widget.expandtabview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.starnet.cqj.taobaoke.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;


public class PriceBetweenView extends RelativeLayout implements ViewBaseAction {

    @BindView(R.id.edt_price_start)
    EditText mEdtPriceStart;
    @BindView(R.id.edt_price_end)
    EditText mEdtPriceEnd;

    private Context mContext;
    private PublishSubject<PriceBetween> mPublishSubject = PublishSubject.create();

    public PriceBetweenView(Context context) {
        this(context, null);
    }

    public PriceBetweenView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public PriceBetweenView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.view_price_between, this, true);
        ButterKnife.bind(this);
        initEvent();
    }


    private void initEvent() {
    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }

    @OnClick(R.id.btn_price_done)
    void viewClick(View view) {
        String startPrice = mEdtPriceStart.getText().toString();
        String endPrice = mEdtPriceEnd.getText().toString();
        if(TextUtils.isEmpty(startPrice)){
            Toast.makeText(mContext, "请输入最低价格", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(endPrice)){
            Toast.makeText(mContext, "请输入最高价格", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            double start = Double.parseDouble(startPrice);
            double end = Double.parseDouble(endPrice);
            if(start>end){
                Toast.makeText(mContext, "最低价格无法高出最高价格", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(mContext, "请输入正确的价格", Toast.LENGTH_SHORT).show();
            return;
        }
        mPublishSubject.onNext(new PriceBetween(startPrice, endPrice));

    }

    public Observable<PriceBetween> getObservable() {
        return mPublishSubject;
    }

    public class PriceBetween {
        public String startPrice;
        public String endPrice;

        public PriceBetween(String startPrice, String endPrice) {
            this.startPrice = startPrice;
            this.endPrice = endPrice;
        }
    }
}
