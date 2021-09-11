package com.mynewacc.dreamteam11.cricket.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category_ {
    @SerializedName("scheme")
    @Expose
    private String scheme;
    @SerializedName("term")
    @Expose
    private String term;

    public String getScheme() {
        return this.scheme;
    }

    public void setScheme(String str) {
        this.scheme = str;
    }

    public String getTerm() {
        return this.term;
    }

    public void setTerm(String str) {
        this.term = str;
    }
}
