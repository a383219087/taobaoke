package com.starnet.cqj.taobaoke.view.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.Message;
import com.starnet.cqj.taobaoke.view.adapter.BaseHolder;
import com.starnet.cqj.taobaoke.view.adapter.IParamContainer;

import java.util.List;

import butterknife.BindView;
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
    @BindView(R.id.ll_message_click)
    LinearLayout mLlClick;

    public MessageHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(List<Message> data, int position, IParamContainer container, final PublishSubject<Message> itemClick) {
        final Message message = data.get(position);
        if (message != null) {
            mTvMessageTitle.setText(message.getTitle());
            mTvMessageDetail.setText(message.getTime());
            if (message.isNew()) {
                mIvMessageNew.setVisibility(View.VISIBLE);
            } else {
                mIvMessageNew.setVisibility(View.GONE);
            }
            mLlClick.setOnClickListener(new View.OnClickListener() {
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
