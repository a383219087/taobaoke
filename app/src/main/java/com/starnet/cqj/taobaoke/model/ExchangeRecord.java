package com.starnet.cqj.taobaoke.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mini on 17/11/14.
 */

public class ExchangeRecord {

    @SerializedName("order_id")
    private String id;
    private String title;
    private String pic;
    private String time;
    private String score;

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

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
