package com.pesonal.adsdk;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.facebook.ads.AbstractAdListener;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeBannerAd;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.greedygame.core.AppConfig;
import com.greedygame.core.GreedyGameAds;
import com.greedygame.core.adview.general.AdLoadCallback;
import com.greedygame.core.adview.general.GGAdview;
import com.greedygame.core.interfaces.GreedyGameAdsEventsListener;
import com.greedygame.core.interstitial.general.GGInterstitialAd;
import com.greedygame.core.interstitial.general.GGInterstitialEventsListener;
import com.greedygame.core.models.general.AdErrors;
import com.greedygame.core.models.general.InitErrors;
import com.startapp.android.publish.ads.nativead.NativeAdDetails;
import com.startapp.android.publish.ads.nativead.NativeAdPreferences;
import com.startapp.android.publish.ads.nativead.StartAppNativeAd;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;
import com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.mediation.IUnityAdsExtendedListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;


public class AppManage {

    private static AppManage mInstance;
    static Activity activity;

    public static int count_banner = -1;
    String admob_b, facebook_b, sdkx_b;
    ArrayList<String> banner_sequence = new ArrayList<>();


    public static int count_native = -1;
    ArrayList<String> native_sequence = new ArrayList<>();


    public static int count_click = -1;
    public static int count_click_for_alt = -1;
    //    public InterstitialAd interstitial1;
    private com.facebook.ads.InterstitialAd fbinterstitialAd1;
    ArrayList<String> interstitial_sequence = new ArrayList<>();
    boolean is_foursesully;


    public static String app_privacyPolicyLink = "";
    public static int app_needInternet = 0;
    public static int app_updateAppDialogStatus = 0;
    public static String app_versionCode = "";
    public static int app_redirectOtherAppStatus = 0;
    public static String app_newPackageName = "";
    public static int app_adShowStatus = 1;
    public static int app_howShowAd = 0;
    public static String app_adPlatformSequence = "";
    public static String app_alernateAdShow = "";
    public static int app_mainClickCntSwAd = 0;
    public static int app_innerClickCntSwAd = 0;

    public static String ADMOB_APPID = "";
    public static String ADMOB_I1 = "";
    public static String ADMOB_I2 = "";
    public static String ADMOB_B1 = "";
    public static String ADMOB_B2 = "";
    public static String ADMOB_N1 = "";
    public static String ADMOB_N2 = "";
    public static String ADMOB_R1 = "";
    public static String ADMOB_R2 = "";

    public static int sdkx_adstatus = 0;
    public static String SDKX_I1 = "";
    public static String SDKX_I2 = "";
    public static String SDKX_B1 = "";
    public static String SDKX_B2 = "";
    public static String SDKX_N1 = "";
    public static String SDKX_N2 = "";
    public static String SDKX_R1 = "";
    public static String SDKX_R2 = "";

    public static String FACEBOOK_I1 = "";
    public static String FACEBOOK_I2 = "";
    public static String FACEBOOK_B1 = "";
    public static String FACEBOOK_B2 = "";
    public static String FACEBOOK_N1 = "";
    public static String FACEBOOK_N2 = "";

    public static String APPNEXT_APPID = "";
    public static String STARTAPP_APPID = "";
    public static String UNITY_APPID = "";
    public static String SDKX_APPID = "";

    public static int admob_AdStatus = 0;
    public static int facebook_AdStatus = 0;
    public static int appnext_AdStatus = 0;
    public static int startapp_AdStatus = 0;
    public static int unity_AdStatus = 0;


    public static SharedPreferences mysharedpreferences;
    public static String APPNEXT_Interstitial1 = "";
    public static int ad_dialog_time_in_second = 2;


    static MyCallback myCallback;
    public static StartAppAd startAppAd;
    private Dialog dialog;
    private static boolean status;
    private InterstitialAd interstitial1;
    int time;
    boolean is_close;
    private GGInterstitialAd gg_ins;
    private String sdkx_n;

    public interface MyCallback {
        void callbackCall();
    }

    public static AppManage getInstance(Activity activity) {
        if (mInstance == null) {
            mInstance = new AppManage(activity);
        }
        return mInstance;
    }

    public AppManage(Activity activity) {
        AppManage.activity = activity;
        mysharedpreferences = activity.getSharedPreferences(activity.getPackageName(), Context.MODE_PRIVATE);
        getResponseFromPref();

    }

