package com.starnet.cqj.taobaoke.model.menu;

import com.starnet.cqj.taobaoke.R;

public class Machine implements IMainMenu {


    @Override
    public String getName() {
        return "数码家电";
    }

    @Override
    public int getDrawable() {
        return R.drawable.main_menu_10;
    }

    @Override
    public void onClick() {

    }
}
