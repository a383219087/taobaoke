package com.starnet.cqj.taobaoke.model.menu;

import com.starnet.cqj.taobaoke.R;

public class Foods implements IMainMenu {


    @Override
    public String getName() {
        return "美食";
    }

    @Override
    public int getDrawable() {
        return R.drawable.main_menu_8;
    }

    @Override
    public void onClick() {

    }
}
