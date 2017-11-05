package com.starnet.cqj.taobaoke.view.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.Address;
import com.starnet.cqj.taobaoke.view.adapter.LinearLayoutManagerWrapper;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerBaseAdapter;
import com.starnet.cqj.taobaoke.view.adapter.viewholder.AddressHolder;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by mini on 17/11/5.
 */

public class AddressListActivity extends BaseActivity {


    @BindView(R.id.title_rightimage)
    ImageButton mTitleRightimage;
    @BindView(R.id.rv_address)
    RecyclerView mRvAddress;

    @Override
    protected int getContentView() {
        return R.layout.activity_address_list;
    }

    @Override
    protected void init() {
        setTitleName(R.string.receive_address_title);
        mTitleRightimage.setImageResource(R.drawable.add_icon);
        mTitleRightimage.setVisibility(View.VISIBLE);
        RecyclerBaseAdapter<Address,AddressHolder> adapter = new RecyclerBaseAdapter<>(R.layout.item_address,AddressHolder.class);
        mRvAddress.setLayoutManager(new LinearLayoutManagerWrapper(this));
        mRvAddress.setAdapter(adapter);
    }

    @OnClick(R.id.title_rightimage)
    public void onViewClicked() {
    }
}
