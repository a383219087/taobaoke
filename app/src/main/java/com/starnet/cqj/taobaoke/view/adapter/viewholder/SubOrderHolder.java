package com.starnet.cqj.taobaoke.view.adapter.viewholder;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.SubOrder;
import com.starnet.cqj.taobaoke.view.adapter.BaseHolder;
import com.starnet.cqj.taobaoke.view.adapter.IParamContainer;

import java.util.List;

import butterknife.BindView;
import io.reactivex.subjects.PublishSubject;


public class SubOrderHolder extends BaseHolder<SubOrder> {

    @BindView(R.id.iv_sub_pic)
    ImageView mIvSubPic;
    @BindView(R.id.tv_sub_title)
    TextView mTvSubTitle;
    @BindView(R.id.tv_sub_price)
    TextView mTvSubPrice;
    @BindView(R.id.tv_sub_count)
    TextView mTvSubCount;

    public SubOrderHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(List<SubOrder> data, int position, IParamContainer container, PublishSubject<SubOrder> itemClick) {
        SubOrder subOrder = data.get(position);
        if (subOrder != null) {
            String pic = subOrder.getPic();
            if (TextUtils.isEmpty(pic)) {
                mIvSubPic.setVisibility(View.GONE);
            } else {
                mIvSubPic.setVisibility(View.VISIBLE);
                Glide.with(itemView.getContext()).load(pic).into(mIvSubPic);
            }
            mTvSubTitle.setText(subOrder.getTitle());
            mTvSubCount.setText(subOrder.getCount());
            mTvSubPrice.setText(subOrder.getFee());
        }
    }
}
