package com.starnet.cqj.taobaoke.view.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.ProductSort;
import com.starnet.cqj.taobaoke.view.adapter.BaseHolder;
import com.starnet.cqj.taobaoke.view.adapter.IParamContainer;

import java.util.List;

import butterknife.BindView;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Administrator on 2017/11/03.
 */

public class SortHolder extends BaseHolder<ProductSort> {

    @BindView(R.id.tv_sort_name)
    TextView mTvSortName;
    @BindView(R.id.iv_sort_check)
    ImageView mIvSortCheck;

    private View mItemView;

    public SortHolder(View itemView) {
        super(itemView);
        mItemView = itemView;
    }

    @Override
    public void bind(List<ProductSort> data, int position, IParamContainer container, final PublishSubject<ProductSort> itemClick) {
        final ProductSort sort = data.get(position);
        if (sort != null) {
            mTvSortName.setText(sort.getName());
            if (sort.isChecked()) {
                mTvSortName.setSelected(true);
                mIvSortCheck.setVisibility(View.VISIBLE);
            } else {
                mTvSortName.setSelected(false);
                mIvSortCheck.setVisibility(View.GONE);
            }
            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClick.onNext(sort);
                }
            });
        }
    }
}
