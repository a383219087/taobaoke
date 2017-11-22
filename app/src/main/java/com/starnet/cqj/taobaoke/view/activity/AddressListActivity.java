package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.Address;
import com.starnet.cqj.taobaoke.presenter.IAddressListPresenter;
import com.starnet.cqj.taobaoke.presenter.impl.AddressListPresenterImpl;
import com.starnet.cqj.taobaoke.view.BaseApplication;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerBaseAdapter;
import com.starnet.cqj.taobaoke.view.adapter.viewholder.AddressHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * Created by mini on 17/11/5.
 */

public class AddressListActivity extends BaseActivity implements IAddressListPresenter.IView {


    public static final int REQUEST_CODE = 101;
    @BindView(R.id.title_rightimage)
    ImageButton mTitleRightimage;
    @BindView(R.id.rv_address)
    RecyclerView mRvAddress;

    private IAddressListPresenter mPresenter;
    private RecyclerBaseAdapter<Address, AddressHolder> mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_address_list;
    }

    @Override
    protected void init() {
        setTitleName(R.string.receive_address_title);
        mTitleRightimage.setImageResource(R.drawable.add_icon);
        mTitleRightimage.setVisibility(View.VISIBLE);
        mAdapter = new RecyclerBaseAdapter<>(R.layout.item_address, AddressHolder.class);
        mRvAddress.setLayoutManager(new LinearLayoutManager(this));
        mRvAddress.setAdapter(mAdapter);
        mPresenter = new AddressListPresenterImpl(this);
        refresh();
    }

    private void refresh() {
        if (mPresenter != null) {
            mPresenter.getAddress(((BaseApplication) getApplication()).getToken());
        }
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mAdapter.itemClickObserve()
                .compose(this.<Address>bindToLifecycle())
                .subscribe(new Consumer<Address>() {
                    @Override
                    public void accept(Address address) throws Exception {
                        if (address.isDelete()) {
                            showDeleteDialog(address);
                        } else if (address.isEdit()) {
                            AddAddressActivity.startForResult(AddressListActivity.this, address, REQUEST_CODE);
                        } else if (address.isEditDefault()) {
                            address.setIsDefault("1");
                            mPresenter.editDefault(((BaseApplication) getApplication()).getToken(), address);
                        }
                    }
                });
    }

    private void showDeleteDialog(final Address address) {
        AlertDialog alertDialog = new AlertDialog.Builder(AddressListActivity.this)
                .setTitle("提示")
                .setMessage("确定要删除该收货地址吗？")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        mPresenter.deleteAddress(((BaseApplication) getApplication()).getToken(), address);
                    }
                })
                .create();
        alertDialog.show();
    }

    @OnClick(R.id.title_rightimage)
    public void onViewClicked() {
        AddAddressActivity.startForResult(this, REQUEST_CODE);
    }

    @Override
    public void onGetAddressDone(List<Address> addressList) {
        mAdapter.setAll(addressList);
    }

    @Override
    public void onDelete(Address address) {
        toast("删除成功");
        refresh();
    }

    @Override
    public void onEditDefault() {
        toast("修改成功");
        refresh();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                refresh();
            }
        }
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, AddressListActivity.class);
        context.startActivity(starter);
    }
}
