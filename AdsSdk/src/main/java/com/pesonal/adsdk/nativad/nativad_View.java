package com.pesonal.adsdk.nativad;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.ads.nativead.NativeAd;
import com.pesonal.adsdk.AppManage;
import com.pesonal.adsdk.R;
import com.pesonal.adsdk.nativ_listner;
import com.pesonal.adsdk.view_Ad_sdk;
import com.startapp.android.publish.ads.nativead.NativeAdDetails;

import java.util.ArrayList;

public class nativad_View extends FrameLayout {

    public nativad_View(@NonNull Context context) {
        super(context);

        View view = LayoutInflater.from(context).inflate(R.layout.ad_text, this, false);
        addView(view);

        AppManage.getInstance((Activity) context).nativeAd(this, new nativ_listner() {
            @Override
            public void on_admob_loded(NativeAd ad, ViewGroup containor) {
                new view_Ad_sdk(context).inflate_NATIV_ADMOB(ad, containor,false);
            }

            @Override
            public void on_facebook_loded(com.facebook.ads.NativeAd ad, ViewGroup containor) {
                new view_Ad_sdk(context).inflate_NATIV_FB(ad, containor,false);
            }

            @Override
            public void on_startapp_loded(ArrayList<NativeAdDetails> ad, ViewGroup containor) {
                new view_Ad_sdk(context).inflate_NATIV_STARTAPP(ad, containor,false);
            }
        });
    }

    public nativad_View(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.ad_text, this, false);
        addView(view);
        AppManage.getInstance((Activity) context).nativeAd(this, new nativ_listner() {
            @Override
            public void on_admob_loded(NativeAd ad, ViewGroup containor) {
                new view_Ad_sdk(context).inflate_NATIV_ADMOB(ad, containor,false);
            }

            @Override
            public void on_facebook_loded(com.facebook.ads.NativeAd ad, ViewGroup containor) {
                new view_Ad_sdk(context).inflate_NATIV_FB(ad, containor,false);
            }

            @Override
            public void on_startapp_loded(ArrayList<NativeAdDetails> ad, ViewGroup containor) {
                new view_Ad_sdk(context).inflate_NATIV_STARTAPP(ad, containor,false);
            }
        });
    }

    public nativad_View(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.ad_text, this, false);
        addView(view);
        AppManage.getInstance((Activity) context).nativeAd(this, new nativ_listner() {
            @Override
            public void on_admob_loded(NativeAd ad, ViewGroup containor) {
                new view_Ad_sdk(context).inflate_NATIV_ADMOB(ad, containor,false);
            }

            @Override
            public void on_facebook_loded(com.facebook.ads.NativeAd ad, ViewGroup containor) {
                new view_Ad_sdk(context).inflate_NATIV_FB(ad, containor,false);
            }

            @Override
            public void on_startapp_loded(ArrayList<NativeAdDetails> ad, ViewGroup containor) {
                new view_Ad_sdk(context).inflate_NATIV_STARTAPP(ad, containor,false);
            }
        });
    }
}
