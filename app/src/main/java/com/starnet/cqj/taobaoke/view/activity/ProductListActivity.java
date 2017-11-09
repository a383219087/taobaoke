package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.MainMenu;
import com.starnet.cqj.taobaoke.model.ProductSort;
import com.starnet.cqj.taobaoke.view.widget.expandtabview.ClassifyView;
import com.starnet.cqj.taobaoke.view.widget.expandtabview.ExpandTabView;
import com.starnet.cqj.taobaoke.view.widget.expandtabview.PriceBetweenView;
import com.starnet.cqj.taobaoke.view.widget.expandtabview.SortView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class ProductListActivity extends BaseActivity {


    @BindView(R.id.btn_search)
    Button mBtnSearch;
    @BindView(R.id.edt_search)
    EditText mEdtSearch;
    @BindView(R.id.product_expand_tab)
    ExpandTabView mProductExpandTab;
    @BindView(R.id.rv_product)
    RecyclerView mRvProduct;
    @BindView(R.id.btn_back_top)
    Button mBtnBackTop;
    @BindView(R.id.message_avatar)
    ImageView mMessageAvatar;
    @BindView(R.id.tv_message_name)
    TextView mTvMessageName;
    @BindView(R.id.tv_message_price)
    TextView mTvMessagePrice;
    @BindView(R.id.tv_message_time)
    TextView mTvMessageTime;
    @BindView(R.id.ll_message)
    LinearLayout mLlMessage;
    private SortView mSortView;
    private ClassifyView mClassifyView;
    private PriceBetweenView mBetweenView;

    @Override
    protected int getContentView() {
        return R.layout.activity_product_list;
    }

    @Override
    protected void init() {
        List<String> titles = new ArrayList<>();
        titles.add("专题分类");
        titles.add("默认排序");
        titles.add("价格筛选");
        List<View> viewList = new ArrayList<>();
        mClassifyView = new ClassifyView(this);
        viewList.add(mClassifyView);
        mSortView = new SortView(this);
        viewList.add(mSortView);
        mBetweenView = new PriceBetweenView(this);
        viewList.add(mBetweenView);
        mProductExpandTab.setValue(titles, viewList);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mClassifyView.getObservable()
                .compose(this.<MainMenu>bindToLifecycle())
                .subscribe(new Consumer<MainMenu>() {
                    @Override
                    public void accept(MainMenu mainMenu) throws Exception {
                        mProductExpandTab.onPressBack();
                        mProductExpandTab.setTitle(mainMenu.getName(),0);
                    }
                });
        mSortView.getObservable()
                .compose(this.<ProductSort>bindToLifecycle())
                .subscribe(new Consumer<ProductSort>() {
                    @Override
                    public void accept(ProductSort productSort) throws Exception {
                        mProductExpandTab.onPressBack();
                        mProductExpandTab.setTitle(productSort.getName(),1);
                    }
                });
        mBetweenView.getObservable()
                .compose(this.<PriceBetweenView.PriceBetween>bindToLifecycle())
                .subscribe(new Consumer<PriceBetweenView.PriceBetween>() {
                    @Override
                    public void accept(PriceBetweenView.PriceBetween priceBetween) throws Exception {
                        mProductExpandTab.onPressBack();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mClassifyView != null) {
            mClassifyView.onDestroy();
        }
        if (mSortView != null) {
            mSortView.onDestroy();
        }
    }

    @Override
    public void onBackPressed() {
        if (!mProductExpandTab.onPressBack()) {
            super.onBackPressed();
        }
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, ProductListActivity.class);
        context.startActivity(starter);
    }

}
