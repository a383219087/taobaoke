package com.starnet.cqj.taobaoke.model;

import com.google.gson.annotations.SerializedName;

public class Product {

    private String id;//商品ID
    @SerializedName("orgin_price")
    private String origin_price;//原始价格
    @SerializedName("price")
    private String price;//券后价格
    @SerializedName("itemid")
    private String itemid;//淘宝ID
    @SerializedName("coupon_fee")
    private String coupon_fee;//优惠券面值
    @SerializedName("url")
    private String url;//拿券地址
    @SerializedName("sell")
    private int sell;//销售数量
    @SerializedName("istmall")
    private int istmall;//是否天猫1是0否
    @SerializedName("score")
    private String score;//可获得的积分
    @SerializedName("icon")
    private String icon;//天猫或淘宝小图标
    @SerializedName("itempic")
    private String itempic;//商品图片
    @SerializedName("title")
    private String title;//商品标题

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrigin_price() {
        return origin_price;
    }

    public void setOrigin_price(String origin_price) {
        this.origin_price = origin_price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getCoupon_fee() {
        return coupon_fee;
    }

    public void setCoupon_fee(String coupon_fee) {
        this.coupon_fee = coupon_fee;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSell() {
        return sell;
    }

    public void setSell(int sell) {
        this.sell = sell;
    }

    public int getIstmall() {
        return istmall;
    }

    public void setIstmall(int istmall) {
        this.istmall = istmall;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getItempic() {
        return itempic;
    }

    public void setItempic(String itempic) {
        this.itempic = itempic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
