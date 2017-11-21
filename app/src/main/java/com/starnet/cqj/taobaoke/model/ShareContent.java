package com.starnet.cqj.taobaoke.model;

import android.text.TextUtils;

/**
 * Created by Administrator on 2017/11/21.
 */

public class ShareContent {

    private String url;

    private String content;

    public String getUrl() {
        return TextUtils.isEmpty(url) ? "" : url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return  TextUtils.isEmpty(content) ? "" : content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
