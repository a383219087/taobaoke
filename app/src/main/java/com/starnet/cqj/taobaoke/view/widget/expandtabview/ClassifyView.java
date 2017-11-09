package com.starnet.cqj.taobaoke.view.widget.expandtabview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.MainMenu;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerBaseAdapter;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerSpaceDecoration;
import com.starnet.cqj.taobaoke.view.adapter.viewholder.ClassifyHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by mini on 17/11/2.
 */

public class ClassifyView extends RelativeLayout implements ViewBaseAction {


    @BindView(R.id.rv_classify)
    RecyclerView mRvClassify;

    private Context mContext;
    private PublishSubject<MainMenu> mPublishSubject = PublishSubject.create();
    private RecyclerBaseAdapter<MainMenu, ClassifyHolder> mAdapter;
    private Disposable mDisposable;

    public ClassifyView(Context context) {
        this(context, null);
    }

    public ClassifyView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ClassifyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_classify, this, true);
        ButterKnife.bind(this);
        mAdapter = new RecyclerBaseAdapter<>(R.layout.item_classify, ClassifyHolder.class);
        mRvClassify.setLayoutManager(new GridLayoutManager(context, 4));
        mRvClassify.addItemDecoration(new RecyclerSpaceDecoration(mContext.getResources().getDimensionPixelOffset(R.dimen.product_item_padding)));
        mRvClassify.setAdapter(mAdapter);
        setData();
        initEvent();
    }

    private void initEvent() {
        mAdapter.itemClickObserve()
                .doOnSubscribe(new Consumer<Disposable>() {

                    @Override
                    public void accept(Disposable disposable) throws Exception {

                        mDisposable = disposable;
                    }
                })
                .subscribe(new Consumer<MainMenu>() {
                    @Override
                    public void accept(MainMenu mainMenu) throws Exception {
                        mPublishSubject.onNext(mainMenu);
                    }
                });
    }

    private void setData() {
        RemoteDataSourceBase.INSTANCE.getHomePageService().getCategory()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<JsonCommon<List<MainMenu>>>() {
                    @Override
                    public void accept(JsonCommon<List<MainMenu>> listJsonCommon) throws Exception {
                        if (listJsonCommon.getCode().equals("200")) {
                            mAdapter.setAll(listJsonCommon.getData());
                        } else {
                            Toast.makeText(mContext, listJsonCommon.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });

    }

    public Observable<MainMenu> getObservable() {
        return mPublishSubject;
    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }

    public void onDestroy() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }
}
