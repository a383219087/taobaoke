package com.starnet.cqj.taobaoke.model;

import com.google.gson.annotations.SerializedName;

/**
 * 统计bean
 * Created by johnChen on 2018/01/10.
 */

public class Statistics {

    @SerializedName("users_num")
    private String userNum;


    @SerializedName("date")
    private String date;


    @SerializedName("order_num")
    private String orderNum;


    @SerializedName("score")
    private String score;

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
