package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.oragee.banners.BannerView;
import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.Article;
import com.starnet.cqj.taobaoke.model.Banner;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.ResultWithBanner;
import com.starnet.cqj.taobaoke.model.ResultWrapper;
import com.starnet.cqj.taobaoke.remote.Constant;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;
import com.starnet.cqj.taobaoke.view.adapter.LinearLayoutManagerWrapper;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerBaseAdapter;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerItemDecoration;
import com.starnet.cqj.taobaoke.view.adapter.viewholder.QuestionListHolder;
import com.starnet.cqj.taobaoke.view.widget.RecyclerViewLoadMoreHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HelpCenterActivity extends BaseActivity {

    @BindView(R.id.vp_banner)
    BannerView mVpBanner;

    @BindView(R.id.help_btn_general_question)
    TextView mTvGeneralQuestion;

    @BindView(R.id.help_btn_general_question_iv)
    ImageView mIvGeneralQuestion;

    @BindView(R.id.help_btn_integral_question)
    TextView mTvIntegralQuestion;

    @BindView(R.id.help_btn_integral_question_iv)
    ImageView mIvIntegralQuestion;

    @BindView(R.id.help_btn_how_shopping)
    TextView mTvHowShopping;

    @BindView(R.id.help_btn_how_shopping_iv)
    ImageView mIvHowShopping;

    @BindView(R.id.help_btn_other)
    TextView mTvOther;

    @BindView(R.id.help_btn_other_iv)
    ImageView mIvOther;

    @BindView(R.id.wrong_list)
    RecyclerView mRecyclerView;


    private RecyclerBaseAdapter<Article, QuestionListHolder> mAdapter;
    private int mType;
    private RecyclerViewLoadMoreHelper mHelper;

    @Override
    protected void init() {
        setTitleName(R.string.help_center_title);
        mTvGeneralQuestion.setSelected(true);
        mIvGeneralQuestion.setSelected(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManagerWrapper(this));
        mAdapter = new RecyclerBaseAdapter<>(R.layout.item_question, QuestionListHolder.class);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new RecyclerItemDecoration());
        mHelper = new RecyclerViewLoadMoreHelper();
        initData();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_help_center;
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mRecyclerView.addOnScrollListener(mHelper.getOnScrollListener());
        mAdapter.itemClickObserve()
                .compose(this.<Article>bindToLifecycle())
                .subscribe(new Consumer<Article>() {
                    @Override
                    public void accept(Article helpArticle) throws Exception {
                        WebViewActivity.start(HelpCenterActivity.this, Constant.HELP_DETAIL_PREFIX + helpArticle.getId());
                    }
                });
        mHelper.setLoadMoreCallback(new RecyclerViewLoadMoreHelper.LoadMoreCallback() {
            @Override
            public void loadMore() {
                getDataWithPage(mHelper.getPage(), mType);
            }
        });
    }



    private void clearAllColor() {
        mTvGeneralQuestion.setSelected(false);
        mIvGeneralQuestion.setSelected(false);
        mTvIntegralQuestion.setSelected(false);
        mIvIntegralQuestion.setSelected(false);
        mTvHowShopping.setSelected(false);
        mIvHowShopping.setSelected(false);
        mTvOther.setSelected(false);
        mIvOther.setSelected(false);
    }

    @OnClick(R.id.ll_general)
    void generalQuestion() {
        clearAllColor();
        mTvGeneralQuestion.setSelected(true);
        mIvGeneralQuestion.setSelected(true);
        if (mType != 0) {
            mType = 0;
            initData();
        }
    }

    @OnClick(R.id.ll_integral)
    void integralQuestion() {
        clearAllColor();
        mTvIntegralQuestion.setSelected(true);
        mIvIntegralQuestion.setSelected(true);
        if (mType != 1) {
            mType = 1;
            initData();
        }
    }

    @OnClick(R.id.ll_how_shopping)
    void howShopping() {
        clearAllColor();
        mTvHowShopping.setSelected(true);
        mIvHowShopping.setSelected(true);
        if (mType != 2) {
            mType = 2;
            initData();
        }
    }

    @OnClick(R.id.ll_other)
    void other() {
        clearAllColor();
        mTvOther.setSelected(true);
        mIvOther.setSelected(true);
        if (mType != 3) {
            mType = 3;
            initData();
        }
    }

    private void initData() {
        mHelper.resetPage();
        RemoteDataSourceBase.INSTANCE.getCommonService()
                .getHelpList(mType)
                .compose(this.<JsonCommon<ResultWithBanner<Article>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JsonCommon<ResultWithBanner<Article>>>() {
                    @Override
                    public void accept(JsonCommon<ResultWithBanner<Article>> productResultJsonCommon) throws Exception {
                        if ("200".equals(productResultJsonCommon.getCode())) {
                            mAdapter.setAll(productResultJsonCommon.getData().getList());
                            setBanner(productResultJsonCommon.getData().getBannerList());
                        } else {
                            toast(productResultJsonCommon.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }

    private void setBanner(List<Banner> bannerList) {
        List<View> viewList = new ArrayList<>();
        for (int i = 0; i < bannerList.size(); i++) {
            final Banner banner = bannerList.get(i);
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(this)
                    .load(banner.getPic())
                    .into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    WebViewActivity.start(HelpCenterActivity.this, Constant.HELP_DETAIL_PREFIX + banner.getId());
                }
            });
            viewList.add(imageView);
        }
        mVpBanner.setViewList(viewList);
        mVpBanner.startLoop(true);

    }

    private void getDataWithPage(int page, int type) {
        RemoteDataSourceBase.INSTANCE.getCommonService()
                .getHelpListPage(page, type)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(this.<JsonCommon<ResultWrapper<Article>>>bindToLifecycle())
                .subscribe(new Consumer<JsonCommon<ResultWrapper<Article>>>() {
                    @Override
                    public void accept(JsonCommon<ResultWrapper<Article>> listJsonCommon) throws Exception {
                        if ("200".equals(listJsonCommon.getCode())) {
                            List<Article> list = listJsonCommon.getData().getList();
                            mHelper.setNoMore(list == null || list.isEmpty());
                            mAdapter.addAll(list);
                        } else {
                            toast(listJsonCommon.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, HelpCenterActivity.class);
        context.startActivity(starter);
    }
}
