package com.starnet.cqj.taobaoke.utils;

/**
 * Created by Administrator on 2018/01/08.
 */

public enum StoreManagerType {

    GOLD(1),
    SILVER(2),
    CUPRUM(3);


    private int mValue;

    StoreManagerType(int value) {
        mValue = value;
    }

    public int getValue() {
        return mValue;
    }
}
