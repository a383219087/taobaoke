package com.starnet.cqj.taobaoke.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.Address;
import com.starnet.cqj.taobaoke.model.city.CityResult;
import com.starnet.cqj.taobaoke.presenter.IAddAddressPresenter;
import com.starnet.cqj.taobaoke.presenter.impl.AddAddressPresenterImpl;
import com.starnet.cqj.taobaoke.utils.AppUtils;
import com.starnet.cqj.taobaoke.view.BaseApplication;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.OnClick;


public class AddAddressActivity extends BaseActivity implements IAddAddressPresenter.IView {


    public static final String KEY_EDIT_DATA = "edit_data";
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
    @BindView(R.id.tv_choose_area)
    TextView mTvChooseArea;

    private Address mAddress;

    private OptionsPickerView pvOptions;//地址选择器
    private IAddAddressPresenter mPresenter;

    @Override
    protected int getContentView() {
        return R.layout.activity_add_address;
    }

    @Override
    protected void init() {
        setTitleName(R.string.add_address_title);
        mCdAddAddress.setCardElevation(8);
        mCdAddAddress.setRadius(4);
        mPresenter = new AddAddressPresenterImpl(this);
        Serializable extra = getIntent().getSerializableExtra(KEY_EDIT_DATA);
        if (extra != null && extra instanceof Address) {
            mAddress = (Address) extra;
            mBtnAddAddress.setText("修改");
        } else {
            mAddress = new Address();
            mBtnAddAddress.setText("添加");
        }
        mEdtReceiveName.setText(mAddress.getName());
        mEdtReceivePhone.setText(mAddress.getPhone());
        mEdtReceiveAddress.setText(mAddress.getAddress());
        mTvChooseArea.setText(mAddress.getArea());
        mCbDefaultAddress.setChecked("1".equals(mAddress.getIsDefault()));
        initCity();
    }

    private void initCity() {
        //选项选择器
        pvOptions = new OptionsPickerView(this);
        // 获取数据库
        mPresenter.initCity(getApplication());

    }

    @OnClick({R.id.ll_choose_area, R.id.ll_set_default, R.id.btn_add_address, R.id.tv_choose_area})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_choose_area:
            case R.id.ll_choose_area:
                AppUtils.hideInput(this);
                if (pvOptions != null) {
                    pvOptions.show();
                }
                break;
            case R.id.ll_set_default:
                mCbDefaultAddress.setChecked(!mCbDefaultAddress.isChecked());
                break;
            case R.id.btn_add_address:
                String name = mEdtReceiveName.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    toast(R.string.please_input_name);
                    return;
                }
                String phone = mEdtReceivePhone.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    toast(R.string.please_input_phone);
                    return;
                }
                String detailAddress = mEdtReceiveAddress.getText().toString();
                if (TextUtils.isEmpty(detailAddress)) {
                    toast(R.string.please_input_detail_address);
                    return;
                }
                String area = mTvChooseArea.getText().toString();
                if (area.equals(getResources().getString(R.string.please_choose))) {
                    toast("请选择所在区域");
                    return;
                }
                mAddress.setName(name);
                mAddress.setPhone(phone);
                mAddress.setArea(area);
                mAddress.setAddress(detailAddress);
                mAddress.setIsDefault(mCbDefaultAddress.isChecked() ? "1" : "0");
                mPresenter.addAddress(((BaseApplication) getApplication()).token, mAddress);
                break;
        }
    }

    @Override
    public void onInitCity(final CityResult cityResult) {
        //设置三级联动效果
        pvOptions.setPicker(cityResult.Provincestr, cityResult.Citystr, cityResult.Areastr, true);
        //设置选择的三级单位
//        pvOptions.setLabels("省", "市", "区");
        pvOptions.setTitle("选择城市");
        //设置是否循环滚动
        pvOptions.setCyclic(false, false, false);
        //设置默认选中的三级项目
        //监听确定选择按钮
        pvOptions.setSelectOptions(0, 0, 0);
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String tx = cityResult.options1Items.get(options1).getPro_name() + ","
                        + cityResult.options2Items.get(options1).get(option2).getName() + ","
                        + cityResult.options3Items.get(options1).get(option2).get(options3).getName();
                mTvChooseArea.setText(tx);
            }
        });
    }

    @Override
    public void onAddAddress() {
        if(TextUtils.isEmpty(mAddress.getID())) {
            toast("添加成功");
        }else{
            toast("修改成功");
        }
        setResult(RESULT_OK);
        finish();
    }

    public static void startForResult(Activity activity, int requestCode) {
        Intent starter = new Intent(activity, AddAddressActivity.class);
        activity.startActivityForResult(starter, requestCode);
    }

    public static void startForResult(Activity activity, Address address, int requestCode) {
        Intent starter = new Intent(activity, AddAddressActivity.class);
        starter.putExtra(KEY_EDIT_DATA, address);
        activity.startActivityForResult(starter, requestCode);
    }
}
