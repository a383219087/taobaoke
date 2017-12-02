package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.Order;
import com.starnet.cqj.taobaoke.model.ResultWrapper;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;
import com.starnet.cqj.taobaoke.view.BaseApplication;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerBaseAdapter;
import com.starnet.cqj.taobaoke.view.adapter.viewholder.OrderHolder;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class OrderListActivity extends BaseActivity {


    @BindView(R.id.title_rightbutton)
    Button mTitleRightbutton;
    @BindView(R.id.btn_search)
    Button mBtnSearch;
    @BindView(R.id.edt_search)
    EditText mEdtSearch;
    @BindView(R.id.rv_order)
    RecyclerView mRvOrder;
    private RecyclerBaseAdapter<Order, OrderHolder> mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_order_list;
    }

    @Override
    protected void init() {
        setTitleName(R.string.my_order_title);
        mTitleRightbutton.setVisibility(View.VISIBLE);
        mTitleRightbutton.setText("绑定订单");
        mAdapter = new RecyclerBaseAdapter<>(R.layout.item_order, OrderHolder.class);
        mRvOrder.setLayoutManager(new LinearLayoutManager(this));
        mRvOrder.setAdapter(mAdapter);
        getData();
    }

    private void getData() {
        RemoteDataSourceBase.INSTANCE.getUserService()
                .getOrder(((BaseApplication) getApplication()).getToken(), mEdtSearch.getText().toString())
                .compose(this.<JsonCommon<ResultWrapper<Order>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JsonCommon<ResultWrapper<Order>>>() {
                    @Override
                    public void accept(JsonCommon<ResultWrapper<Order>> resultWrapperJsonCommon) throws Exception {
                        if ("200".equals(resultWrapperJsonCommon.getCode())) {
                            mAdapter.setAll(resultWrapperJsonCommon.getData().getList());
                        } else {
                            toast(resultWrapperJsonCommon.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }

    @OnClick({R.id.title_rightbutton, R.id.btn_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_rightbutton:
                showDialog();
                break;
            case R.id.btn_search:
                getData();
                break;
        }
    }

    private void showDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_relation_order, null, false);
        final EditText edtOrderId = (EditText) view.findViewById(R.id.edt_order_id);
        Button btnRelation = (Button) view.findViewById(R.id.btn_relation);
        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(view)
                .create();
        alertDialog.show();
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        btnRelation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String orderId = edtOrderId.getText().toString().trim();
                if (TextUtils.isEmpty(orderId)) {
                    toast("请输入正确的订单号");
                    return;
                }
                RemoteDataSourceBase.INSTANCE.getUserService()
                        .bindOrder(((BaseApplication) getApplication()).getToken(), orderId)
                        .compose(OrderListActivity.this.<JsonCommon<Object>>bindToLifecycle())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Consumer<JsonCommon<Object>>() {
                            @Override
                            public void accept(JsonCommon<Object> objectJsonCommon) throws Exception {
                                if ("200".equals(objectJsonCommon.getCode())) {
                                    toast("关联成功");
                                    getData();
                                } else {
                                    toast(objectJsonCommon.getMessage());
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                throwable.printStackTrace();
                            }
                        });

            }
        });
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, OrderListActivity.class);
        context.startActivity(starter);
    }

}