    public static boolean hasActiveInternetConnection(Context context) {
        @SuppressLint("WrongConstant") NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public void getResponseFromPref(getDataListner listner, int vcode) {
        String response1 = mysharedpreferences.getString("response", "");
        if (!response1.isEmpty()) {
            try {
                JSONObject response = new JSONObject(response1);
                JSONObject settingsJsonObject = response.getJSONObject("APP_SETTINGS");

                status = response.getBoolean("STATUS");

                app_privacyPolicyLink = settingsJsonObject.getString("app_privacyPolicyLink");
                app_needInternet = settingsJsonObject.getInt("app_needInternet");
                app_updateAppDialogStatus = settingsJsonObject.getInt("app_updateAppDialogStatus");
                app_versionCode = settingsJsonObject.getString("app_versionCode");
                app_redirectOtherAppStatus = settingsJsonObject.getInt("app_redirectOtherAppStatus");
                app_newPackageName = settingsJsonObject.getString("app_newPackageName");
                app_adShowStatus = settingsJsonObject.getInt("app_adShowStatus");
                app_howShowAd = settingsJsonObject.getInt("app_howShowAd");
                app_adPlatformSequence = settingsJsonObject.getString("app_adPlatformSequence");
                app_alernateAdShow = settingsJsonObject.getString("app_alernateAdShow");
                app_mainClickCntSwAd = settingsJsonObject.getInt("app_mainClickCntSwAd");
                app_innerClickCntSwAd = settingsJsonObject.getInt("app_innerClickCntSwAd");

                SharedPreferences.Editor editor = mysharedpreferences.edit();
                editor.putString("app_privacyPolicyLink", app_privacyPolicyLink);
                editor.putInt("app_needInternet", app_needInternet);
                editor.putInt("app_updateAppDialogStatus", app_updateAppDialogStatus);
                editor.putString("app_versionCode", app_versionCode);
                editor.putInt("app_redirectOtherAppStatus", app_redirectOtherAppStatus);
                editor.putString("app_newPackageName", app_newPackageName);
                editor.putInt("app_adShowStatus", app_adShowStatus);
                editor.putInt("app_howShowAd", app_howShowAd);
                editor.putString("app_adPlatformSequence", app_adPlatformSequence);
                editor.putString("app_alernateAdShow", app_alernateAdShow);
                editor.putInt("app_mainClickCntSwAd", app_mainClickCntSwAd);
                editor.putInt("app_innerClickCntSwAd", app_innerClickCntSwAd);
                editor.commit();

                JSONObject AdmobJsonObject = response.getJSONObject("PLACEMENT").getJSONObject("Admob");
                admob_AdStatus = AdmobJsonObject.getInt("ad_showAdStatus");
                ADMOB_APPID = AdmobJsonObject.getString("AppID");
                ADMOB_B1 = AdmobJsonObject.getString("Banner1");
                ADMOB_B2 = AdmobJsonObject.getString("Banner2");
                ADMOB_I1 = AdmobJsonObject.getString("Interstitial1");
                ADMOB_I2 = AdmobJsonObject.getString("Interstitial2");
                ADMOB_N1 = AdmobJsonObject.getString("Native1");
                ADMOB_N2 = AdmobJsonObject.getString("Native2");
                ADMOB_R1 = AdmobJsonObject.getString("RewardedVideo1");
                ADMOB_R2 = AdmobJsonObject.getString("RewardedVideo2");

                JSONObject FBJsonObject = response.getJSONObject("PLACEMENT").getJSONObject("Facebookaudiencenetwork");
                facebook_AdStatus = FBJsonObject.getInt("ad_showAdStatus");
                FACEBOOK_B1 = FBJsonObject.getString("Banner1");
                FACEBOOK_B2 = FBJsonObject.getString("Banner2");
                FACEBOOK_I1 = FBJsonObject.getString("Interstitial1");
                FACEBOOK_I2 = FBJsonObject.getString("Interstitial2");
                FACEBOOK_N1 = FBJsonObject.getString("Native1");
                FACEBOOK_N2 = FBJsonObject.getString("Native2");

                JSONObject SAJsonObject = response.getJSONObject("PLACEMENT").getJSONObject("StartApp");
                startapp_AdStatus = SAJsonObject.getInt("ad_showAdStatus");
                STARTAPP_APPID = SAJsonObject.getString("AppID");

                JSONObject ANJsonObject = response.getJSONObject("PLACEMENT").getJSONObject("AppNext");
                appnext_AdStatus = ANJsonObject.getInt("ad_showAdStatus");
                APPNEXT_APPID = ANJsonObject.getString("AppID");

                JSONObject UNJsonObject = response.getJSONObject("PLACEMENT").getJSONObject("Unity");
                unity_AdStatus = UNJsonObject.getInt("ad_showAdStatus");
                UNITY_APPID = UNJsonObject.getString("AppID");

                try {
                    JSONObject sdkxjson = response.getJSONObject("PLACEMENT").getJSONObject("SDKX");
                    sdkx_adstatus = sdkxjson.getInt("ad_showAdStatus");
                    SDKX_APPID = sdkxjson.getString("AppID");
                    SDKX_B1 = sdkxjson.getString("Banner1");
                    SDKX_B2 = sdkxjson.getString("Banner2");
                    SDKX_I1 = sdkxjson.getString("Interstitial1");
                    SDKX_I2 = sdkxjson.getString("Interstitial2");
                    SDKX_N1 = sdkxjson.getString("Native1");
                    SDKX_N2 = sdkxjson.getString("Native2");
                    SDKX_R1 = sdkxjson.getString("RewardedVideo1");
                    SDKX_R2 = sdkxjson.getString("RewardedVideo2");
                } catch (Exception e) {

                }


                listner.ongetExtradata(response.getJSONObject("EXTRA_DATA"));

            } catch (Exception e) {
            }


            if (app_redirectOtherAppStatus == 1) {
                String redirectNewPackage = mysharedpreferences.getString("app_newPackageName", "");
                listner.onRedirect(redirectNewPackage);
            } else if (app_updateAppDialogStatus == 1 && !checkUpdate(vcode)) {
                listner.onUpdate("https://play.google.com/store/apps/details?id=" + activity.getPackageName());
            } else {

                if (sdkx_adstatus == 1) {
                    initAd(listner);
                } else {
                    listner.onsuccess();
                    initAd(null);
                    if (myCallback != null) {
                        myCallback.callbackCall();
                        myCallback = null;
                    }
                }

            }
        }


    }


    public String get_gif() {
        return APPNEXT_APPID;
    }

    public static void getResponseFromPref() {
        String response1 = mysharedpreferences.getString("response", "");
        if (!response1.isEmpty()) {
            try {
                JSONObject response = new JSONObject(response1);
                JSONObject settingsJsonObject = response.getJSONObject("APP_SETTINGS");

                status = response.getBoolean("STATUS");

                app_privacyPolicyLink = settingsJsonObject.getString("app_privacyPolicyLink");
                app_needInternet = settingsJsonObject.getInt("app_needInternet");
                app_updateAppDialogStatus = settingsJsonObject.getInt("app_updateAppDialogStatus");
                app_versionCode = settingsJsonObject.getString("app_versionCode");
                app_redirectOtherAppStatus = settingsJsonObject.getInt("app_redirectOtherAppStatus");
                app_newPackageName = settingsJsonObject.getString("app_newPackageName");
                app_adShowStatus = settingsJsonObject.getInt("app_adShowStatus");
                app_howShowAd = settingsJsonObject.getInt("app_howShowAd");
                app_adPlatformSequence = settingsJsonObject.getString("app_adPlatformSequence");
                app_alernateAdShow = settingsJsonObject.getString("app_alernateAdShow");
                app_mainClickCntSwAd = settingsJsonObject.getInt("app_mainClickCntSwAd");
                app_innerClickCntSwAd = settingsJsonObject.getInt("app_innerClickCntSwAd");

                SharedPreferences.Editor editor = mysharedpreferences.edit();
                editor.putString("app_privacyPolicyLink", app_privacyPolicyLink);
                editor.putInt("app_needInternet", app_needInternet);
                editor.putInt("app_updateAppDialogStatus", app_updateAppDialogStatus);
                editor.putString("app_versionCode", app_versionCode);
                editor.putInt("app_redirectOtherAppStatus", app_redirectOtherAppStatus);
                editor.putString("app_newPackageName", app_newPackageName);
                editor.putInt("app_adShowStatus", app_adShowStatus);
                editor.putInt("app_howShowAd", app_howShowAd);
                editor.putString("app_adPlatformSequence", app_adPlatformSequence);
                editor.putString("app_alernateAdShow", app_alernateAdShow);
                editor.putInt("app_mainClickCntSwAd", app_mainClickCntSwAd);
                editor.putInt("app_innerClickCntSwAd", app_innerClickCntSwAd);
                editor.commit();

                JSONObject AdmobJsonObject = response.getJSONObject("PLACEMENT").getJSONObject("Admob");
                admob_AdStatus = AdmobJsonObject.getInt("ad_showAdStatus");
                ADMOB_APPID = AdmobJsonObject.getString("AppID");
                ADMOB_B1 = AdmobJsonObject.getString("Banner1");
                ADMOB_B2 = AdmobJsonObject.getString("Banner2");
                ADMOB_I1 = AdmobJsonObject.getString("Interstitial1");
                ADMOB_I2 = AdmobJsonObject.getString("Interstitial2");
                ADMOB_N1 = AdmobJsonObject.getString("Native1");
                ADMOB_N2 = AdmobJsonObject.getString("Native2");
                ADMOB_R1 = AdmobJsonObject.getString("RewardedVideo1");
                ADMOB_R2 = AdmobJsonObject.getString("RewardedVideo2");

                JSONObject FBJsonObject = response.getJSONObject("PLACEMENT").getJSONObject("Facebookaudiencenetwork");
                facebook_AdStatus = FBJsonObject.getInt("ad_showAdStatus");
                FACEBOOK_B1 = FBJsonObject.getString("Banner1");
                FACEBOOK_B2 = FBJsonObject.getString("Banner2");
                FACEBOOK_I1 = FBJsonObject.getString("Interstitial1");
                FACEBOOK_I2 = FBJsonObject.getString("Interstitial2");
                FACEBOOK_N1 = FBJsonObject.getString("Native1");
                FACEBOOK_N2 = FBJsonObject.getString("Native2");

                JSONObject SAJsonObject = response.getJSONObject("PLACEMENT").getJSONObject("StartApp");
                startapp_AdStatus = SAJsonObject.getInt("ad_showAdStatus");
                STARTAPP_APPID = SAJsonObject.getString("AppID");

                JSONObject ANJsonObject = response.getJSONObject("PLACEMENT").getJSONObject("AppNext");
                appnext_AdStatus = ANJsonObject.getInt("ad_showAdStatus");
                APPNEXT_APPID = ANJsonObject.getString("AppID");

                JSONObject UNJsonObject = response.getJSONObject("PLACEMENT").getJSONObject("Unity");
                unity_AdStatus = UNJsonObject.getInt("ad_showAdStatus");
                UNITY_APPID = UNJsonObject.getString("AppID");

                JSONObject sdkxjson = response.getJSONObject("PLACEMENT").getJSONObject("SDKX");
                sdkx_adstatus = sdkxjson.getInt("ad_showAdStatus");
                SDKX_APPID = sdkxjson.getString("AppID");
                SDKX_B1 = sdkxjson.getString("Banner1");
                SDKX_B2 = sdkxjson.getString("Banner2");
                SDKX_I1 = sdkxjson.getString("Interstitial1");
                SDKX_I2 = sdkxjson.getString("Interstitial2");
                SDKX_N1 = sdkxjson.getString("Native1");
                SDKX_N2 = sdkxjson.getString("Native2");
                SDKX_R1 = sdkxjson.getString("RewardedVideo1");
                SDKX_R2 = sdkxjson.getString("RewardedVideo2");

            } catch (Exception e) {

            }

        }


    }

    public boolean is_app_open_avilabel() {

        if (admob_AdStatus == 1) {
            return true;
        } else {
            return false;
        }

    }

    public boolean is_sdkx_app_open_avilabel() {

        if (sdkx_adstatus == 1) {
            return true;
        } else {
            return false;
        }

    }


    public String get_appopen_id() {
        SharedPreferences.Editor editor = mysharedpreferences.edit();
        String appopen_i;
        if (mysharedpreferences.getInt("which_id_appopen", 0) == 0) {
            editor.putInt("which_id_appopen", 1).apply();
            appopen_i = ADMOB_R1;
        } else {
            editor.putInt("which_id_appopen", 0).apply();
            appopen_i = ADMOB_R2;
        }
        return appopen_i;

    }

    public String get_appopen_id_sdkx() {
        SharedPreferences.Editor editor = mysharedpreferences.edit();
        String appopen_i;
        if (mysharedpreferences.getInt("which_id_appopen_sdkx", 0) == 0) {
            editor.putInt("which_id_appopen_sdkx", 1).apply();
            appopen_i = SDKX_R1;
        } else {
            editor.putInt("which_id_appopen_sdkx", 0).apply();
            appopen_i = SDKX_R2;
        }
        return appopen_i;

    }

    private static void initAd(final getDataListner listner) {

        if (admob_AdStatus == 1) {
            MobileAds.initialize(activity, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                }
            });
        }

