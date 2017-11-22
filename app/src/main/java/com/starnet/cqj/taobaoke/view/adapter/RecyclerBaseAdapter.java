package com.starnet.cqj.taobaoke.view.adapter;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by cqj on 2017-05-22.
 */
public class RecyclerBaseAdapter<T, VH extends BaseHolder<T>> extends RecyclerView.Adapter<VH>
        implements IParamContainer {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;

    private View mHeaderView;
    @LayoutRes
    private int mLayoutId;
    private Class<VH> mClazz;
    private List<T> mDataList;
    private PublishSubject<T> mItemClick = PublishSubject.create();

    public RecyclerBaseAdapter(@LayoutRes int layoutId, Class<VH> clazz) {
        mLayoutId = layoutId;
        mClazz = clazz;
        mDataList = new ArrayList<>();
    }

    public RecyclerBaseAdapter(View headerView, @LayoutRes int layoutId, Class<VH> clazz) {
        mHeaderView = headerView;
        mLayoutId = layoutId;
        mClazz = clazz;
        mDataList = new ArrayList<>();
    }

    public void add(T data) {
        if (data == null) {
            return;
        }
        mDataList.add(data);
        notifyItemInserted(getItemCount() + 1);
    }

    public void insert(T data) {
        if (data == null) {
            return;
        }
        if (mDataList.size() == 0) {
            mDataList.add(data);
        } else {
            mDataList.set(0, data);
        }
        notifyItemInserted(mHeaderView == null ? 0 : 1);
    }

    public void addAll(List<T> datas) {
        if (datas == null || datas.isEmpty()) {
            return;
        }
        int positionStart = getItemCount();
        mDataList.addAll(datas);
        notifyItemRangeInserted(positionStart,datas.size());
    }

    public void setAll(List<T> datas) {
        if (datas == null) {
            return;
        }
        mDataList.clear();
        mDataList.addAll(datas);
        notifyDataSetChanged();
    }

    public void update(T data) {
        int indexOf = mDataList.indexOf(data);
        if (indexOf != -1) {
            mDataList.set(indexOf, data);
        }
        notifyItemChanged(indexOf);
    }

    public void remove(int position) {
        if (position < 0 || position >= getItemCount()) {
            return;
        }
        mDataList.remove(position);
        notifyItemRemoved(position);
    }

    public List<T> getDataList() {
        return mDataList;
    }

    public void remove(T data) {
        if (data == null) {
            return;
        }
        int position = mDataList.indexOf(data);
        mDataList.remove(data);
        notifyItemRemoved(position);
    }

    public void removeAll() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    public Observable<T> itemClickObserve() {
        return mItemClick.throttleFirst(500, TimeUnit.MILLISECONDS);
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            view = mHeaderView;
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        }
        VH vh = null;
        try {
            Constructor<VH> csr = mClazz.getConstructor(View.class);  //调用有参构造
            vh = csr.newInstance(view);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return vh;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            final int originSpanCount = gridLayoutManager.getSpanCount();

            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int viewType = getItemViewType(position);
                    if (viewType == TYPE_HEADER) {
                        return originSpanCount;
                    }
                    return 1;
                }
            });
            gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());
        }
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) return;
        final int pos = getRealPosition(holder);
        holder.bind(mDataList, pos, this, mItemClick);
    }

    private int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? mDataList.size() : mDataList.size() + 1;
    }

    private final HashMap<String, Object> paramContainerMap = new HashMap<>();

    @Override
    public Object get(String key) {
        return paramContainerMap.get(key);
    }

    @Override
    public void set(String key, Object object) {
        paramContainerMap.put(key, object);
    }
}
