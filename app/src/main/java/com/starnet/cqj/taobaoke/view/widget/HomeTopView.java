package com.starnet.cqj.taobaoke.view.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.Banner;
import com.starnet.cqj.taobaoke.model.BuyTip;
import com.starnet.cqj.taobaoke.model.HomePageBanner;
import com.starnet.cqj.taobaoke.model.MainMenu;
import com.starnet.cqj.taobaoke.model.Product;
import com.starnet.cqj.taobaoke.model.SearchType;
import com.starnet.cqj.taobaoke.presenter.IHomePagePresenter;
import com.starnet.cqj.taobaoke.presenter.impl.HomePagePresenterImpl;
import com.starnet.cqj.taobaoke.remote.Constant;
import com.starnet.cqj.taobaoke.view.activity.ProductListActivity;
import com.starnet.cqj.taobaoke.view.activity.WebViewActivity;
import com.starnet.cqj.taobaoke.view.adapter.LinearLayoutManagerWrapper;
import com.starnet.cqj.taobaoke.view.adapter.MyViewPagerAdapter;
import com.starnet.cqj.taobaoke.view.adapter.NoScrollGridLayoutManager;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerBaseAdapter;
import com.starnet.cqj.taobaoke.view.adapter.viewholder.LookBuyHolder;
import com.starnet.cqj.taobaoke.view.adapter.viewholder.MainMenuHolder;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2017/11/22.
 */

public class HomeTopView extends LinearLayout implements IHomePagePresenter.IView{
    @BindView(R.id.main_auto_banner)
    AutoScrollViewPager mMainAutoBanner;
    @BindView(R.id.home_tab_rg)
    RadioGroup mHomeTabRg;
    @BindView(R.id.message_avatar)
    ImageView mMessageAvatar;
    @BindView(R.id.tv_message_name)
    TextView mTvMessageName;
    @BindView(R.id.tv_message_content)
    TextView mTvMessageContent;
    @BindView(R.id.main_ll_message)
    LinearLayout mMainLlMessage;
    @BindView(R.id.tab_other)
    LinearLayout mTabOther;
    @BindView(R.id.rv_main_menu)
    RecyclerView mRvMainMenu;
    @BindView(R.id.main_medium_banner)
    AutoScrollViewPager mMainMediumBanner;
    @BindView(R.id.look_buy_rv)
    RecyclerView mLookBuyRv;
    @BindView(R.id.main_ll_content)
    LinearLayout mMainLlContent;


    private RecyclerBaseAdapter<MainMenu, MainMenuHolder> mMenuAdapter;
    private IHomePagePresenter mHomePagePresenter;
    private RecyclerBaseAdapter<Product, LookBuyHolder> mLookBuyAdapter;
    private Callback mCallback;

    public HomeTopView(Context context) {
        super(context);
        init();
    }

