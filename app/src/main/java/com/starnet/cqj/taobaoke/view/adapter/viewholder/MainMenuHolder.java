package com.starnet.cqj.taobaoke.view.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.MainMenu;
import com.starnet.cqj.taobaoke.view.adapter.BaseHolder;
import com.starnet.cqj.taobaoke.view.adapter.IParamContainer;

import java.util.List;

import butterknife.BindView;
import io.reactivex.subjects.PublishSubject;


public class MainMenuHolder extends BaseHolder<MainMenu> {

    @BindView(R.id.menu_icon)
    ImageView mMenuIcon;
    @BindView(R.id.menu_name)
    TextView mMenuName;

    public MainMenuHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(List<MainMenu> data, int position, IParamContainer container, final PublishSubject<MainMenu> itemClick) {
        final MainMenu mainMenu = data.get(position);
        if (mainMenu != null) {
            Glide
                    .with(itemView.getContext())
                    .load(mainMenu.getPic())
                    .into(mMenuIcon);
            mMenuName.setText(mainMenu.getName());
            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClick.onNext(mainMenu);
                }
            });
        }
    }
}
