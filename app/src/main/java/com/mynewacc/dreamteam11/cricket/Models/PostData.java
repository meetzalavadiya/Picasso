package com.mynewacc.dreamteam11.cricket.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostData {
    @SerializedName("entry")
    @Expose
    private Entry entry;

    public Entry getEntry() {
        return this.entry;
    }

    public void setEntry(Entry entry2) {
        this.entry = entry2;
    }
}
