package com.starnet.cqj.taobaoke.view.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.Member;
import com.starnet.cqj.taobaoke.view.adapter.BaseHolder;
import com.starnet.cqj.taobaoke.view.adapter.IParamContainer;

import java.util.List;

import butterknife.BindView;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Administrator on 2018/01/09.
 */

public class MemberHolder extends BaseHolder<Member> {

    @BindView(R.id.iv_avatar)
    ImageView mIvAvatar;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_member_id)
    TextView mTvMemberId;
    @BindView(R.id.tv_register_time)
    TextView mTvRegisterTime;

    public MemberHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(List<Member> data, int position, IParamContainer container, final PublishSubject<Member> itemClick) {
        final Member member = data.get(position);
        if (member != null) {
            mTvMemberId.setText(itemView.getContext().getResources().getString(R.string.member_id_text, member.getId()));
            mTvName.setText(member.getNickName());
            mTvRegisterTime.setText(itemView.getContext().getResources().getString(R.string.member_time_text, member.getTime()));
            Glide.with(itemView.getContext())
                    .load(member.getAvatar())
                    .into(mIvAvatar);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClick.onNext(member);
                }
            });
        }
    }
}
