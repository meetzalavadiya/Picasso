package com.mynewacc.dreamteam11.cricket.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mynewacc.dreamteam11.cricket.Utils.AppUtils;

import java.util.List;

public class Entry {
    @SerializedName("category")
    @Expose
    private List<Category_> category = null;
    @SerializedName("content")
    @Expose
    private Content content;
    private String deadLine;
    @SerializedName("id")
    @Expose
    private Id_ id;
    @SerializedName("link")
    @Expose
    private List<Link_> link = null;
    @SerializedName("published")
    @Expose
    private Published published;
    @SerializedName(AppUtils.PUSH_TITLE)
    @Expose
    private Title_ title;
    @SerializedName("updated")
    @Expose
    private Updated_ updated;

    public Id_ getId() {
        return this.id;
    }

    public void setId(Id_ id_) {
        this.id = id_;
    }

    public Published getPublished() {
        return this.published;
    }

    public void setPublished(Published published2) {
        this.published = published2;
    }

    public Updated_ getUpdated() {
        return this.updated;
    }

    public void setUpdated(Updated_ updated_) {
        this.updated = updated_;
    }

    public List<Category_> getCategory() {
        return this.category;
    }

    public void setCategory(List<Category_> list) {
        this.category = list;
    }

    public Title_ getTitle() {
        return this.title;
    }

    public void setTitle(Title_ title_) {
        this.title = title_;
    }

    public Content getContent() {
        return this.content;
    }

    public void setContent(Content content2) {
        this.content = content2;
    }

    public List<Link_> getLink() {
        return this.link;
    }

    public void setLink(List<Link_> list) {
        this.link = list;
    }

    public String getDeadLine() {
        return this.deadLine;
    }

    public void setDeadLine(String str) {
        this.deadLine = str;
    }
}
