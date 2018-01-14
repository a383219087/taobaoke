package com.starnet.cqj.taobaoke.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/01/14.
 */

public class MemberDetailItem {

    @SerializedName("orderstatus")
    private String status;

    private String score;

    private String ctime;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }
}
