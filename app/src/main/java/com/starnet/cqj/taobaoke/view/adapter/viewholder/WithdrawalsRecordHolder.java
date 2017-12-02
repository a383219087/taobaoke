package com.starnet.cqj.taobaoke.view.adapter.viewholder;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.WithdrawalsRecord;
import com.starnet.cqj.taobaoke.view.adapter.BaseHolder;
import com.starnet.cqj.taobaoke.view.adapter.IParamContainer;

import java.util.List;

import butterknife.BindView;
import io.reactivex.subjects.PublishSubject;


public class WithdrawalsRecordHolder extends BaseHolder<WithdrawalsRecord> {


    @Nullable
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @Nullable
    @BindView(R.id.tv_cncbk)
    TextView mTvCncbk;
    @Nullable
    @BindView(R.id.tv_remark)
    TextView mTvRemark;

    public WithdrawalsRecordHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(List<WithdrawalsRecord> data, int position, IParamContainer container, PublishSubject<WithdrawalsRecord> itemClick) {
        WithdrawalsRecord withdrawalsRecord = data.get(position);
        if (withdrawalsRecord != null && mTvTime != null) {
            mTvTime.setText(withdrawalsRecord.getTime());
            mTvCncbk.setText(withdrawalsRecord.getScore());
            mTvRemark.setText(withdrawalsRecord.getRemark());
        }
    }
}
