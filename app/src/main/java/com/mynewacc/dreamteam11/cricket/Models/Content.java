package com.mynewacc.dreamteam11.cricket.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Content {
    @SerializedName("$t")
    @Expose
    private String $t;

    public String get$t() {
        return this.$t;
    }

    public void set$t(String str) {
        this.$t = str;
    }
}
