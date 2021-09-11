package com.mynewacc.dreamteam11.cricket.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("term")
    @Expose
    private String term;

    public String getTerm() {
        return this.term;
    }

    public void setTerm(String str) {
        this.term = str;
    }
}
