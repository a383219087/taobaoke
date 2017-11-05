package com.starnet.cqj.taobaoke.presenter;

import android.support.annotation.StringRes;

/**
 * Created by mini on 17/11/4.
 */

public interface BasePresenter {

    void onDestroy();

    interface IView {
        void toast(String res);

        void toast(@StringRes int res);
    }
}
