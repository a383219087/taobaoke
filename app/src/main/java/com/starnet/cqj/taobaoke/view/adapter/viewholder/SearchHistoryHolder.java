package com.starnet.cqj.taobaoke.view.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.view.adapter.BaseHolder;
import com.starnet.cqj.taobaoke.view.adapter.IParamContainer;

import java.util.List;

import butterknife.BindView;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Administrator on 2017/11/21.
 */

public class SearchHistoryHolder extends BaseHolder<String> {

    @BindView(R.id.item_text)
    TextView mItemText;

    public SearchHistoryHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(List<String> data, int position, IParamContainer container, final PublishSubject<String> itemClick) {
        final String text = data.get(position);
        mItemText.setText(text);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.onNext(text);
            }
        });
    }
}
