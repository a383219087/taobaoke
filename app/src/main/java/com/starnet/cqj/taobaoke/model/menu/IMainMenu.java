package com.starnet.cqj.taobaoke.model.menu;

import android.support.annotation.DrawableRes;

/**
 * Created by Administrator on 2017/11/02.
 */

public interface IMainMenu {

    String getName();

    @DrawableRes int getDrawable();

    void onClick();
}
