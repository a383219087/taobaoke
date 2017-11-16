package com.starnet.cqj.taobaoke.model;

import java.io.Serializable;
import java.net.URLDecoder;

/**
 * Created by mini on 17/11/16.
 */

public class WechatUser implements Serializable{

    private String mobile;

    private String openid;

    private String nickname;
    private String password;
    private String password_confirm;
    private String avatar;

    private String unionId;
    private String gender;
    private String code;
    private String is_create;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOpenid() {
        return openid == null ? "" : URLDecoder.decode(openid);
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_confirm() {
        return password_confirm;
    }

    public void setPassword_confirm(String password_confirm) {
        this.password_confirm = password_confirm;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIs_create() {
        return is_create;
    }

    public void setIs_create(String is_create) {
        this.is_create = is_create;
    }
}
