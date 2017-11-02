package com.starnet.cqj.taobaoke.model.menu;

import com.starnet.cqj.taobaoke.R;

public class Cosmetic implements IMainMenu {


    @Override
    public String getName() {
        return "化妆品";
    }

    @Override
    public int getDrawable() {
        return R.drawable.main_menu_5;
    }

    @Override
    public void onClick() {

    }
}

