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
import butterknife.ButterKnife;
import io.reactivex.subjects.PublishSubject;


public class MainMenuHolder extends BaseHolder<MainMenu> {

    @BindView(R.id.menu_icon)
    ImageView mMenuIcon;
    @BindView(R.id.menu_name)
    TextView mMenuName;
    private View mItemView;

    public MainMenuHolder(View itemView) {
        super(itemView);
        mItemView = itemView;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind(List<MainMenu> data, int position, IParamContainer container, PublishSubject<MainMenu> itemClick) {
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

                }
            });
        }
    }
}
