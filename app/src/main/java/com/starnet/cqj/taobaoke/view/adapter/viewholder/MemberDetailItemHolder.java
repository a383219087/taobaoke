package com.starnet.cqj.taobaoke.view.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.MemberDetailItem;
import com.starnet.cqj.taobaoke.view.adapter.BaseHolder;
import com.starnet.cqj.taobaoke.view.adapter.IParamContainer;

import java.util.List;

import butterknife.BindView;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Administrator on 2018/01/14.
 */

public class MemberDetailItemHolder extends BaseHolder<MemberDetailItem> {

    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_score)
    TextView mTvScore;
    @BindView(R.id.tv_status)
    TextView mTvStatus;


    public MemberDetailItemHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(List<MemberDetailItem> data, int position, IParamContainer container, PublishSubject<MemberDetailItem> itemClick) {
        MemberDetailItem memberDetailItem = data.get(position);
        if (memberDetailItem != null) {
            mTvTime.setText(memberDetailItem.getCtime());
            mTvScore.setText(memberDetailItem.getScore());
            String status = memberDetailItem.getStatus();
            mTvStatus.setText(status);
            if ("未确认".equals(status)) {
                mTvStatus.setTextColor(itemView.getResources().getColor(R.color.main_color));
            } else {
                mTvStatus.setTextColor(itemView.getResources().getColor(R.color.gray));
            }
        }
    }
}
