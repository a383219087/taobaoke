package com.starnet.cqj.taobaoke.model;


import com.google.gson.annotations.SerializedName;

public class BuyAction {

    private String name;

    private String stime;

    private String etime;

    //需要的订单总数
    private String orders;
    //奖励的积分
    private String score;
    //已完成订单数
    private String count;

    private String msg;

    private String url;

    @SerializedName("is_over")
    private String isOver;

    @SerializedName("is_receive")
    private String isReceive;

    public boolean isOver() {
        return "1".equals(isOver);
    }

    public void setIsOver(String isOver) {
        this.isOver = isOver;
    }

    public boolean isReceive() {
        return "1".equals(isReceive);
    }

    public void setIsReceive(String isReceive) {
        this.isReceive = isReceive;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
