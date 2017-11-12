package com.starnet.cqj.taobaoke.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/11/09.
 */

public class ProductResult<T> {

    @SerializedName("page")
    private int page;

    @SerializedName("list")
    private List<T> mProductList;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<T> getList() {
        return mProductList;
    }

    public void setList(List<T> productList) {
        mProductList = productList;
    }
}
