package com.starnet.cqj.taobaoke.view.widget.expandtabview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.ProductSort;
import com.starnet.cqj.taobaoke.view.adapter.LinearLayoutManagerWrapper;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerBaseAdapter;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerItemDecoration;
import com.starnet.cqj.taobaoke.view.adapter.viewholder.SortHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;


public class SortView extends RelativeLayout implements ViewBaseAction {

    @BindView(R.id.rv_sort)
    RecyclerView mRvSort;
    private Context mContext;
    private RecyclerBaseAdapter<ProductSort, SortHolder> mAdapter;
    private Disposable mDisposable;
    private ProductSort mOldCheckOsort;
    private PublishSubject<ProductSort> mPublishSubject = PublishSubject.create();

    public SortView(Context context) {
        this(context, null);
    }

    public SortView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public SortView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.view_sort, this, true);
        ButterKnife.bind(this);
        setData();
        initEvent();
    }

    private void setData() {
        mAdapter = new RecyclerBaseAdapter<>(R.layout.item_sort, SortHolder.class);
        String[] sortArray = mContext.getResources().getStringArray(R.array.product_sort_array);
        String[] sortIdArray = mContext.getResources().getStringArray(R.array.product_sort_id_array);
        List<ProductSort> productSorts = new ArrayList<>();
        for (int i = 0; i < sortArray.length; i++) {
            ProductSort sort = new ProductSort();
            sort.setName(sortArray[i]);
            sort.setId(sortIdArray[i]);
            if (i == 0) {
                sort.setChecked(true);
                mOldCheckOsort = sort;
            }
            productSorts.add(sort);
        }
        mAdapter.setAll(productSorts);
        mRvSort.setLayoutManager(new LinearLayoutManagerWrapper(mContext));
        mRvSort.addItemDecoration(new RecyclerItemDecoration());
        mRvSort.setAdapter(mAdapter);
    }

    private void initEvent() {
        mAdapter.itemClickObserve()
                .doOnSubscribe(new Consumer<Disposable>() {

                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mDisposable = disposable;
                    }
                })
                .subscribe(new Consumer<ProductSort>() {
                    @Override
                    public void accept(ProductSort productSort) throws Exception {
                        mPublishSubject.onNext(productSort);
                        mOldCheckOsort.setChecked(false);
                        mAdapter.update(mOldCheckOsort);
                        productSort.setChecked(true);
                        mAdapter.update(productSort);
                        mOldCheckOsort = productSort;
                    }
                });
    }

    public Observable<ProductSort> getObservable() {
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
