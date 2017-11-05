package com.starnet.cqj.taobaoke.view.adapter.viewholder;

import android.view.View;

import com.starnet.cqj.taobaoke.model.Address;
import com.starnet.cqj.taobaoke.view.adapter.BaseHolder;
import com.starnet.cqj.taobaoke.view.adapter.IParamContainer;

import java.util.List;

import io.reactivex.subjects.PublishSubject;

/**
 * Created by mini on 17/11/5.
 */

public class AddressHolder extends BaseHolder<Address> {


    public AddressHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(List<Address> data, int position, IParamContainer container, PublishSubject<Address> itemClick) {

    }
}
