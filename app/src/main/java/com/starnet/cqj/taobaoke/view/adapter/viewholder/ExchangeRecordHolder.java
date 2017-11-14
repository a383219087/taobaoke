package com.starnet.cqj.taobaoke.view.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.ExchangeRecord;
import com.starnet.cqj.taobaoke.view.adapter.BaseHolder;
import com.starnet.cqj.taobaoke.view.adapter.IParamContainer;

import java.util.List;

import butterknife.BindView;
import io.reactivex.subjects.PublishSubject;


public class ExchangeRecordHolder extends BaseHolder<ExchangeRecord> {


    @BindView(R.id.iv_pic)
    ImageView mIvPic;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_score)
    TextView mTvScore;
    @BindView(R.id.tv_id)
    TextView mTvId;
    @BindView(R.id.tv_time)
    TextView mTvTime;


    public ExchangeRecordHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(List<ExchangeRecord> data, int position, IParamContainer container, PublishSubject<ExchangeRecord> itemClick) {
        ExchangeRecord exchangeRecord = data.get(position);
        if (exchangeRecord != null) {
            Glide.with(itemView.getContext())
                    .load(exchangeRecord.getPic())
                    .into(mIvPic);
            mTvTime.setText(String.format("下单时间：%s", exchangeRecord.getTime()));
            mTvTitle.setText(exchangeRecord.getTitle());
            mTvScore.setText(String.format("%s积分", exchangeRecord.getScore()));
            mTvId.setText(String.format("订单编号：%s", exchangeRecord.getId()));

        }
    }
}
