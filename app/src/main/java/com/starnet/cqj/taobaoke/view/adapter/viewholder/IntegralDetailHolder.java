package com.starnet.cqj.taobaoke.view.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.IntegralDetail;
import com.starnet.cqj.taobaoke.view.adapter.BaseHolder;
import com.starnet.cqj.taobaoke.view.adapter.IParamContainer;

import java.util.List;

import butterknife.BindView;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by mini on 17/11/19.
 */

public class IntegralDetailHolder extends BaseHolder<IntegralDetail> {


    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_score)
    TextView mTvScore;
    @BindView(R.id.tv_remark)
    TextView mTvRemark;

    public IntegralDetailHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(List<IntegralDetail> data, int position, IParamContainer container, PublishSubject<IntegralDetail> itemClick) {
        IntegralDetail integralDetail = data.get(position);
        if (integralDetail != null) {
            mTvTime.setText(integralDetail.getTime());
            mTvScore.setText(String.format("%s白积分", integralDetail.getScore()));
            mTvRemark.setText(integralDetail.getRemark());
        }
    }
}
