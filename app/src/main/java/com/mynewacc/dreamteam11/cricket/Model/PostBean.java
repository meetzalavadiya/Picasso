package com.mynewacc.dreamteam11.cricket.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class PostBean implements Parcelable, Comparable<PostBean> {
    public static final Creator<PostBean> CREATOR = new Creator<PostBean>() {

        @Override
        public PostBean createFromParcel(Parcel parcel) {
            return new PostBean(parcel);
        }

        public PostBean[] newArray(int i) {
            return new PostBean[i];
        }
    };
    private String content;
    private String deadLine;
    private List<String> imgUrl;
    private String level;
    private String linkId;
    private String postTime;
    private String title;

    public int getRwalpos() {
        return rwalpos;
    }

    public void setRwalpos(int rwalpos) {
        this.rwalpos = rwalpos;
    }

    private String url;
    public int rwalpos;


    public int describeContents() {
        return 0;
    }

    public PostBean() {
    }

    public PostBean(Parcel parcel) {
        this.linkId = parcel.readString();
        this.title = parcel.readString();
        this.level = parcel.readString();
        this.url = parcel.readString();
        this.imgUrl = parcel.createStringArrayList();
        this.deadLine = parcel.readString();
        this.postTime = parcel.readString();
        this.content = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.linkId);
        parcel.writeString(this.title);
        parcel.writeString(this.level);
        parcel.writeString(this.url);
        parcel.writeStringList(this.imgUrl);
        parcel.writeString(this.deadLine);
        parcel.writeString(this.postTime);
        parcel.writeString(this.content);
    }

    public String getLinkId() {
        return this.linkId;
    }

    public void setLinkId(String str) {
        this.linkId = str;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String str) {
        this.level = str;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public List<String> getImgUrl() {
        return this.imgUrl;
    }

    public void setImgUrl(List<String> list) {
        this.imgUrl = list;
    }

    public String getDeadLine() {
        return this.deadLine;
    }

    public void setDeadLine(String str) {
        this.deadLine = str;
    }

    public String getPostTime() {
        return this.postTime;
    }

    public void setPostTime(String str) {
        this.postTime = str;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public int compareTo(PostBean postBean) {
        return this.deadLine.compareTo(postBean.getDeadLine());
    }
}