        if (facebook_AdStatus == 1) {
            AudienceNetworkAds.initialize(activity);
        }


        if (startapp_AdStatus == 1 && !STARTAPP_APPID.isEmpty()) {
            StartAppSDK.init(activity, STARTAPP_APPID, false);
            StartAppAd.disableSplash();
            startAppAd = new StartAppAd(activity);
            startAppAd.loadAd(StartAppAd.AdMode.AUTOMATIC);
        }

        if (unity_AdStatus == 1 && !UNITY_APPID.isEmpty()) {
            UnityAds.initialize(activity, UNITY_APPID);
        }

        if (sdkx_adstatus == 1 && !SDKX_APPID.isEmpty()) {
            AppConfig appConfig = new AppConfig.Builder(activity)
                    .withAppId(SDKX_APPID)
                    .build();
            GreedyGameAds.initWith(appConfig, new GreedyGameAdsEventsListener() {
                @Override
                public void onInitSuccess() {
                    Log.e("my_log_gg", "onInitSuccess: ");
                    AppManage.getInstance(activity).loadintertialads();
                    listner.onsuccess();
                    if (myCallback != null) {
                        myCallback.callbackCall();
                        myCallback = null;
                    }
                }

                @Override
                public void onInitFailed(@NotNull InitErrors initErrors) {
                    Log.e("my_log_gg", "onInitFailed: ");
                    AppManage.getInstance(activity).loadintertialads();
                    listner.onsuccess();
                    if (myCallback != null) {
                        myCallback.callbackCall();
                        myCallback = null;
                    }
                }
            });
        } else {
            AppManage.getInstance(activity).loadintertialads();
        }


    }


    private static boolean checkUpdate(int vcode) {


        if (mysharedpreferences.getInt("app_updateAppDialogStatus", 0) == 1) {
            String versions = mysharedpreferences.getString("app_versionCode", "");
            String str[] = versions.split(",");

            try {
                int cversion = vcode;

                if (Arrays.asList(str).contains(cversion + "")) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return false;
    }


    public void loadintertialads() {

        if (app_adShowStatus == 0) {
            return;
        }

        if (admob_AdStatus == 1) {
            loadAdmobInterstitial();
        }

        if (sdkx_adstatus == 1) {
            load_sdkx_ins();
        }

        if (facebook_AdStatus == 1) {
            loadFacebookInterstitial();
        }


    }

    public void load_sdkx_ins() {
        Log.e("GGADS_INS", "Enter 1111");
        SharedPreferences.Editor editor = mysharedpreferences.edit();
        String SDKX_i;
        if (mysharedpreferences.getInt("which_id_adx_ins", 0) == 0) {
            editor.putInt("which_id_adx_ins", 1).apply();
            SDKX_i = SDKX_I1;
        } else {
            editor.putInt("which_id_adx_ins", 0).apply();
            SDKX_i = SDKX_I2;
        }
        try {
            gg_ins.destroy();
        } catch (Exception e) {

        }
        Log.e("GGADS_INS", "Enter 222");

        gg_ins = new GGInterstitialAd(activity, SDKX_i);
        gg_ins.setListener(new GGInterstitialEventsListener() {
            @Override
            public void onAdLoaded() {
                Log.e("GGADS_INS", "Ad Loaded");
            }

            @Override
            public void onAdClosed() {
                Log.e("GGADS_INS", "Ad Closed");
                interstitialCallBack();
                load_sdkx_ins();
            }

            @Override
            public void onAdOpened() {
                Log.e("GGADS_INS", "Ad Opened");
            }

            @Override
            public void onAdShowFailed() {
                Log.e("GGADS_INS", "Ad Show failed");
            }

            @Override
            public void onAdLoadFailed(AdErrors cause) {
                Log.e("GGADS_INS", "Ad Load Failed " + cause);
            }
        });
        gg_ins.loadAd();
    }


    private void loadFacebookInterstitial() {
        SharedPreferences.Editor editor = mysharedpreferences.edit();
        String facebook_i;
        if (mysharedpreferences.getInt("which_id_fb", 0) == 0) {
            editor.putInt("which_id_fb", 1).apply();
            facebook_i = FACEBOOK_I1;
        } else {
            editor.putInt("which_id_fb", 0).apply();
            facebook_i = FACEBOOK_I2;
        }
        fbinterstitialAd1 = new com.facebook.ads.InterstitialAd(activity, facebook_i);
        fbinterstitialAd1.loadAd(fbinterstitialAd1.buildLoadAdConfig().withAdListener(new AbstractAdListener() {
            @Override
            public void onError(Ad ad, AdError error) {
                super.onError(ad, error);
            }

            @Override
            public void onAdLoaded(Ad ad) {
                super.onAdLoaded(ad);
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                super.onInterstitialDismissed(ad);
                fbinterstitialAd1.loadAd();
                interstitialCallBack();
            }
        }).build());
    }

    private void loadAdmobInterstitial() {
        load_admob_final();
    }

    public void load_admob_final() {
        final SharedPreferences.Editor editor = mysharedpreferences.edit();
        String google_i;
        if (mysharedpreferences.getInt("which_id_admob", 0) == 0) {
            editor.putInt("which_id_admob", 1).apply();
            google_i = ADMOB_I1;
        } else {
            editor.putInt("which_id_admob", 0).apply();
            google_i = ADMOB_I2;
        }
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(
                activity,
                google_i,
                adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        interstitial1 = interstitialAd;
                        Log.e("my_log", "onAdLoaded");
                        interstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        Log.e("my_log", "The ad was dismissed.");
                                        interstitial1 = null;
                                        load_admob_final();
                                        interstitialCallBack();
                                    }


                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        Log.d("my_log", "The ad was shown.");
                                    }
                                });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.e("my_log", loadAdError.getMessage());
                        interstitial1 = null;

                    }
                });
    }

    public void show_INTERSTIAL(Context context, MyCallback myCallback) {
        displayInterstitial(myCallback, false, app_mainClickCntSwAd, context);
    }

    public void show_INTERSTIAL(Context context, boolean is_foursesully, MyCallback myCallback) {

        if (is_foursesully) {
            displayInterstitial(myCallback, is_foursesully, 0, context);
        } else {
            displayInterstitial(myCallback, is_foursesully, app_mainClickCntSwAd, context);
        }
    }

    public void show_INTERSTIAL(Context context, int inner_click, MyCallback myCallback) {
        if (inner_click == 1) {
            displayInterstitial(myCallback, false, app_innerClickCntSwAd, context);
        } else {
            displayInterstitial(myCallback, false, app_mainClickCntSwAd, context);
        }

    }

    public void show_INTERSTIAL(Context context, boolean is_foursesully, int how_many_clicks, MyCallback myCallback) {
        displayInterstitial(myCallback, is_foursesully, how_many_clicks, context);
    }


    public void displayInterstitial(MyCallback myCallback2, boolean is_foursesully, int how_many_clicks, Context context) {
        this.myCallback = myCallback2;
        this.is_foursesully = is_foursesully;

        count_click++;

        if (app_adShowStatus == 0) {
            if (myCallback != null) {
                myCallback.callbackCall();
                myCallback = null;
            }
            return;
        }
        if (how_many_clicks != 0) {
            if (count_click % how_many_clicks != 0) {
                if (myCallback != null) {
                    myCallback.callbackCall();
                    myCallback = null;
                }
                return;
            }
        }

        count_click_for_alt++;


        int app_howShowAd = mysharedpreferences.getInt("app_howShowAd", 0);
        String adPlatformSequence = mysharedpreferences.getString("app_adPlatformSequence", "");
        String alernateAdShow = mysharedpreferences.getString("app_alernateAdShow", "");

        interstitial_sequence = new ArrayList<String>();
        if (app_howShowAd == 0 && !adPlatformSequence.isEmpty()) {
            String adSequence[] = adPlatformSequence.split(",");
            for (int i = 0; i < adSequence.length; i++) {
                interstitial_sequence.add(adSequence[i]);
            }

        } else if (app_howShowAd == 1 && !alernateAdShow.isEmpty()) {
            String alernateAd[] = alernateAdShow.split(",");

            int index = 0;
            for (int j = 0; j <= 10; j++) {
                if (count_click_for_alt % alernateAd.length == j) {
                    index = j;
                    interstitial_sequence.add(alernateAd[index]);
                }
            }

            String adSequence[] = adPlatformSequence.split(",");
            for (int j = 0; j < adSequence.length; j++) {
                if (interstitial_sequence.size() != 0) {
                    if (!interstitial_sequence.get(0).equals(adSequence[j])) {
                        interstitial_sequence.add(adSequence[j]);
                    }
                }

            }
        } else {
            if (myCallback != null) {
                myCallback.callbackCall();
                myCallback = null;
            }
        }

        if (interstitial_sequence.size() != 0) {
            showInterstitialAd(interstitial_sequence.get(0), context);
        }


    }

    private void showInterstitialAd(String platform, final Context context) {


        dialog = new Dialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.ad_progress_dialog, null);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        if (platform.equals("Admob") && admob_AdStatus == 1 && interstitial1 != null) {
            if (interstitial1 != null) {
                if (app_needInternet == 1) {
                    dialog.show();

                    final CircularProgressIndicator circular_progress = view.findViewById(R.id.circular_progress);
                    circular_progress.setProgress(0, 100);
                    new CountDownTimer(ad_dialog_time_in_second * 1000, 10) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            double time = (millisUntilFinished / 10) / ad_dialog_time_in_second;
                            circular_progress.setProgress(time, 100);
                        }

                        @Override
                        public void onFinish() {
                            dialog.dismiss();
                            interstitial1.show(activity);
                        }
                    }.start();

                } else {
                    interstitial1.show(activity);
                }
            } else {
                load_admob_final();
                nextInterstitialPlatform(context);
            }
        } else if (platform.equals("SDKX") && sdkx_adstatus == 1) {
            if (gg_ins != null && gg_ins.isAdLoaded()) {
                if (app_needInternet == 1) {
                    dialog.show();

                    final CircularProgressIndicator circular_progress = view.findViewById(R.id.circular_progress);
                    circular_progress.setProgress(0, 100);
                    new CountDownTimer(ad_dialog_time_in_second * 1000, 10) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            double time = (millisUntilFinished / 10) / ad_dialog_time_in_second;
                            circular_progress.setProgress(time, 100);
                        }

                        @Override
                        public void onFinish() {
                            dialog.dismiss();
                            gg_ins.show();

                        }
                    }.start();

                } else {
                    gg_ins.show();
                }
            } else {
                load_sdkx_ins();
                nextInterstitialPlatform(context);
            }
        } else if (platform.equals("Facebookaudiencenetwork") && facebook_AdStatus == 1 && fbinterstitialAd1 != null) {

            if (fbinterstitialAd1.isAdLoaded()) {
                if (app_needInternet == 1) {

                    dialog.show();

                    final CircularProgressIndicator circular_progress = view.findViewById(R.id.circular_progress);
                    circular_progress.setProgress(0, 100);
                    new CountDownTimer(ad_dialog_time_in_second * 1000, 100) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            double time = (millisUntilFinished / 10) / ad_dialog_time_in_second;
                            circular_progress.setProgress(time, 100);
                        }

                        @Override
                        public void onFinish() {
                            dialog.dismiss();
                            fbinterstitialAd1.show();
                        }
                    }.start();

                } else {
                    fbinterstitialAd1.show();
                }
            } else {
                fbinterstitialAd1.loadAd();
                nextInterstitialPlatform(context);
            }

        } else if (platform.equals("Unity") && unity_AdStatus == 1) {
            if (UnityAds.isReady()) {
                if (app_needInternet == 1) {

                    dialog.show();

                    final CircularProgressIndicator circular_progress = view.findViewById(R.id.circular_progress);
                    circular_progress.setProgress(0, 100);
                    new CountDownTimer(ad_dialog_time_in_second * 1000, 100) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            double time = (millisUntilFinished / 10) / ad_dialog_time_in_second;
                            circular_progress.setProgress(time, 100);
                        }

                        @Override
                        public void onFinish() {
                            dialog.dismiss();
                            UnityAds.show((Activity) activity);
                        }
                    }.start();

                } else {
                    UnityAds.show((Activity) activity);
                }
                UnityAds.setListener(new IUnityAdsExtendedListener() {
                    @Override
                    public void onUnityAdsClick(String placementId) {

                    }

                    @Override
                    public void onUnityAdsPlacementStateChanged(String placementId, UnityAds.PlacementState oldState, UnityAds.PlacementState newState) {

                    }

                    @Override
                    public void onUnityAdsReady(String placementId) {

                    }

                    @Override
                    public void onUnityAdsStart(String placementId) {

                    }

                    @Override
                    public void onUnityAdsFinish(String placementId, UnityAds.FinishState result) {
                        interstitialCallBack();
                        UnityAds.initialize(activity, UNITY_APPID);
                    }

                    @Override
                    public void onUnityAdsError(UnityAds.UnityAdsError error, String message) {
                        nextInterstitialPlatform(context);
                        UnityAds.initialize(activity, UNITY_APPID);
                    }

                });
            } else {
                nextInterstitialPlatform(context);
            }

        } else if (platform.equals("StartApp") && startapp_AdStatus == 1) {
            startAppAd.showAd(new AdDisplayListener() {

                @Override
                public void adHidden(com.startapp.android.publish.adsCommon.Ad ad) {
                    interstitialCallBack();

                }

                @Override
                public void adDisplayed(com.startapp.android.publish.adsCommon.Ad ad) {

                }

                @Override
                public void adClicked(com.startapp.android.publish.adsCommon.Ad ad) {

                }

                @Override
                public void adNotDisplayed(com.startapp.android.publish.adsCommon.Ad ad) {
                    nextInterstitialPlatform(context);
                }
            });
        } else {

            nextInterstitialPlatform(context);

        }
    }

    private void nextInterstitialPlatform(Context context) {

        if (interstitial_sequence.size() != 0) {
            interstitial_sequence.remove(0);

            if (interstitial_sequence.size() != 0) {
                showInterstitialAd(interstitial_sequence.get(0), context);
            } else {
                interstitialCallBack();
            }

        } else {
            interstitialCallBack();

        }
    }


    public void interstitialCallBack() {

        if (myCallback != null) {
            myCallback.callbackCall();
            AppManage.this.myCallback = null;
        }
    }

    private boolean isPackageInstalled(String packageName, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }


    public void banner(ViewGroup banner_container, banner_listner listner_b) {

        if (app_adShowStatus == 0) {
            return;
        }

        count_banner++;
        int app_howShowAd = mysharedpreferences.getInt("app_howShowAd", 0);
        String adPlatformSequence = mysharedpreferences.getString("app_adPlatformSequence", "");
        String alernateAdShow = mysharedpreferences.getString("app_alernateAdShow", "");


        banner_sequence = new ArrayList<String>();
        if (app_howShowAd == 0 && !adPlatformSequence.isEmpty()) {
            String adSequence[] = adPlatformSequence.split(",");
            for (int i = 0; i < adSequence.length; i++) {
                banner_sequence.add(adSequence[i]);
            }

        } else if (app_howShowAd == 1 && !alernateAdShow.isEmpty()) {
            String alernateAd[] = alernateAdShow.split(",");

            int index = 0;
            for (int j = 0; j <= 10; j++) {
                if (count_banner % alernateAd.length == j) {
                    index = j;
                    banner_sequence.add(alernateAd[index]);
                }
            }

            String adSequence[] = adPlatformSequence.split(",");
            for (int j = 0; j < adSequence.length; j++) {
                if (banner_sequence.size() != 0) {
                    if (!banner_sequence.get(0).equals(adSequence[j])) {
                        banner_sequence.add(adSequence[j]);
                    }
                }
            }
        }

        for (int i = 0; i < banner_sequence.size(); i++) {
            if (banner_sequence.get(i).equalsIgnoreCase("Unity") || banner_sequence.get(i).equalsIgnoreCase("AppNext")) {
                banner_sequence.remove(i);
            }
        }


        if (banner_sequence.size() != 0) {
            showBanner(banner_sequence.get(0), banner_container, listner_b);
        }


    }

    public void showBanner(String platform, ViewGroup banner_container, banner_listner listner_b) {
        if (platform.equals("Admob")) {
            showAdmobBanner(banner_container, listner_b);
        } else if (platform.equals("Facebookaudiencenetwork")) {
            showFacebookBanner(banner_container, listner_b);
        } else if (platform.equals("AppNext")) {
            showAppNextBanner(banner_container, listner_b);
        } else if (platform.equals("StartApp")) {
            showStartAppBanner(banner_container, listner_b);
        } else if (platform.equals("SDKX")) {
            showSDKXBanner(banner_container, listner_b);
        } else {
            nextBannerPlatform(banner_container, listner_b);
        }
    }

    private void nextBannerPlatform(ViewGroup banner_container, banner_listner listner_b) {
        if (banner_sequence.size() != 0) {
            banner_sequence.remove(0);
            if (banner_sequence.size() != 0) {
                showBanner(banner_sequence.get(0), banner_container, listner_b);
            }
        }
    }

    private void showSDKXBanner(final ViewGroup banner_container, banner_listner listner_b) {
        if (GreedyGameAds.isSdkInitialized()) {
            Log.e("GGADS_Banner", "Enter 1111");
            SharedPreferences.Editor editor = mysharedpreferences.edit();
            if (mysharedpreferences.getInt("which_id_sdkx_banner", 0) == 0) {
                editor.putInt("which_id_sdkx_banner", 1).apply();
                sdkx_b = SDKX_B1;
            } else {
                editor.putInt("which_id_sdkx_banner", 0).apply();
                sdkx_b = SDKX_B2;
            }

            if (sdkx_b.isEmpty() || sdkx_adstatus == 0) {
                nextBannerPlatform(banner_container, listner_b);
                return;
            }
            Log.e("GGADS_Banner", "Enter 222222");

            final View view = activity.getLayoutInflater().inflate(R.layout.sdkx_banner, banner_container, false);
            banner_container.removeAllViews();
            banner_container.addView(view);
            final GGAdview adview = view.findViewById(R.id.banner);
            adview.setAdsMaxWidth(banner_container.getWidth());
            adview.setAdsMaxHeight(banner_container.getHeight());
            adview.setUnitId(sdkx_b);
            adview.loadAd(new AdLoadCallback() {
                              @Override
                              public void onReadyForRefresh() {
                                  Log.e("GGADS_Banner", "Ad Ready for refresh");
                              }

                              @Override
                              public void onUiiClosed() {
                                  Log.e("GGADS_Banner", "Uii closed");
                              }

                              @Override
                              public void onUiiOpened() {
                                  Log.e("GGADS_Banner", "Uii Opened");
                              }

                              @Override
                              public void onAdLoadFailed(AdErrors cause) {
                                  nextBannerPlatform(banner_container, listner_b);
                                  Log.e("GGADS_Banner", "onAdLoadFailed" + cause.toString());
                              }

                              @Override
                              public void onAdLoaded() {
                                  banner_container.removeAllViews();
                                  banner_container.addView(view);
                                  Log.e("GGADS_Banner", "onAdLoaded");
                              }
                          }
            );
        } else {
            AppConfig appConfig = new AppConfig.Builder(activity)
                    .withAppId(SDKX_APPID)
                    .build();
            GreedyGameAds.initWith(appConfig, new GreedyGameAdsEventsListener() {
                @Override
                public void onInitSuccess() {
                    load_sdkx_ins();
                    Log.e("my_log_gg", "onInitSuccess: ");
                    Log.e("GGADS_Banner", "Enter 1111");
                    SharedPreferences.Editor editor = mysharedpreferences.edit();
                    if (mysharedpreferences.getInt("which_id_sdkx_banner", 0) == 0) {
                        editor.putInt("which_id_sdkx_banner", 1).apply();
                        sdkx_b = SDKX_B1;
                    } else {
                        editor.putInt("which_id_sdkx_banner", 0).apply();
                        sdkx_b = SDKX_B2;
                    }

                    if (sdkx_b.isEmpty() || sdkx_adstatus == 0) {
                        nextBannerPlatform(banner_container, listner_b);
                        return;
                    }
                    Log.e("GGADS_Banner", "Enter 222222");

                    final View view = activity.getLayoutInflater().inflate(R.layout.sdkx_banner, banner_container, false);
                    banner_container.removeAllViews();
                    banner_container.addView(view);
                    final GGAdview adview = view.findViewById(R.id.banner);
                    adview.setAdsMaxWidth(banner_container.getWidth());
                    adview.setAdsMaxHeight(banner_container.getHeight());
                    adview.setUnitId(sdkx_b);
                    adview.loadAd(new AdLoadCallback() {
                                      @Override
                                      public void onReadyForRefresh() {
                                          Log.e("GGADS_Banner", "Ad Ready for refresh");
                                      }

                                      @Override
                                      public void onUiiClosed() {
                                          Log.e("GGADS_Banner", "Uii closed");
                                      }

                                      @Override
                                      public void onUiiOpened() {
                                          Log.e("GGADS_Banner", "Uii Opened");
                                      }

                                      @Override
                                      public void onAdLoadFailed(AdErrors cause) {
                                          nextBannerPlatform(banner_container, listner_b);
                                          Log.e("GGADS_Banner", "onAdLoadFailed" + cause.toString());
                                      }

                                      @Override
                                      public void onAdLoaded() {
                                          banner_container.removeAllViews();
                                          banner_container.addView(view);
                                          Log.e("GGADS_Banner", "onAdLoaded");
                                      }
                                  }
                    );

                }

                @Override
                public void onInitFailed(@NotNull InitErrors initErrors) {
                    Log.e("my_log_gg", "onInitFaild: ");
                }
            });
        }


    }

    private void showStartAppBanner(final ViewGroup banner_container, banner_listner listner_b) {
        if (STARTAPP_APPID.isEmpty() || startapp_AdStatus == 0) {
            nextBannerPlatform(banner_container, listner_b);
            return;
        }
        final StartAppNativeAd startAppNativeAd = new StartAppNativeAd(activity);
        AdEventListener adListener = new AdEventListener() {
            @Override
            public void onReceiveAd(com.startapp.android.publish.adsCommon.Ad ad) {
                ArrayList<NativeAdDetails> ads = startAppNativeAd.getNativeAds();    // get NativeAds list
                listner_b.on_startapp_loded(ads, banner_container);

            }

            @Override
            public void onFailedToReceiveAd(com.startapp.android.publish.adsCommon.Ad ad) {
                nextBannerPlatform(banner_container, listner_b);
            }

        };
        NativeAdPreferences preferences = new NativeAdPreferences();
        preferences.setAutoBitmapDownload(true);
        preferences.setPrimaryImageSize(4);
        startAppNativeAd.loadAd(preferences, adListener);
    }


    private void showAppNextBanner(final ViewGroup banner_container, banner_listner listner_n) {
        if (APPNEXT_APPID.isEmpty() || appnext_AdStatus == 0) {
            nextBannerPlatform(banner_container, listner_n);
            return;
        }

    }

    private void showFacebookBanner(final ViewGroup container, banner_listner listner_b) {
        SharedPreferences.Editor editor = mysharedpreferences.edit();
        if (mysharedpreferences.getInt("which_id_fb_banner", 0) == 0) {
            editor.putInt("which_id_fb_banner", 1).apply();
            facebook_b = FACEBOOK_B1;
        } else {
            editor.putInt("which_id_fb_banner", 0).apply();
            facebook_b = FACEBOOK_B2;
        }
        if (facebook_b.isEmpty() || facebook_AdStatus == 0) {
            nextBannerPlatform(container, listner_b);
            return;
        }

        final NativeBannerAd nativeAd1 = new NativeBannerAd(activity, facebook_b);
        nativeAd1.loadAd(nativeAd1.buildLoadAdConfig().withAdListener(new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
                listner_b.on_facebook_loded(nativeAd1, container);
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                nextBannerPlatform(container, listner_b);
            }

            @Override
            public void onAdLoaded(Ad ad) {
                if (nativeAd1 == null || nativeAd1 != ad) {
                    return;
                }
                nativeAd1.downloadMedia();
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        }).build());

    }

    private void showAdmobBanner(final ViewGroup nativeAdContainer, banner_listner listner_b) {

        SharedPreferences.Editor editor = mysharedpreferences.edit();
        String admob_n;
        if (mysharedpreferences.getInt("which_id_admob_nativ", 0) == 0) {
            editor.putInt("which_id_admob_nativ", 1).apply();
            admob_n = ADMOB_N1;
        } else {
            editor.putInt("which_id_admob_nativ", 0).apply();
            admob_n = ADMOB_N2;
        }
        if (admob_n.isEmpty() || admob_AdStatus == 0) {
            nextBannerPlatform(nativeAdContainer, listner_b);
            return;
        }


        final AdLoader adLoader = new AdLoader.Builder(activity, admob_n)
                .forNativeAd(new com.google.android.gms.ads.nativead.NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(@NonNull com.google.android.gms.ads.nativead.NativeAd nativeAd) {
                        listner_b.on_admob_loded(nativeAd, nativeAdContainer);

                    }

                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        nextBannerPlatform(nativeAdContainer, listner_b);
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        .build())
                .build();
        adLoader.loadAd(new AdRequest.Builder().build());


    }

    public void nativeAd(ViewGroup nativeAdContainer, nativ_listner listner_n) {


        if (app_adShowStatus == 0) {
            return;
        }

        count_native++;
        int app_howShowAd = mysharedpreferences.getInt("app_howShowAd", 0);
        String adPlatformSequence = mysharedpreferences.getString("app_adPlatformSequence", "");
        String alernateAdShow = mysharedpreferences.getString("app_alernateAdShow", "");


        native_sequence = new ArrayList<String>();
        if (app_howShowAd == 0 && !adPlatformSequence.isEmpty()) {
            String adSequence[] = adPlatformSequence.split(",");
            for (int i = 0; i < adSequence.length; i++) {
                native_sequence.add(adSequence[i]);
            }

        } else if (app_howShowAd == 1 && !alernateAdShow.isEmpty()) {
            String alernateAd[] = alernateAdShow.split(",");

            int index = 0;
            for (int j = 0; j <= 10; j++) {
                if (count_native % alernateAd.length == j) {
                    index = j;
                    native_sequence.add(alernateAd[index]);
                }
            }

            String adSequence[] = adPlatformSequence.split(",");
            for (int j = 0; j < adSequence.length; j++) {
                if (native_sequence.size() != 0) {
                    if (!native_sequence.get(0).equals(adSequence[j])) {
                        native_sequence.add(adSequence[j]);
                    }
                }
            }
        }


        for (int i = 0; i < native_sequence.size(); i++) {
            if (native_sequence.get(i).equalsIgnoreCase("Unity") || native_sequence.get(i).equalsIgnoreCase("AppNext")) {
                native_sequence.remove(i);
            }
        }

        if (native_sequence.size() != 0) {
            showNative(native_sequence.get(0), nativeAdContainer, listner_n);
        }

    }

    private void showNative(String platform, ViewGroup nativeAdContainer, nativ_listner listner_n) {
        if (platform.equals("Admob")) {
            showAdmobNative(nativeAdContainer, listner_n);
        } else if (platform.equals("Facebookaudiencenetwork")) {
            showFacebookNative(nativeAdContainer, listner_n);
        } else if (platform.equals("AppNext")) {
            showAppNextNative(nativeAdContainer, listner_n);
        } else if (platform.equals("StartApp")) {
            showStartappNative(nativeAdContainer, listner_n);
        } else if (platform.equals("SDKX")) {
            showSDKXNativ(nativeAdContainer, listner_n);
        } else {
            nextNativePlatform(nativeAdContainer, listner_n);
        }
    }

    private void nextNativePlatform(ViewGroup nativeAdContainer, nativ_listner listner_n) {
        if (native_sequence.size() != 0) {
            native_sequence.remove(0);
            if (native_sequence.size() != 0) {
                showNative(native_sequence.get(0), nativeAdContainer, listner_n);
            }
        }
    }

    private void showSDKXNativ(final ViewGroup banner_container, nativ_listner listner_n) {
        Log.e("GGADS_Nativ", "Enter 111");

        if (GreedyGameAds.isSdkInitialized()) {
            SharedPreferences.Editor editor = mysharedpreferences.edit();
            if (mysharedpreferences.getInt("which_id_sdkx_nativ", 0) == 0) {
                editor.putInt("which_id_sdkx_nativ", 1).apply();
                sdkx_n = SDKX_N1;
            } else {
                editor.putInt("which_id_sdkx_nativ", 0).apply();
                sdkx_n = SDKX_N2;
            }

            if (sdkx_n.isEmpty() || sdkx_adstatus == 0) {
                nextNativePlatform(banner_container, listner_n);
                return;
            }

            Log.e("GGADS_Nativ", "Enter eeee");

            final View view = activity.getLayoutInflater().inflate(R.layout.sdkx_banner, banner_container, false);


            banner_container.removeAllViews();
            banner_container.addView(view);
            final GGAdview adview = view.findViewById(R.id.banner);
            adview.setUnitId(sdkx_n);
            adview.setAdsMaxWidth(banner_container.getWidth());
            adview.setAdsMaxHeight(banner_container.getHeight());
            adview.loadAd(new AdLoadCallback() {
                              @Override
                              public void onReadyForRefresh() {
                                  Log.e("GGADS_Nativ", "Ad Ready for refresh");
                              }

                              @Override
                              public void onUiiClosed() {
                                  Log.e("GGADS_Nativ", "Uii closed");
                              }

                              @Override
                              public void onUiiOpened() {
                                  Log.e("GGADS_Nativ", "Uii Opened");
                              }

                              @Override
                              public void onAdLoadFailed(AdErrors cause) {
                                  Log.e("GGADS_Nativ", "onAdLoadFailed " + cause.toString());
                                  nextNativePlatform(banner_container, listner_n);
                              }

                              @Override
                              public void onAdLoaded() {
                                  banner_container.removeAllViews();
                                  banner_container.addView(view);
                                  Log.e("GGADS_Nativ", "onAdLoaded");
                              }
                          }
            );

        } else {
            AppConfig appConfig = new AppConfig.Builder(activity)
                    .withAppId(SDKX_APPID)
                    .build();
            GreedyGameAds.initWith(appConfig, new GreedyGameAdsEventsListener() {
                @Override
                public void onInitSuccess() {
                    load_sdkx_ins();
                    Log.e("my_log_gg", "onInitSuccess: ");
                    SharedPreferences.Editor editor = mysharedpreferences.edit();
                    if (mysharedpreferences.getInt("which_id_sdkx_nativ", 0) == 0) {
                        editor.putInt("which_id_sdkx_nativ", 1).apply();
                        sdkx_n = SDKX_N1;
                    } else {
                        editor.putInt("which_id_sdkx_nativ", 0).apply();
                        sdkx_n = SDKX_N2;
                    }

                    if (sdkx_n.isEmpty() || sdkx_adstatus == 0) {
                        nextNativePlatform(banner_container, listner_n);
                        return;
                    }

                    Log.e("GGADS_Nativ", "Enter eeee");

                    final View view = activity.getLayoutInflater().inflate(R.layout.sdkx_banner, banner_container, false);


                    banner_container.removeAllViews();
                    banner_container.addView(view);
                    final GGAdview adview = view.findViewById(R.id.banner);
                    adview.setUnitId(sdkx_n);
                    adview.setAdsMaxWidth(banner_container.getWidth());
                    adview.setAdsMaxHeight(banner_container.getHeight());
                    adview.loadAd(new AdLoadCallback() {
                                      @Override
                                      public void onReadyForRefresh() {
                                          Log.e("GGADS_Nativ", "Ad Ready for refresh");
                                      }

                                      @Override
                                      public void onUiiClosed() {
                                          Log.e("GGADS_Nativ", "Uii closed");
                                      }

                                      @Override
                                      public void onUiiOpened() {
                                          Log.e("GGADS_Nativ", "Uii Opened");
                                      }

                                      @Override
                                      public void onAdLoadFailed(AdErrors cause) {
                                          Log.e("GGADS_Nativ", "onAdLoadFailed " + cause.toString());
                                          nextNativePlatform(banner_container, listner_n);
                                      }

                                      @Override
                                      public void onAdLoaded() {
                                          banner_container.removeAllViews();
                                          banner_container.addView(view);
                                          Log.e("GGADS_Nativ", "onAdLoaded");
                                      }
                                  }
                    );
                }

                @Override
                public void onInitFailed(@NotNull InitErrors initErrors) {
                    Log.e("my_log_gg", "onInitFailed: ");
                }
            });
        }


    }

    private void showFacebookNative(final ViewGroup nativeAdContainer, nativ_listner listner_n) {
        SharedPreferences.Editor editor = mysharedpreferences.edit();
        String facebook_n;
        if (mysharedpreferences.getInt("which_id_fb_nativ", 0) == 0) {
            editor.putInt("which_id_fb_nativ", 1).apply();
            facebook_n = FACEBOOK_N1;
        } else {
            editor.putInt("which_id_fb_nativ", 0).apply();
            facebook_n = FACEBOOK_N2;
        }
        if (facebook_n.isEmpty() || facebook_AdStatus == 0) {
            nextNativePlatform(nativeAdContainer, listner_n);
            return;
        }

        final NativeAd nativeAd = new NativeAd(activity, facebook_n);

        nativeAd.loadAd(nativeAd.buildLoadAdConfig().withAdListener(new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {

            }

            @Override
            public void onError(Ad ad, AdError adError) {
                nextNativePlatform(nativeAdContainer, listner_n);
            }

            @Override
            public void onAdLoaded(Ad ad) {
                if (nativeAd == null || nativeAd != ad) {
                    return;
                }
                listner_n.on_facebook_loded(nativeAd, nativeAdContainer);
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        }).build());
    }


    private void showAdmobNative(final ViewGroup nativeAdContainer, nativ_listner listner_n) {
        SharedPreferences.Editor editor = mysharedpreferences.edit();
        String admob_n;
        if (mysharedpreferences.getInt("which_id_admob_nativ", 0) == 0) {
            editor.putInt("which_id_admob_nativ", 1).apply();
            admob_n = ADMOB_N1;
        } else {
            editor.putInt("which_id_admob_nativ", 0).apply();
            admob_n = ADMOB_N2;
        }
        if (admob_n.isEmpty() || admob_AdStatus == 0) {
            nextNativePlatform(nativeAdContainer, listner_n);
            return;
        }


        final AdLoader adLoader = new AdLoader.Builder(activity, admob_n)
                .forNativeAd(new com.google.android.gms.ads.nativead.NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(@NonNull com.google.android.gms.ads.nativead.NativeAd nativeAd) {

                        listner_n.on_admob_loded(nativeAd, nativeAdContainer);
                    }

                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        // Handle the failure by logging, altering the UI, and so on.
                        nextNativePlatform(nativeAdContainer, listner_n);
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        // Methods in the NativeAdOptions.Builder class can be
                        // used here to specify individual options settings.
                        .build())
                .build();
        adLoader.loadAd(new AdRequest.Builder().build());


    }

    private void showAppNextNative(final ViewGroup nativeAdContainer, nativ_listner listner_n) {
        nextNativePlatform(nativeAdContainer, listner_n);
        return;
    }

    private void showStartappNative(final ViewGroup nativeAdContainer, nativ_listner listner_n) {
        if (STARTAPP_APPID.isEmpty() || startapp_AdStatus == 0) {
            nextNativePlatform(nativeAdContainer, listner_n);
            return;
        }

        final StartAppNativeAd startAppNativeAd = new StartAppNativeAd(activity);

        AdEventListener adListener = new AdEventListener() {
            @Override
            public void onReceiveAd(com.startapp.android.publish.adsCommon.Ad ad) {
                ArrayList<NativeAdDetails> ads = startAppNativeAd.getNativeAds();
                listner_n.on_startapp_loded(ads, nativeAdContainer);

            }

            @Override
            public void onFailedToReceiveAd(com.startapp.android.publish.adsCommon.Ad ad) {
                nextNativePlatform(nativeAdContainer, listner_n);
            }
        };

        NativeAdPreferences preferences = new NativeAdPreferences();
        preferences.setAutoBitmapDownload(true);
        preferences.setPrimaryImageSize(4);
        startAppNativeAd.loadAd(preferences, adListener);
    }


    private void openPortal(String link) {
        String url = link;
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(Color.parseColor("#3d51b9"));
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.intent.setPackage("com.android.chrome");
        customTabsIntent.launchUrl(activity, Uri.parse(url));
    }


    public String get_Qureka_link() {
        String link = mysharedpreferences.getString("app_privacyPolicyLink", "");
        return link;
    }


}
