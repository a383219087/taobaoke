package com.starnet.cqj.taobaoke.view.adapter.viewholder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.Account;
import com.starnet.cqj.taobaoke.view.adapter.BaseHolder;
import com.starnet.cqj.taobaoke.view.adapter.IParamContainer;

import java.util.List;

import butterknife.BindView;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Administrator on 2018/01/14.
 */

public class AccountHolder extends BaseHolder<Account> {

    @BindView(R.id.iv_type)
    ImageView mIvType;
    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.tv_user_msg)
    TextView mTvUserMsg;
    @BindView(R.id.cb_default)
    CheckBox mCbDefault;
    @BindView(R.id.iv_delete)
    ImageView mIvDelete;
    @BindView(R.id.iv_edit)
    ImageView mIvEdit;

    public AccountHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(List<Account> data, int position, IParamContainer container, PublishSubject<Account> itemClick) {
        Account account = data.get(position);
        if (account != null) {
            mCbDefault.setChecked("1".equals(account.getStatus()));
            mTvUserMsg.setText(account.getName() + " " + account.getUserName());
            mTvType.setText("1".equals(account.getType()) ? "支付宝" : "银行卡-" + account.getBank());
        }
    }
}
