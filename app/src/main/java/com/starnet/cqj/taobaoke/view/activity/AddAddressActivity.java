package com.starnet.cqj.taobaoke.view.activity;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.starnet.cqj.taobaoke.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by mini on 17/11/5.
 */

public class AddAddressActivity extends BaseActivity {


    @BindView(R.id.edt_receive_name)
    EditText mEdtReceiveName;
    @BindView(R.id.edt_receive_phone)
    EditText mEdtReceivePhone;
    @BindView(R.id.edt_receive_address)
    EditText mEdtReceiveAddress;
    @BindView(R.id.cb_default_address)
    CheckBox mCbDefaultAddress;
    @BindView(R.id.cd_add_address)
    CardView mCdAddAddress;
    @BindView(R.id.btn_add_address)
    Button mBtnAddAddress;

    @Override
    protected int getContentView() {
        return R.layout.activity_add_address;
    }

    @Override
    protected void init() {
        setTitleName(R.string.add_address_title);
        mCdAddAddress.setCardElevation(8);
        mCdAddAddress.setRadius(4);
    }

    @OnClick({R.id.ll_choose_area, R.id.ll_set_default, R.id.btn_add_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_choose_area:
                break;
            case R.id.ll_set_default:
                mCbDefaultAddress.setChecked(!mCbDefaultAddress.isChecked());
                break;
            case R.id.btn_add_address:
                break;
        }
    }
}
