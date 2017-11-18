package com.starnet.cqj.taobaoke.model;

import java.io.Serializable;

/**
 * Created by mini on 17/11/18.
 */

public class Article implements Serializable{

    private String id;
    private String title;
    private String pic;
    private String time;
    private String dec;
    private String view;

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDec() {
        return dec;
    }

    public void setDec(String dec) {
        this.dec = dec;
    }
}
