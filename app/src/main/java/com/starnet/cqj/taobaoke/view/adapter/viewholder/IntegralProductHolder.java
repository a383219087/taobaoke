package com.starnet.cqj.taobaoke.view.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.IntegralProduct;
import com.starnet.cqj.taobaoke.view.adapter.BaseHolder;
import com.starnet.cqj.taobaoke.view.adapter.IParamContainer;

import java.util.List;

import butterknife.BindView;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by mini on 17/11/11.
 */

public class IntegralProductHolder extends BaseHolder<IntegralProduct> {

    @BindView(R.id.iv_product)
    ImageView mIvProduct;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_score)
    TextView mTvScore;

    public IntegralProductHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(List<IntegralProduct> data, int position, IParamContainer container, final PublishSubject<IntegralProduct> itemClick) {
        final IntegralProduct product = data.get(position);
        if(product!=null){
            Glide.with(mItemView.getContext())
                    .load(product.getPic())
                    .into(mIvProduct);
            mTvTitle.setText(product.getTitle());
            mTvScore.setText(String.format("%s积分", product.getScore()));
            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClick.onNext(product);
                }
            });
        }
    }
}
