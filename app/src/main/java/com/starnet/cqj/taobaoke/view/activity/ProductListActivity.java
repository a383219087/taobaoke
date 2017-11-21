package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.BuyTip;
import com.starnet.cqj.taobaoke.model.MainMenu;
import com.starnet.cqj.taobaoke.model.Product;
import com.starnet.cqj.taobaoke.model.ProductSort;
import com.starnet.cqj.taobaoke.model.SearchType;
import com.starnet.cqj.taobaoke.presenter.IProductListPresenter;
import com.starnet.cqj.taobaoke.presenter.impl.ProductListPresenterImpl;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerBaseAdapter;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerSpaceDecoration;
import com.starnet.cqj.taobaoke.view.adapter.viewholder.ProductHolder;
import com.starnet.cqj.taobaoke.view.widget.expandtabview.ClassifyView;
import com.starnet.cqj.taobaoke.view.widget.expandtabview.ExpandTabView;
import com.starnet.cqj.taobaoke.view.widget.expandtabview.PriceBetweenView;
import com.starnet.cqj.taobaoke.view.widget.expandtabview.SortView;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class ProductListActivity extends BaseActivity implements IProductListPresenter.IView {


    public static final String KEY_SEARCH_TYPE = "search_type";
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
    @BindView(R.id.ll_message)
    LinearLayout mLlMessage;
    private SortView mSortView;
    private ClassifyView mClassifyView;
    private PriceBetweenView mBetweenView;

    private String typeName = "";
    private String cateId = "";
    private String maxFee = "";
    private String minFee = "";
    private RecyclerBaseAdapter<Product, ProductHolder> mAdapter;
    private IProductListPresenter mPresenter;
    private boolean mHasMore;
    private int mPage = 1;

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
        mRvProduct.setLayoutManager(new GridLayoutManager(this, 2));
        mRvProduct.addItemDecoration(new RecyclerSpaceDecoration(getResources().getDimensionPixelOffset(R.dimen.product_item_padding)));
        mAdapter = new RecyclerBaseAdapter<>(R.layout.item_product, ProductHolder.class);
        mRvProduct.setAdapter(mAdapter);
        SearchType searchType = (SearchType) getIntent().getSerializableExtra(KEY_SEARCH_TYPE);
        mPresenter = new ProductListPresenterImpl(this, searchType);
        get();
        mPresenter.getTip();
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
                        mProductExpandTab.setTitle(mainMenu.getName(), 0);
                        cateId = String.valueOf(mainMenu.getId());
                        get();
                    }
                });
        mSortView.getObservable()
                .compose(this.<ProductSort>bindToLifecycle())
                .subscribe(new Consumer<ProductSort>() {
                    @Override
                    public void accept(ProductSort productSort) throws Exception {
                        mProductExpandTab.onPressBack();
                        mProductExpandTab.setTitle(productSort.getName(), 1);
                        typeName = productSort.getId();
                        get();
                    }
                });
        mBetweenView.getObservable()
                .compose(this.<PriceBetweenView.PriceBetween>bindToLifecycle())
                .subscribe(new Consumer<PriceBetweenView.PriceBetween>() {
                    @Override
                    public void accept(PriceBetweenView.PriceBetween priceBetween) throws Exception {
                        mProductExpandTab.onPressBack();
                        maxFee = priceBetween.endPrice;
                        minFee = priceBetween.startPrice;
                        get();
                    }
                });
        mAdapter.itemClickObserve()
                .compose(this.<Product>bindToLifecycle())
                .subscribe(new Consumer<Product>() {
                    @Override
                    public void accept(Product product) throws Exception {
                        String url = product.getUrl();
                        url = URLDecoder.decode(url);
                        WebViewActivity.start(ProductListActivity.this, url);
                    }
                });
        mRvProduct.addOnScrollListener(mOnScrollListener);
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                int lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                if (mHasMore && lastPosition == recyclerView.getLayoutManager().getItemCount() - 1) {
                    mPage++;
                    if (mPresenter != null) {
                        mPresenter.getData(mPage, mEdtSearch.getText().toString(), typeName, minFee, maxFee, cateId);
                    }
                }
            }
        }
    };

    @OnClick({R.id.btn_search, R.id.btn_back_top})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_search:
                get();
                break;
            case R.id.btn_back_top:
                mRvProduct.scrollToPosition(0);
                break;
        }
    }

    private void get() {
        mPage = 1;
        if (mPresenter != null) {
            mPresenter.getData(mPage, mEdtSearch.getText().toString(), typeName, minFee, maxFee, cateId);
        }
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
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }

    @Override
    public void onBackPressed() {
        if (!mProductExpandTab.onPressBack()) {
            super.onBackPressed();
        }
    }

    @Override
    public void setResult(List<Product> productList) {
        if (mPage == 1) {
            mAdapter.setAll(productList);
        } else {
            if (productList == null || productList.isEmpty()) {
                mHasMore = false;
            }
            mAdapter.addAll(productList);
        }
    }

    @Override
    public void setTip(BuyTip tip) {
        if (tip != null
                && !TextUtils.isEmpty(tip.getContent())
                && !TextUtils.isEmpty(tip.getName())) {
            mLlMessage.setVisibility(View.VISIBLE);
            Glide.with(this).load(tip.getItempic()).into(mMessageAvatar);
            mTvMessageName.setText(tip.getName());
            mTvMessagePrice.setText(tip.getContent());
        } else {
            mLlMessage.setVisibility(View.GONE);
        }
    }

    public static void start(Context context, SearchType searchType) {
        Intent starter = new Intent(context, ProductListActivity.class);
        starter.putExtra(KEY_SEARCH_TYPE, searchType);
        context.startActivity(starter);
    }

}
