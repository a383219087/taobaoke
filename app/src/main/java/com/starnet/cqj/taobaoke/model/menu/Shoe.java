package com.starnet.cqj.taobaoke.model.menu;

import com.starnet.cqj.taobaoke.R;

public class Shoe implements IMainMenu {


    @Override
    public String getName() {
        return "鞋包配饰";
    }

    @Override
    public int getDrawable() {
        return R.drawable.main_menu_7;
    }

    @Override
    public void onClick() {

    }
}

