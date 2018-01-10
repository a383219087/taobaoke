package com.starnet.cqj.taobaoke.view.adapter.viewholder;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.Statistics;
import com.starnet.cqj.taobaoke.view.adapter.BaseHolder;
import com.starnet.cqj.taobaoke.view.adapter.IParamContainer;

import java.util.List;

import butterknife.BindView;
import io.reactivex.subjects.PublishSubject;

/**
 * 统计holder
 * Created by JohnChen on 2018/01/10.
 */

public class StatisticsHolder extends BaseHolder<Statistics> {

    @Nullable
    @BindView(R.id.tv_date)
    TextView mTvDate;
    @Nullable
    @BindView(R.id.tv_user_count)
    TextView mTvUserCount;
    @Nullable
    @BindView(R.id.tv_order_count)
    TextView mTvOrderCount;
    @Nullable
    @BindView(R.id.tv_score)
    TextView mTvScore;

    public StatisticsHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(List<Statistics> data, int position, IParamContainer container, PublishSubject<Statistics> itemClick) {
        Statistics statistics = data.get(position);
        if (statistics != null && mTvDate != null) {
            mTvDate.setText(statistics.getDate());
            mTvUserCount.setText(statistics.getUserNum());
            mTvOrderCount.setText(statistics.getOrderNum());
            mTvScore.setText(statistics.getScore());
        }
    }
}
