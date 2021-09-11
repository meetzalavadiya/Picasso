package com.mynewacc.dreamteam11.cricket.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Feed {
    @SerializedName("category")
    @Expose
    private List<Category> category = null;
    @SerializedName("entry")
    @Expose
    private List<Entry> entry = null;

    public List<Category> getCategory() {
        return this.category;
    }

    public void setCategory(List<Category> list) {
        this.category = list;
    }

    public List<Entry> getEntry() {
        return this.entry;
    }

    public void setEntry(List<Entry> list) {
        this.entry = list;
    }
}
