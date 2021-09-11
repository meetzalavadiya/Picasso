package com.mynewacc.dreamteam11.cricket.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Title_ {
    @SerializedName("$t")
    @Expose
    private String $t;
    @SerializedName("type")
    @Expose
    private String type;

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String get$t() {
        return this.$t;
    }

    public void set$t(String str) {
        this.$t = str;
    }
}
