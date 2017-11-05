package com.starnet.cqj.taobaoke.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.MainMenu;
import com.starnet.cqj.taobaoke.presenter.IHomePagePresenter;
import com.starnet.cqj.taobaoke.presenter.impl.HomePagePresenterImpl;
import com.starnet.cqj.taobaoke.view.activity.HelpCenterActivity;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerBaseAdapter;
import com.starnet.cqj.taobaoke.view.adapter.viewholder.MainMenuHolder;
import com.starnet.cqj.taobaoke.view.widget.AutoScrollViewPager;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/02.
 */

public class HomePageFragment extends BaseFragment implements IHomePagePresenter.IView{


    @BindView(R.id.home_search_edit)
    EditText mHomeSearchEdit;
    @BindView(R.id.main_auto_banner)
    AutoScrollViewPager mMainAutoBanner;
    @BindView(R.id.message_avatar)
    ImageView mMessageAvatar;
    @BindView(R.id.tv_message)
    TextView mTvMessage;
    @BindView(R.id.main_ll_message)
    LinearLayout mMainLlMessage;
    @BindView(R.id.tab_other)
    LinearLayout mTabOther;
    @BindView(R.id.main_ll_content)
    LinearLayout mTabContent;
    @BindView(R.id.rv_main_menu)
    RecyclerView mRvMainMenu;
    @BindView(R.id.main_medium_banner)
    AutoScrollViewPager mMainMediumBanner;
    @BindView(R.id.forestall_link)
    ImageView mForestallLink;
    @BindView(R.id.discounts_link)
    ImageView mDiscountsLink;
    @BindView(R.id.brand_link)
    ImageView mBrandLink;
    @BindView(R.id.must_buy_link)
    ImageView mMustBuyLink;
    @BindView(R.id.look_buy_rv)
    RecyclerView mLookBuyRv;
    @BindView(R.id.rv_goods_recommend)
    RecyclerView mRvGoodsRecommend;
    @BindView(R.id.look_buy_more)
    TextView mLookBuyMore;
    @BindView(R.id.home_tab_rg)
    RadioGroup mHomeTabRg;

    private RecyclerBaseAdapter<MainMenu, MainMenuHolder> mAdapter;
    private IHomePagePresenter mHomePagePresenter;


    public static HomePageFragment newInstance() {
        return new HomePageFragment();
    }


    @Override
    int getContentRes() {
        return R.layout.fragment_homepage;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHomePagePresenter = new HomePagePresenterImpl(this);
        initMenu();
        initEvent();
    }

    private void initMenu() {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 5);
        mRvMainMenu.setLayoutManager(layoutManager);
        mAdapter = new RecyclerBaseAdapter<>(R.layout.item_main_menu, MainMenuHolder.class);
        mRvMainMenu.setAdapter(mAdapter);
        mHomePagePresenter.getCategory();
    }

    private void initEvent() {
        mHomeTabRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.home_tab_rb1) {
                    mTabContent.setVisibility(View.VISIBLE);
                    mTabOther.setVisibility(View.GONE);
                } else {
                    mTabContent.setVisibility(View.GONE);
                    mTabOther.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @OnClick(R.id.homepage_help)
    void toHelp() {
        HelpCenterActivity.start(getActivity());
    }

    @OnClick(R.id.homepage_message)
    void toMessage() {
        HelpCenterActivity.start(getActivity());
    }



    @OnClick(R.id.look_buy_more)
    public void onViewClicked() {
    }

    @Override
    public void setCategoryList(List<MainMenu> mainMenuList) {
        mAdapter.setAll(mainMenuList);
    }

    @Override
    public void toast(String res) {
        Toast.makeText(getActivity(), res, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toast(@StringRes int res) {
        Toast.makeText(getActivity(), res, Toast.LENGTH_SHORT).show();
    }
}
