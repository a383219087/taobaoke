package com.starnet.cqj.taobaoke.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/11/09.
 */

public class ResultWrapper<T> {

    @SerializedName("page")
    private int page;

    @SerializedName("list")
    private List<T> mProductList;

    @SerializedName("count")
    private String count;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

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
