package com.starnet.cqj.taobaoke.model.city;

/**
 * Created by Administrator on 2017/12/26.
 */

public interface ICityData {

    String getId();
    String getName();

    boolean isChoose();

    void setChoose(boolean isChoose);
}
