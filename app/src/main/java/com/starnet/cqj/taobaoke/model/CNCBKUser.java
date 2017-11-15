package com.starnet.cqj.taobaoke.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mini on 17/11/15.
 */

public class CNCBKUser {

    @SerializedName("username")
    private String name;
    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
