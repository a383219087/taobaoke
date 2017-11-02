package com.starnet.cqj.taobaoke.model.menu;

import com.starnet.cqj.taobaoke.R;

public class Furniture implements IMainMenu {


    @Override
    public String getName() {
        return "家居";
    }

    @Override
    public int getDrawable() {
        return R.drawable.main_menu_6;
    }

    @Override
    public void onClick() {

    }
}
