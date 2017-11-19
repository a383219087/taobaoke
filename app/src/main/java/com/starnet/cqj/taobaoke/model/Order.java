package com.starnet.cqj.taobaoke.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mini on 17/11/19.
 */

public class Order {

    @SerializedName("order_id")
    private String orderId;

    @SerializedName("orderstatus")
    private String orderStatus;

    @SerializedName("ctime")
    private String time;
    @SerializedName("credit")
    private String credit;

    @SerializedName("sub_list")
    private List<SubOrder> subList;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public List<SubOrder> getSubList() {
        return subList;
    }

    public void setSubList(List<SubOrder> subList) {
        this.subList = subList;
    }
}
