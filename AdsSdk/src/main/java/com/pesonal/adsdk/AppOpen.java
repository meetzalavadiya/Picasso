package com.pesonal.adsdk;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.greedygame.core.app_open_ads.general.AdOrientation;
import com.greedygame.core.app_open_ads.general.AppOpenAdsEventsListener;
import com.greedygame.core.app_open_ads.general.GGAppOpenAds;
import com.greedygame.core.models.general.AdErrors;

public class AppOpen extends AppCompatActivity {
    private Activity myApplication;
    private Activity currentActivity;
    private AppOpenAd appOpenAd = null;
    String TAG = "my_log_111";

    private AppOpenAd.AppOpenAdLoadCallback loadCallback;
    private Dialog pd;
    splshADlistner splshADlistner;

    public interface splshADlistner {

        void onsuccess();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApplication = AppOpen.this;


    }

    private AdRequest getAdRequest() {
        return new AdRequest.Builder().build();
    }

    public void fetchAd(final splshADlistner listner) {

        pd = new Dialog(AppOpen.this);
        View view = getLayoutInflater().inflate(R.layout.ad_loading, null);
        pd.setContentView(view);
        pd.setCancelable(false);
        pd.show();
        Window window = pd.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        splshADlistner = listner;
        loadCallback =
                new AppOpenAd.AppOpenAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull AppOpenAd ad) {
                        super.onAdLoaded(ad);
                        appOpenAd = ad;
                        FullScreenContentCallback fullScreenContentCallback =
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        Log.e(TAG, "onAdDismissedFullScreenContent: ");
                                        listner.onsuccess();
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        Log.e(TAG, "onAdFailedToShowFullScreenContent: ");
                                        pd.dismiss();
                                        AppManage.getInstance(AppOpen.this).show_INTERSTIAL(AppOpen.this, new AppManage.MyCallback() {
                                            @Override
                                            public void callbackCall() {
                                                Log.e(TAG, "Faild to Show Then Show Interstitial..");
                                                listner.onsuccess();
                                            }
                                        });
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        pd.dismiss();
                                        Log.e(TAG, "onAdShowedFullScreenContent: ");

                                    }
                                };

                        appOpenAd.show(currentActivity);
                        appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);

                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                        Log.e(TAG, ": onAdFailedToLoad");
                        pd.dismiss();
                        AppManage.getInstance(AppOpen.this).show_INTERSTIAL(AppOpen.this, new AppManage.MyCallback() {
                            @Override
                            public void callbackCall() {
                                Log.e(TAG, "callbackCall: onAdFailedToLoad");
                                listner.onsuccess();
                            }
                        });
                    }

                };
        SharedPreferences preferences = getSharedPreferences("which_open", 0);
        SharedPreferences.Editor editor = preferences.edit();

        if (AppManage.getInstance(AppOpen.this).is_app_open_avilabel() && AppManage.getInstance(AppOpen.this).is_sdkx_app_open_avilabel()) {
            Log.d("GGADS", "AppOpenAd 2 on");
            if (preferences.getBoolean("which", true)) {
                Log.d("GGADS", "AppOpenAd 2 on = 1");
                editor.putBoolean("which", false).apply();
                sdkx_ad();
            } else {
                Log.d("GGADS", "AppOpenAd 2 on = 2");
                editor.putBoolean("which", true).apply();
                adx_ad();
            }
            GGAppOpenAds.setOrientation(AdOrientation.PORTRAIT);
        } else if (AppManage.getInstance(AppOpen.this).is_app_open_avilabel()) {
            Log.d("GGADS", "AppOpenAd 1 on");
            adx_ad();
        } else if (AppManage.getInstance(AppOpen.this).is_sdkx_app_open_avilabel()) {
            Log.d("GGADS", "AppOpenAd 2 on");
            sdkx_ad();
        } else {
            Log.d("GGADS", "AppOpenAd 2 else");
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "fetchAd: 111");
                    pd.dismiss();
                    AppManage.getInstance(AppOpen.this).show_INTERSTIAL(AppOpen.this, new AppManage.MyCallback() {
                        @Override
                        public void callbackCall() {
                            Log.e(TAG, "callbackCall: onAdFailedToLoad");
                            listner.onsuccess();
                        }
                    });
                }
            }, 4000);

        }

    }

    public void adx_ad() {
        AdRequest request = getAdRequest();
        AppOpenAd.load(
                myApplication, AppManage.getInstance(AppOpen.this).get_appopen_id(), request,
                AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);
    }

    public void sdkx_ad() {

        GGAppOpenAds.setOrientation(AdOrientation.PORTRAIT);
        GGAppOpenAds.setListener(new AppOpenAdsEventsListener() {
            @Override
            public void onAdLoaded() {
                GGAppOpenAds.show();
                Log.d("GGADS", "AppOpenAd loaded");
            }

            @Override
            public void onAdLoadFailed(AdErrors cause) {
                Log.d("GGADS", "AppOpenAd load failed " + cause);
                pd.dismiss();
                AppManage.getInstance(AppOpen.this).show_INTERSTIAL(AppOpen.this, new AppManage.MyCallback() {
                    @Override
                    public void callbackCall() {
                        Log.e(TAG, "callbackCall: onAdFailedToLoad");
                        splshADlistner.onsuccess();
                    }
                });
            }

            @Override
            public void onAdShowFailed() {
                pd.dismiss();
                AppManage.getInstance(AppOpen.this).show_INTERSTIAL(AppOpen.this, new AppManage.MyCallback() {
                    @Override
                    public void callbackCall() {
                        Log.e(TAG, "callbackCall: onAdFailedToLoad");
                        splshADlistner.onsuccess();
                    }
                });
            }

            @Override
            public void onAdOpened() {
                Log.d("GGADS", "AppOpenAd Opened");
            }

            @Override
            public void onAdClosed() {
                Log.d("GGADS", "AppOpenAd closed");
                pd.dismiss();
                splshADlistner.onsuccess();
            }

        });
        GGAppOpenAds.loadAd(AppManage.getInstance(AppOpen.this).get_appopen_id_sdkx());
    }


    @Override
    protected void onResume() {
        super.onResume();
        currentActivity = AppOpen.this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentActivity = AppOpen.this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        currentActivity = null;
    }

}