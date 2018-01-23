package com.starnet.cqj.taobaoke.model;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

/**
 * 申请详情
 * Created by cqj on 2018/01/08.
 */

public class StoreManagerApplyDetail {

    private String contact;
    private String phone;
    private String remark;
    @SerializedName("shop_type")
    private String shopType;

    @SerializedName("area_type")
    private String areaType;
    private String province;

    private String city;
    private String area;

    public String getAreaType() {
        return areaType;
    }

    public void setAreaType(String areaType) {
        this.areaType = areaType;
    }

    public String getProvince() {
        return TextUtils.isEmpty(province) ? "" : province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return TextUtils.isEmpty(city) ? "" : city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return TextUtils.isEmpty(area) ? "" : area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }
}
