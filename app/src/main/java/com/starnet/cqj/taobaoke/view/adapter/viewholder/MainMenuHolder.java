package com.starnet.cqj.taobaoke.view.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.menu.IMainMenu;
import com.starnet.cqj.taobaoke.view.adapter.BaseHolder;
import com.starnet.cqj.taobaoke.view.adapter.IParamContainer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.subjects.PublishSubject;


public class MainMenuHolder extends BaseHolder<IMainMenu> {

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
    public void bind(List<IMainMenu> data, int position, IParamContainer container, PublishSubject<IMainMenu> itemClick) {
        final IMainMenu mainMenu = data.get(position);
        if (mainMenu != null) {
            mMenuIcon.setImageResource(mainMenu.getDrawable());
            mMenuName.setText(mainMenu.getName());
            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainMenu.onClick();
                }
            });
        }
    }
}
