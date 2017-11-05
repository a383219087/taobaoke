package com.starnet.cqj.taobaoke.model;

import java.util.List;

/**
 * Created by mini on 17/11/5.
 */

public class JsonCommon<T> {

    private String code;

    private List<T> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
