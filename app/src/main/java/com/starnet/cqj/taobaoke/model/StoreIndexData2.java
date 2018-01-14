package com.starnet.cqj.taobaoke.model;

import com.google.gson.annotations.SerializedName;

/**
 * 累计收益部分
 * Created by JohnChen on 2018/01/14.
 */

public class StoreIndexData2 {

    @SerializedName("users_num")
    private String userNum;

    @SerializedName("order_num")
    private String orderNum;

    private String score;

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
