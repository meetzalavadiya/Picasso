package com.pesonal.adsdk;

import android.view.ViewGroup;

import com.google.android.gms.ads.nativead.NativeAd;
import com.startapp.android.publish.ads.nativead.NativeAdDetails;

import java.util.ArrayList;

public interface nativ_listner {

    void on_admob_loded(NativeAd ad, ViewGroup containor);

    void on_facebook_loded(com.facebook.ads.NativeAd ad, ViewGroup containor);

    void on_startapp_loded(ArrayList<NativeAdDetails> ad, ViewGroup containor);
}
