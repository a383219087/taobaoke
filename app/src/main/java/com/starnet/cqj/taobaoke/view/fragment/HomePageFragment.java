package com.starnet.cqj.taobaoke.view.fragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.menu.Baby;
import com.starnet.cqj.taobaoke.model.menu.CarAccessory;
import com.starnet.cqj.taobaoke.model.menu.Cosmetic;
import com.starnet.cqj.taobaoke.model.menu.Foods;
import com.starnet.cqj.taobaoke.model.menu.Furniture;
import com.starnet.cqj.taobaoke.model.menu.IMainMenu;
import com.starnet.cqj.taobaoke.model.menu.Machine;
import com.starnet.cqj.taobaoke.model.menu.ManClothes;
import com.starnet.cqj.taobaoke.model.menu.Shoe;
import com.starnet.cqj.taobaoke.model.menu.Underwear;
import com.starnet.cqj.taobaoke.model.menu.WomenClothes;
import com.starnet.cqj.taobaoke.view.activity.HelpCenterActivity;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerBaseAdapter;
import com.starnet.cqj.taobaoke.view.adapter.viewholder.MainMenuHolder;
import com.starnet.cqj.taobaoke.view.widget.AutoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/11/02.
 */

public class HomePageFragment extends BaseFragment {


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


    private Unbinder mUnbinder;

    public static HomePageFragment newInstance() {
        return new HomePageFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initMenu();
    }

    private void initMenu() {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 5);
        mRvMainMenu.setLayoutManager(layoutManager);
        List<IMainMenu> mainMenuList = new ArrayList<>();
        mainMenuList.add(new WomenClothes());
        mainMenuList.add(new ManClothes());
        mainMenuList.add(new Underwear());
        mainMenuList.add(new Baby());
        mainMenuList.add(new Cosmetic());
        mainMenuList.add(new Furniture());
        mainMenuList.add(new Shoe());
        mainMenuList.add(new Foods());
        mainMenuList.add(new CarAccessory());
        mainMenuList.add(new Machine());
        RecyclerBaseAdapter<IMainMenu, MainMenuHolder> adapter = new RecyclerBaseAdapter<>(R.layout.item_main_menu, MainMenuHolder.class);
        adapter.setAll(mainMenuList);
        mRvMainMenu.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @OnClick(R.id.homepage_help)
    void toHelp() {
        HelpCenterActivity.start(getActivity());
    }

    @OnClick(R.id.homepage_message)
    void toMessage() {
        HelpCenterActivity.start(getActivity());
    }

    @OnCheckedChanged(R.id.home_tab_rg)
    void onCheckChanged(RadioGroup group, @IdRes int checkedId) {
        if (checkedId == R.id.home_tab_rb1) {
            mTabContent.setVisibility(View.VISIBLE);
            mTabOther.setVisibility(View.GONE);
        } else {
            mTabContent.setVisibility(View.GONE);
            mTabOther.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @OnClick(R.id.look_buy_more)
    public void onViewClicked() {
    }
}
