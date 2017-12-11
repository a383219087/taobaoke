package com.starnet.cqj.taobaoke.view.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.Medal;
import com.starnet.cqj.taobaoke.view.adapter.BaseHolder;
import com.starnet.cqj.taobaoke.view.adapter.IParamContainer;

import java.util.List;

import butterknife.BindView;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by mini on 17/11/18.
 */

public class MedalItemHolder extends BaseHolder<Medal> {


    @BindView(R.id.iv_medal)
    ImageView mIvMedal;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.ll_medal_item)
    LinearLayout mLlMedalItem;
    @BindView(R.id.tv_grade)
    TextView mTvGrade;

    public MedalItemHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(List<Medal> data, int position, IParamContainer container, final PublishSubject<Medal> itemClick) {
        final Medal medal = data.get(position);
        if (medal != null) {
            mTvName.setText(medal.getName());
            Glide.with(itemView.getContext())
                    .load(medal.getPic())
                    .into(mIvMedal);
            if("0".equals(medal.getIsGet())){
                mLlMedalItem.setSelected(false);
                mTvTime.setText("未获得");
                mTvGrade.setVisibility(View.GONE);
            }else{
                mLlMedalItem.setSelected(true);
                mTvTime.setText(medal.getTime());
                mTvGrade.setVisibility(View.VISIBLE);
                mTvGrade.setText(medal.getGrade());
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClick.onNext(medal);
                }
            });
        }
    }
}
