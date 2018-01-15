package com.starnet.cqj.taobaoke.view.adapter.viewholder;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.Account;
import com.starnet.cqj.taobaoke.view.activity.AddAccountActivity;
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
        final Account account = data.get(position);
        if (account != null) {
            final boolean isDefault = "1".equals(account.getStatus());
            mCbDefault.setChecked(isDefault);
            mTvUserMsg.setText(account.getName() + " " + account.getUserName());
            mTvType.setText("1".equals(account.getType()) ? "支付宝" : "银行卡-" + account.getBank());
            mIvEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddAccountActivity.start(itemView.getContext(),account);
                }
            });
            mIvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isDefault){
                        Toast.makeText(itemView.getContext(), "默认账户无法删除。", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    showDeleteDialog(account);

                }
            });
            mCbDefault.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!mCbDefault.isChecked()) {
                        mCbDefault.setChecked(true);
                    } else {

                    }
                }
            });
        }
    }
    private void showDeleteDialog(final Account account) {
        AlertDialog alertDialog = new AlertDialog.Builder(itemView.getContext())
                .setTitle("提示")
                .setMessage("确定要删除该账户吗？")
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
                    }
                })
                .create();
        alertDialog.show();
    }
}
