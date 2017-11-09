package com.starnet.cqj.taobaoke.view.adapter.viewholder;

import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.MainMenu;
import com.starnet.cqj.taobaoke.view.adapter.BaseHolder;
import com.starnet.cqj.taobaoke.view.adapter.IParamContainer;

import java.util.List;

import butterknife.BindView;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by mini on 17/11/2.
 */

public class ClassifyHolder extends BaseHolder<MainMenu> {


    private static final String KEY_OLD_SELECT_BTN = "old_select_btn";


    @BindView(R.id.tv_item_classify)
    AppCompatButton mTvItemClassify;

    public ClassifyHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(List<MainMenu> data, int position, final IParamContainer container, final PublishSubject<MainMenu> itemClick) {
        final MainMenu value = data.get(position);
        mTvItemClassify.setText(value.getName());
        mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (container.get(KEY_OLD_SELECT_BTN) != null && container.get(KEY_OLD_SELECT_BTN) instanceof View) {
                    View oldView = (View) container.get(KEY_OLD_SELECT_BTN);
                    oldView.setSelected(false);
                }
                view.setSelected(true);
                container.set(KEY_OLD_SELECT_BTN, view);
                itemClick.onNext(value);
            }
        });
    }
}
