package com.starnet.cqj.taobaoke.model.city;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class AreaBean implements Serializable, ICityData {

    private boolean isChoose;

    public int getAreaid() {
        return areaid;
    }

    public void setAreaid(int areaid) {
        this.areaid = areaid;
    }

    public int getCityid() {
        return cityid;
    }

    public void setCityid(int cityid) {
        this.cityid = cityid;
    }

    @Override
    public String getId() {
        return cityid + "";
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
        this.isChoose = isChoose;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public AreaBean(int areaid, int cityid, String name, String citycode) {
        this.areaid = areaid;
        this.citycode = citycode;
        this.cityid = cityid;
        this.name = name;
    }

    @SerializedName("id")
    private int areaid;//地区id

    @SerializedName("parent_id")
    private int cityid;//城市id

    @SerializedName("name")
    private String name;//名称
    private String citycode;//城市编码
}