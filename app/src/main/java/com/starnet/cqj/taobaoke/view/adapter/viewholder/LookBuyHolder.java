package com.starnet.cqj.taobaoke.view.adapter.viewholder;

import android.graphics.Paint;
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

/**
 * Created by Administrator on 2017/11/09.
 */

public class LookBuyHolder extends BaseHolder<Product> {

    @BindView(R.id.iv_product)
    ImageView mIvProduct;
    @BindView(R.id.tv_product_name)
    TextView mTvProductName;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_origin_price)
    TextView mTvOriginPrice;
    @BindView(R.id.tv_discounts)
    TextView mTvDiscounts;

    public LookBuyHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(List<Product> data, int position, IParamContainer container, PublishSubject<Product> itemClick) {
        Product product = data.get(position);
        if (product != null) {
            Glide.with(mItemView.getContext())
                    .load(product.getItempic())
                    .into(mIvProduct);
            mTvProductName.setText(product.getTitle());
            mTvPrice.setText(product.getPrice());
            mTvOriginPrice.setText(String.format("￥%s", product.getOrigin_price()));
            mTvOriginPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            mTvDiscounts.setText(String.format("立减%s元", product.getCoupon_fee()));
        }
    }
}
