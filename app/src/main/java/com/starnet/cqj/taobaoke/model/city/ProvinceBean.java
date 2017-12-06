package com.starnet.cqj.taobaoke.model.city;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class ProvinceBean implements Serializable {

    public String getPro_id() {
        return pro_id;
    }

    public void setPro_id(String pro_id) {
        this.pro_id = pro_id;
    }

    public String getPro_code() {
        return pro_code;
    }

    public void setPro_code(String pro_code) {
        this.pro_code = pro_code;
    }

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }

    public String getPro_name2() {
        return pro_name2;
    }

    public void setPro_name2(String pro_name2) {
        this.pro_name2 = pro_name2;
    }

    public  ProvinceBean(String pro_id,String pro_name) {
        this.pro_id = pro_id;
        this.pro_name = pro_name;

    }

    @SerializedName("id")
    private String pro_id;//省份id
    private String pro_code;//城市编码
    @SerializedName("name")
    private String pro_name;//省份名称
    private String pro_name2;//省份别称
}