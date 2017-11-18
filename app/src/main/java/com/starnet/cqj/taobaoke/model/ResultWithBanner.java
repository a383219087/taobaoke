package com.starnet.cqj.taobaoke.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mini on 17/11/18.
 */

public class ResultWithBanner<T> extends ResultWrapper<T> {


    @SerializedName("banner")
    List<Banner> mBannerList;

    public List<Banner> getBannerList() {
        return mBannerList;
    }

    public void setBannerList(List<Banner> bannerList) {
        mBannerList = bannerList;
    }
}
