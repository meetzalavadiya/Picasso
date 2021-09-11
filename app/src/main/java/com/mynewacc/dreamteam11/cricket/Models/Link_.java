package com.mynewacc.dreamteam11.cricket.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mynewacc.dreamteam11.cricket.Utils.AppUtils;

public class Link_ {
    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("rel")
    @Expose
    private String rel;
    @SerializedName(AppUtils.PUSH_TITLE)
    @Expose
    private String title;
    @SerializedName("type")
    @Expose
    private String type;

    public String getRel() {
        return this.rel;
    }

    public void setRel(String str) {
        this.rel = str;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String getHref() {
        return this.href;
    }

    public void setHref(String str) {
        this.href = str;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }
}
