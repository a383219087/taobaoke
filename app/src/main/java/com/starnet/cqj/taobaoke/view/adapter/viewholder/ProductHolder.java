package com.starnet.cqj.taobaoke.view.adapter.viewholder;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.Product;
import com.starnet.cqj.taobaoke.view.adapter.BaseHolder;
import com.starnet.cqj.taobaoke.view.adapter.IParamContainer;

import java.util.List;

import butterknife.BindView;
import io.reactivex.subjects.PublishSubject;


public class ProductHolder extends BaseHolder<Product> {

    @Nullable
    @BindView(R.id.tv_discounts)
    TextView mTvDiscounts;
    @Nullable
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @Nullable
    @BindView(R.id.tv_origin_price)
    TextView mTvOriginPrice;
    @Nullable
    @BindView(R.id.tv_sell_count)
    TextView mTvSellCount;
    @Nullable
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @Nullable
    @BindView(R.id.tv_award)
    TextView mTvAward;
    @Nullable
    @BindView(R.id.iv_product)
    ImageView mIvProduct;

    public ProductHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(List<Product> data, int position, IParamContainer container, final PublishSubject<Product> itemClick) {
        try {
            final Product product = data.get(position);
            if (product != null && mTvDiscounts != null) {
                Glide.with(mItemView.getContext())
                        .load(product.getItempic())
                        .into(mIvProduct);
                mTvDiscounts.setText(product.getCoupon_fee());
                mTvAward.setText(product.getScore());
                mTvTitle.setText(product.getTitle());
                mTvOriginPrice.setText(product.getOrigin_price());
                mTvPrice.setText(product.getPrice());
                mTvSellCount.setText(String.valueOf(product.getSell()));
                mItemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        itemClick.onNext(product);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
