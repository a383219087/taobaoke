package com.starnet.cqj.taobaoke.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable{

    @SerializedName("token")
    private String token;
    private String nickname;
    private String mobile;
    private float credit1;
    private float credit2;
    private float credit3;
    private String avatar;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public float getCredit1() {
        return credit1;
    }

    public void setCredit1(float credit1) {
        this.credit1 = credit1;
    }

    public float getCredit2() {
        return credit2;
    }

    public void setCredit2(float credit2) {
        this.credit2 = credit2;
    }

    public float getCredit3() {
        return credit3;
    }

    public void setCredit3(float credit3) {
        this.credit3 = credit3;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
