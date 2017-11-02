package com.starnet.cqj.taobaoke.view.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.Message;
import com.starnet.cqj.taobaoke.view.adapter.BaseHolder;
import com.starnet.cqj.taobaoke.view.adapter.IParamContainer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Administrator on 2017/11/02.
 */

public class MessageHolder extends BaseHolder<Message> {

    @BindView(R.id.tv_message_title)
    TextView mTvMessageTitle;
    @BindView(R.id.tv_message_detail)
    TextView mTvMessageDetail;
    @BindView(R.id.iv_message_new)
    ImageView mIvMessageNew;
    @BindView(R.id.tv_message_delete)
    TextView mTvMessageDelete;
    private View mItemView;

    public MessageHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mItemView = itemView;
    }

    @Override
    public void bind(List<Message> data, int position, IParamContainer container, final PublishSubject<Message> itemClick) {
        final Message message = data.get(position);
        if (message != null) {
            mTvMessageTitle.setText(message.getTitle());
            mTvMessageDetail.setText(message.getDetail());
            if (message.isNew()) {
                mIvMessageNew.setVisibility(View.VISIBLE);
            } else {
                mIvMessageNew.setVisibility(View.GONE);
            }
            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClick.onNext(message);
                }
            });
            mTvMessageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    message.setDelete(true);
                    itemClick.onNext(message);
                }
            });
        }
    }
}
