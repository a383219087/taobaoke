package com.starnet.cqj.taobaoke.model.menu;

import com.starnet.cqj.taobaoke.R;

public class Underwear  implements IMainMenu {


    @Override
    public String getName() {
        return "内衣";
    }

    @Override
    public int getDrawable() {
        return R.drawable.main_menu_3;
    }

    @Override
    public void onClick() {

    }
}