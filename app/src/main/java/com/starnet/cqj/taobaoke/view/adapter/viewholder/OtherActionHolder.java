package com.starnet.cqj.taobaoke.view.adapter.viewholder;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.OtherAction;
import com.starnet.cqj.taobaoke.view.adapter.BaseHolder;
import com.starnet.cqj.taobaoke.view.adapter.IParamContainer;

import java.util.List;

import butterknife.BindView;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Administrator on 2017/12/15.
 */

public class OtherActionHolder extends BaseHolder<OtherAction> {

    @Nullable
    @BindView(R.id.iv_other_action)
    ImageView mImageView;

    public OtherActionHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(List<OtherAction> data, int position, IParamContainer container, final PublishSubject<OtherAction> itemClick) {
        if (mImageView == null) {
            return;
        }
        final OtherAction otherAction = data.get(position);
        if (otherAction != null) {
            Glide.with(itemView.getContext())
                    .load(otherAction.getPic())
                    .into(mImageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClick.onNext(otherAction);
                }
            });
        }
    }
}
