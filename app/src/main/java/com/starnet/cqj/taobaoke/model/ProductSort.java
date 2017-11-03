package com.starnet.cqj.taobaoke.model;

/**
 * Created by Administrator on 2017/11/03.
 */

public class ProductSort {

    private int id;
    private String name;
    private boolean isChecked;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ProductSort)) {
            return false;
        }
        ProductSort sort = (ProductSort) obj;
        return sort.id == id;
    }

    @Override
    public int hashCode() {
        return id + name.hashCode();
    }
}
