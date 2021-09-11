package com.mynewacc.dreamteam11.cricket.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class PushBean implements Parcelable {
    public static final Creator<PushBean> CREATOR = new Creator<PushBean>() {
        /* class com.yd.expert.predictionforwinningteam.fentacyteamexpert.AppData.Model.PushBean.AnonymousClass1 */

        @Override // android.os.Parcelable.Creator
        public PushBean createFromParcel(Parcel parcel) {
            return new PushBean(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public PushBean[] newArray(int i) {
            return new PushBean[i];
        }
    };
    private String banner;
    private String body;
    private String currentTime;
    private String deadLine;
    private String isSeen;
    private String level;
    private String linkId;
    private String postTime;
    private String postUrl;
    private String pushValue;
    private String title;

    public int describeContents() {
        return 0;
    }

    public PushBean() {
    }

    public PushBean(Parcel parcel) {
        this.linkId = parcel.readString();
        this.title = parcel.readString();
        this.body = parcel.readString();
        this.level = parcel.readString();
        this.deadLine = parcel.readString();
        this.banner = parcel.readString();
        this.pushValue = parcel.readString();
        this.isSeen = parcel.readString();
        this.postUrl = parcel.readString();
        this.postTime = parcel.readString();
        this.currentTime = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.linkId);
        parcel.writeString(this.title);
        parcel.writeString(this.body);
        parcel.writeString(this.level);
        parcel.writeString(this.deadLine);
        parcel.writeString(this.banner);
        parcel.writeString(this.pushValue);
        parcel.writeString(this.isSeen);
        parcel.writeString(this.postUrl);
        parcel.writeString(this.postTime);
        parcel.writeString(this.currentTime);
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

    public String getBody() {
        return this.body;
    }

    public void setBody(String str) {
        this.body = str;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String str) {
        this.level = str;
    }

    public String getDeadLine() {
        return this.deadLine;
    }

    public void setDeadLine(String str) {
        this.deadLine = str;
    }

    public String getBanner() {
        return this.banner;
    }

    public void setBanner(String str) {
        this.banner = str;
    }

    public String getPushValue() {
        return this.pushValue;
    }

    public void setPushValue(String str) {
        this.pushValue = str;
    }

    public String getIsSeen() {
        return this.isSeen;
    }

    public void setIsSeen(String str) {
        this.isSeen = str;
    }

    public String getPostUrl() {
        return this.postUrl;
    }

    public void setPostUrl(String str) {
        this.postUrl = str;
    }

    public String getPostTime() {
        return this.postTime;
    }

    public void setPostTime(String str) {
        this.postTime = str;
    }

    public String getCurrentTime() {
        return this.currentTime;
    }

    public void setCurrentTime(String str) {
        this.currentTime = str;
    }
}
