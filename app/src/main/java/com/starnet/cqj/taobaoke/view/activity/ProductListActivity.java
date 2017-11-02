package com.starnet.cqj.taobaoke.view.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.view.widget.expandtabview.ExpandTabView;

import butterknife.BindView;

public class ProductListActivity extends BaseActivity {


    @BindView(R.id.title_rightbutton)
    Button mTitleRightbutton;
    @BindView(R.id.home_search_edit)
    EditText mHomeSearchEdit;
    @BindView(R.id.product_expand_tab)
    ExpandTabView mProductExpandTab;
    @BindView(R.id.rv_product)
    RecyclerView mRvProduct;
    @BindView(R.id.btn_back_top)
    FloatingActionButton mBtnBackTop;
    @BindView(R.id.message_avatar)
    ImageView mMessageAvatar;
    @BindView(R.id.tv_message)
    TextView mTvMessage;
    @BindView(R.id.ll_message)
    LinearLayout mLlMessage;

    @Override
    protected int getContentView() {
        return R.layout.activity_product_list;
    }

    @Override
    protected void init() {

    }

}
