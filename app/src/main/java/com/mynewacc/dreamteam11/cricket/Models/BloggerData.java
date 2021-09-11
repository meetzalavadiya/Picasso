package com.mynewacc.dreamteam11.cricket.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BloggerData {
    @SerializedName("feed")
    @Expose
    private Feed feed;

    public Feed getFeed() {
        return this.feed;
    }

    public void setFeed(Feed feed2) {
        this.feed = feed2;
    }
}
