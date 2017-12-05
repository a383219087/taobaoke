package com.starnet.cqj.taobaoke.view.adapter.viewholder;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.Order;
import com.starnet.cqj.taobaoke.model.SubOrder;
import com.starnet.cqj.taobaoke.view.adapter.BaseHolder;
import com.starnet.cqj.taobaoke.view.adapter.IParamContainer;
import com.starnet.cqj.taobaoke.view.adapter.LinearLayoutManagerWrapper;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerBaseAdapter;
import com.starnet.cqj.taobaoke.view.widget.SharePopupWindow;

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
    @BindView(R.id.tv_share)
    TextView mTvShare;
    private final RecyclerBaseAdapter<SubOrder, SubOrderHolder> mAdapter;

    public OrderHolder(View itemView) {
        super(itemView);
        mRvSubOrder.setLayoutManager(new LinearLayoutManagerWrapper(itemView.getContext()));
        mAdapter = new RecyclerBaseAdapter<>(R.layout.item_sub_order, SubOrderHolder.class);
        mRvSubOrder.setAdapter(mAdapter);
    }

    @Override
    public void bind(List<Order> data, int position, IParamContainer container, PublishSubject<Order> itemClick) {
        final Order order = data.get(position);
        if (order != null) {
            mAdapter.setAll(order.getSubList());
            mTvId.setText(order.getOrderId());
            mTvScore.setText(order.getCredit());
            mTvStatus.setText(order.getOrderStatus());
            mTvPrice.setText(order.getOrderFee());
            if ("1".equals(order.getIsShare())) {
                mTvShare.setSelected(true);
                mTvShare.setText("已分享");
                mTvShare.setClickable(false);
            } else {
                mTvShare.setSelected(false);
                mTvShare.setText("分享");
                mTvShare.setClickable(true);
            }
            mTvShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("1".equals(order.getIsShare())) {
                        Toast.makeText(itemView.getContext(), "该订单已分享", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    SharePopupWindow sharePopupWindow = new SharePopupWindow(itemView.getContext());
                    sharePopupWindow.setOrderId(order.getOrderId());
                    Activity activity = (Activity) itemView.getContext();
                    sharePopupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                }
            });
        }
    }
}
