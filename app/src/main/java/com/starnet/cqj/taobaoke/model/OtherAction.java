package com.starnet.cqj.taobaoke.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/12/15.
 */

public class OtherAction {

    @SerializedName("active_id")
    private String id;

    private String name;

    private String pic;

    private String url;

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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
