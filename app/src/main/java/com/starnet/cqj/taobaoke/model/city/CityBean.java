package com.starnet.cqj.taobaoke.model.city;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class CityBean implements Serializable ,ICityData {

    private boolean isChoose;
    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public int getProvince_id() {
        return province_id;
    }

    public void setProvince_id(int province_id) {
        this.province_id = province_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getId() {
        return city_id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isChoose() {
        return isChoose;
    }

    @Override
    public void setChoose(boolean isChoose) {
        this.isChoose =isChoose;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvincecode() {
        return provincecode;
    }

    public void setProvincecode(String provincecode) {
        this.provincecode = provincecode;
    }

    //构造方法
    public CityBean(String city_id, int province_id, String name) {
        this.city_id = city_id;
        this.province_id = province_id;
        this.name = name;
    }

    @SerializedName("id")
    private String city_id;
    @SerializedName("parent_id")
    private int province_id;
    private String code;
    @SerializedName("name")
    private String name;
    private String provincecode;

}