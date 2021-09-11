package com.pesonal.adsdk.bannerad;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.facebook.ads.NativeBannerAd;
import com.google.android.gms.ads.nativead.NativeAd;
import com.pesonal.adsdk.AppManage;
import com.pesonal.adsdk.R;
import com.pesonal.adsdk.banner_listner;
import com.pesonal.adsdk.view_Ad_sdk;
import com.startapp.android.publish.ads.nativead.NativeAdDetails;

import java.util.ArrayList;

public class bannerad_Card_Fix_h extends CardView {

    public bannerad_Card_Fix_h(@NonNull Context context) {
        super(context);

        View view = LayoutInflater.from(context).inflate(R.layout.ad_text, this, false);
        addView(view);

        Log.e("bannerad_Card", "bannerad_Card:111 "+"Enter" );
        AppManage.getInstance((Activity) context).banner(this, new banner_listner() {
            @Override
            public void on_admob_loded(NativeAd ad, ViewGroup containor) {
                new view_Ad_sdk(context).inflate_NATIV_BANNER_ADMOB(ad, containor,true);
            }

            @Override
            public void on_facebook_loded(NativeBannerAd ad, ViewGroup containor) {
                new view_Ad_sdk(context).inflate_NB_FB(ad, containor,true);
            }

            @Override
            public void on_startapp_loded(ArrayList<NativeAdDetails> ad, ViewGroup containor) {
                new view_Ad_sdk(context).inflate_NB_STARTAPP(ad, containor,true);
            }
        });
    }

    public bannerad_Card_Fix_h(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.ad_text, this, false);
        addView(view);
        Log.e("bannerad_Card", "bannerad_Card:222 "+"Enter" );
        AppManage.getInstance((Activity) context).banner(this, new banner_listner() {
            @Override
            public void on_admob_loded(NativeAd ad, ViewGroup containor) {
                new view_Ad_sdk(context).inflate_NATIV_BANNER_ADMOB(ad, containor,true);
            }

            @Override
            public void on_facebook_loded(NativeBannerAd ad, ViewGroup containor) {
                new view_Ad_sdk(context).inflate_NB_FB(ad, containor,true);
            }

            @Override
            public void on_startapp_loded(ArrayList<NativeAdDetails> ad, ViewGroup containor) {
                new view_Ad_sdk(context).inflate_NB_STARTAPP(ad, containor,true);
            }
        });
    }

    public bannerad_Card_Fix_h(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.ad_text, this, false);
        addView(view);
        Log.e("bannerad_Card", "bannerad_Card:333 "+"Enter" );
        AppManage.getInstance((Activity) context).banner(this, new banner_listner() {
            @Override
            public void on_admob_loded(NativeAd ad, ViewGroup containor) {
                new view_Ad_sdk(context).inflate_NATIV_BANNER_ADMOB(ad, containor,true);
            }

            @Override
            public void on_facebook_loded(NativeBannerAd ad, ViewGroup containor) {
                new view_Ad_sdk(context).inflate_NB_FB(ad, containor,true);
            }

            @Override
            public void on_startapp_loded(ArrayList<NativeAdDetails> ad, ViewGroup containor) {
                new view_Ad_sdk(context).inflate_NB_STARTAPP(ad, containor,true);

            }
        });
    }
}
