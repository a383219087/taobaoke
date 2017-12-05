package com.starnet.cqj.taobaoke.model.city;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * Created by Administrator on 2017/12/05.
 */

public class RemoteCity implements IPickerViewData {

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPickerViewText() {
        return name;
    }
}
