package com.starnet.cqj.taobaoke.view.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.Order;
import com.starnet.cqj.taobaoke.model.SubOrder;
import com.starnet.cqj.taobaoke.view.adapter.BaseHolder;
import com.starnet.cqj.taobaoke.view.adapter.IParamContainer;
import com.starnet.cqj.taobaoke.view.adapter.LinearLayoutManagerWrapper;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerBaseAdapter;

import java.util.List;

import butterknife.BindView;
import io.reactivex.subjects.PublishSubject;


public class OrderHolder extends BaseHolder<Order> {


    @BindView(R.id.tv_id)
    TextView mTvId;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.rv_sub_order)
    RecyclerView mRvSubOrder;
    @BindView(R.id.tv_score)
    TextView mTvScore;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    private final RecyclerBaseAdapter<SubOrder, SubOrderHolder> mAdapter;

    public OrderHolder(View itemView) {
        super(itemView);
        mRvSubOrder.setLayoutManager(new LinearLayoutManagerWrapper(itemView.getContext()));
        mAdapter = new RecyclerBaseAdapter<>(R.layout.item_sub_order, SubOrderHolder.class);
        mRvSubOrder.setAdapter(mAdapter);
    }

    @Override
    public void bind(List<Order> data, int position, IParamContainer container, PublishSubject<Order> itemClick) {
        Order order = data.get(position);
        if (order != null) {
            mAdapter.setAll(order.getSubList());
            mTvId.setText(order.getOrderId());
            mTvScore.setText(order.getCredit());
            mTvStatus.setText(order.getOrderStatus());
            double price = 0;
            for (SubOrder subOrder : order.getSubList()) {
                try {
                    int i = Integer.parseInt(subOrder.getCount());
                    double fee = Double.parseDouble(subOrder.getFee());
                    price += i * fee;
                } catch (NumberFormatException ignored) {
                }
            }
            mTvPrice.setText(String.valueOf(price));
        }
    }
}
