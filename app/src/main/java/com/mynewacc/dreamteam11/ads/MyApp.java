package com.mynewacc.dreamteam11.ads;

import android.app.Application;

import com.onesignal.OneSignal;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .init();
    }

}
