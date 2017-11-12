package com.starnet.cqj.taobaoke.model;

import java.io.Serializable;

/**
 * Created by mini on 17/11/11.
 */

public class IntegralProduct implements Serializable {

    private String id;

    private String title;

    private String pic;

    private String detail;

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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
