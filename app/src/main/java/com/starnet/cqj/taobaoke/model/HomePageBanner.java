package com.starnet.cqj.taobaoke.model;

import java.util.List;

/**
 * Created by mini on 17/11/20.
 */

public class HomePageBanner {

    List<Banner> top;

    List<Banner> mid;

    public List<Banner> getTop() {
        return top;
    }

    public void setTop(List<Banner> top) {
        this.top = top;
    }

    public List<Banner> getMid() {
        return mid;
    }

    public void setMid(List<Banner> mid) {
        this.mid = mid;
    }
}
