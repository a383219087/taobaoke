package com.starnet.cqj.taobaoke.view.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.city.ICityData;
import com.starnet.cqj.taobaoke.view.adapter.BaseHolder;
import com.starnet.cqj.taobaoke.view.adapter.IParamContainer;
import com.starnet.cqj.taobaoke.view.adapter.RecyclerBaseAdapter;

import java.util.List;

import butterknife.BindView;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Administrator on 2017/12/26.
 */

public class PickerDataHolder extends BaseHolder<ICityData> {

    public static final String KEY_LAST_CHOOSE = "last_choose";
    @BindView(R.id.tv_picker)
    TextView mTvPicker;

    public PickerDataHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(List<ICityData> data, int position, final IParamContainer container, final PublishSubject<ICityData> itemClick) {
        final ICityData pickerViewData = data.get(position);
        if (pickerViewData != null) {
            mTvPicker.setText(pickerViewData.getName());
            mTvPicker.setSelected(pickerViewData.isChoose());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClick.onNext(pickerViewData);
                    Object lastChoose = container.get(KEY_LAST_CHOOSE);
                    if (lastChoose != null && lastChoose instanceof ICityData) {
                        ((ICityData) lastChoose).setChoose(false);
                    }
                    pickerViewData.setChoose(true);
                    container.set(KEY_LAST_CHOOSE, pickerViewData);
                    Object o = container.get(RecyclerBaseAdapter.KEY_ADAPTER);
                    if (o != null && o instanceof RecyclerBaseAdapter) {
                        ((RecyclerBaseAdapter) o).notifyDataSetChanged();
                    }
                }
            });
        }

    }
}
