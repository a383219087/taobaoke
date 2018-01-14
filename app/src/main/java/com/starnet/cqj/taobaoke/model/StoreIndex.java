package com.starnet.cqj.taobaoke.model;

import com.google.gson.annotations.SerializedName;

/**
 * 店长首页
 * Created by JohnChen on 2018/01/14.
 */

public class StoreIndex {

    @SerializedName("shop_type")
    private String type;

    @SerializedName("status")
    private String status;

    private String province;

    private String city;

    private String area;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @SerializedName("data1")
    private StoreIndexData1 data1;


    @SerializedName("data2")
    private StoreIndexData2 data2;


    @SerializedName("data3")
    private StoreIndexData2 data3;

    @SerializedName("data4")
    private StoreIndexData2 data4;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public StoreIndexData1 getData1() {
        return data1;
    }

    public void setData1(StoreIndexData1 data1) {
        this.data1 = data1;
    }

    public StoreIndexData2 getData2() {
        return data2;
    }

    public void setData2(StoreIndexData2 data2) {
        this.data2 = data2;
    }

    public StoreIndexData2 getData3() {
        return data3;
    }

    public void setData3(StoreIndexData2 data3) {
        this.data3 = data3;
    }

    public StoreIndexData2 getData4() {
        return data4;
    }

    public void setData4(StoreIndexData2 data4) {
        this.data4 = data4;
    }
}