    public HomeTopView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HomeTopView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        LayoutInflater.from(getContext()).inflate(R.layout.view_home_top, this, true);
        ButterKnife.bind(this);
        mHomePagePresenter = new HomePagePresenterImpl(this);
        initMenu();
        mLookBuyAdapter = new RecyclerBaseAdapter<>(R.layout.item_look_buy, LookBuyHolder.class);
        mLookBuyRv.setLayoutManager(new LinearLayoutManagerWrapper(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mLookBuyRv.setAdapter(mLookBuyAdapter);
        mLookBuyRv.setFocusableInTouchMode(false);
        mLookBuyRv.setFocusable(false);
        mLookBuyRv.clearFocus();
        initEvent();
    }

    private void initEvent() {
        mHomeTabRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.home_tab_rb1) {
                    mMainLlContent.setVisibility(View.VISIBLE);
                    mTabOther.setVisibility(View.GONE);
                    mCallback.tabChange(true);
                } else {
                    mMainLlContent.setVisibility(View.GONE);
                    mTabOther.setVisibility(View.VISIBLE);
                    mCallback.tabChange(false);
                }
            }
        });
        mMenuAdapter.itemClickObserve()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {

                    }
                })
                .subscribe(new Consumer<MainMenu>() {
                    @Override
                    public void accept(MainMenu mainMenu) throws Exception {
                        ProductListActivity.start(getContext(), mainMenu);
                    }
                });

        mLookBuyAdapter.itemClickObserve()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {

                    }
                })
                .subscribe(new Consumer<Product>() {
                    @Override
                    public void accept(Product product) throws Exception {
                        String url = product.getUrl();
                        url = URLDecoder.decode(url);
                        WebViewActivity.start(getContext(), url);
                    }
                });
    }

    private void initMenu() {
        GridLayoutManager layoutManager = new NoScrollGridLayoutManager(getContext(), 5);
        mRvMainMenu.setLayoutManager(layoutManager);
        mMenuAdapter = new RecyclerBaseAdapter<>(R.layout.item_main_menu, MainMenuHolder.class);
        mRvMainMenu.setAdapter(mMenuAdapter);
        mRvMainMenu.setFocusableInTouchMode(false);
        mRvMainMenu.setFocusable(false);
        mRvMainMenu.clearFocus();
    }

    public void getData(){
        if(mHomePagePresenter!=null){
            mHomePagePresenter.getBanner();
        }
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    @OnClick(R.id.look_buy_more)
    public void onViewClicked() {
        ProductListActivity.start(getContext(), SearchType.VIDEO);
    }

    @OnClick({R.id.ll_tqg, R.id.ll_jhs, R.id.ll_ppq, R.id.ll_bmqd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_tqg:
                ProductListActivity.start(getContext(), SearchType.TQG);
                break;
            case R.id.ll_jhs:
                ProductListActivity.start(getContext(), SearchType.JHS);
                break;
            case R.id.ll_ppq:
                ProductListActivity.start(getContext(), SearchType.PPQ);
                break;
            case R.id.ll_bmqd:
                ProductListActivity.start(getContext(), SearchType.BMQD);
                break;
        }
    }

    @Override
    public void toast(String res) {
        mCallback.refreshDone(false);
        Toast.makeText(getContext(), res, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toast(@StringRes int res) {
        mCallback.refreshDone(false);
        Toast.makeText(getContext(), res, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setBanner(HomePageBanner banner) {
        List<View> viewList = new ArrayList<>();
        List<Banner> bannerTop = banner.getTop();
        for (int i = 0; i < bannerTop.size(); i++) {
            ImageView iv = new ImageView(getContext());
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            final Banner item = bannerTop.get(i);
            Glide.with(getContext()).load(item.getPic()).into(iv);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    WebViewActivity.start(getContext(), item.getUrl());
                }
            });
            viewList.add(iv);
        }
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(viewList);
        mMainAutoBanner.setAdapter(adapter);
        mMainAutoBanner.startAutoScroll(Constant.BANNER_AUTO_TIME);

        List<View> viewList2 = new ArrayList<>();
        List<Banner> bannerMid = banner.getMid();
        for (int i = 0; i < bannerMid.size(); i++) {
            ImageView iv = new ImageView(getContext());
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            final Banner item = bannerMid.get(i);
            Glide.with(getContext()).load(item.getPic()).into(iv);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    WebViewActivity.start(getContext(), item.getUrl());
                }
            });
            viewList2.add(iv);
        }
        MyViewPagerAdapter adapter2 = new MyViewPagerAdapter(viewList2);
        mMainMediumBanner.setAdapter(adapter2);
        mMainMediumBanner.startAutoScroll(Constant.BANNER_AUTO_TIME);
        mHomePagePresenter.getCategory();
    }

    @Override
    public void setCategoryList(List<MainMenu> mainMenuList) {
        mMenuAdapter.setAll(mainMenuList);
        mHomePagePresenter.getLookBuy();
    }

    @Override
    public void setLookBuy(List<Product> productList) {
        mLookBuyAdapter.setAll(productList);
        mCallback.refreshDone(true);
        mHomePagePresenter.getTip();
    }

    @Override
    public void setTip(BuyTip tip) {
        if (tip != null) {
            mMainLlMessage.setVisibility(View.VISIBLE);
            mTvMessageName.setText(tip.getName());
            mTvMessageContent.setText(tip.getContent());
            Glide.with(getContext()).load(tip.getItempic()).into(mMessageAvatar);
        }
    }

    @Override
    public void setRecommend(List<Product> productList) {

    }

    public interface Callback{
        void refreshDone(boolean success);

        void tabChange(boolean isShow);
    }
}
