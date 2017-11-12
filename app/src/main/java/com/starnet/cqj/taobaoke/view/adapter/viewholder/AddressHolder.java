package com.starnet.cqj.taobaoke.view.adapter.viewholder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.Address;
import com.starnet.cqj.taobaoke.view.adapter.BaseHolder;
import com.starnet.cqj.taobaoke.view.adapter.IParamContainer;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by mini on 17/11/5.
 */

public class AddressHolder extends BaseHolder<Address> {

    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.cb_default)
    CheckBox mCbDefault;
    private PublishSubject<Address> mItemClick;
    private Address mAddress;

    public AddressHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(List<Address> data, int position, IParamContainer container, PublishSubject<Address> itemClick) {
        mItemClick = itemClick;
        Address address = data.get(position);
        mAddress = address;
        if (address != null) {
            mTvName.setText(address.getName());
            mTvPhone.setText(address.getPhone());
            mTvAddress.setText(address.getArea() + "," + address.getAddress());
            mCbDefault.setChecked(address.getIsDefault().equals("1"));
            mCbDefault.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!mCbDefault.isChecked()) {
                        mCbDefault.setChecked(true);
                    } else {
                        mAddress.setEditDefault(true);
                        mItemClick.onNext(mAddress);
                    }
                }
            });
        }
    }

    @OnClick({R.id.iv_delete, R.id.iv_edit})
    public void onViewClicked(View view) {
        if (mItemClick == null || mAddress == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.iv_delete:
                mAddress.setDelete(true);
                mItemClick.onNext(mAddress);
                break;
            case R.id.iv_edit:
                mAddress.setEdit(true);
                mItemClick.onNext(mAddress);
                break;
        }
    }
}
