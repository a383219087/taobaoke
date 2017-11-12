package com.starnet.cqj.taobaoke.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by mini on 17/11/5.
 */

public class Address implements Serializable {

    private String id;
    private String name;

    private String address;

    private String area;
    @SerializedName("is_default")
    private String isDefault;
    private String phone;
    private boolean isEdit;
    private boolean isDelete;

    private boolean isEditDefault;

    public boolean isEditDefault() {
        return isEditDefault;
    }

    public void setEditDefault(boolean editDefault) {
        isEditDefault = editDefault;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public String getID() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Address)) {
            return false;
        }
        Address address = (Address) obj;
        return id != null && address.getID() != null && address.getID().equals(id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
