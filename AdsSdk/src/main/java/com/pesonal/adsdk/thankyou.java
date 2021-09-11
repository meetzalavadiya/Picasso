package com.pesonal.adsdk;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.nativead.NativeAd;
import com.hsalf.smileyrating.SmileyRating;
import com.startapp.android.publish.ads.nativead.NativeAdDetails;

import java.util.ArrayList;

public class thankyou extends AppCompatActivity {

    LinearLayout rating_view;

    SmileyRating smile_rating;

    TextView later, submit;

    boolean is_dark = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (is_dark) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.parseColor("#353639"));
            }
            setContentView(R.layout.activity_thankyou_dark);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.WHITE);
            }
            setContentView(R.layout.activity_thankyou_light);

        }


        SharedPreferences preferences = getSharedPreferences("israteus", 0);
        final SharedPreferences.Editor editor = preferences.edit();
        rating_view = findViewById(R.id.rating);

        smile_rating = findViewById(R.id.smile_rating);
        if (preferences.getBoolean("rate", false)) {
            rating_view.setVisibility(View.GONE);
        }
        smile_rating.setSmileySelectedListener(new SmileyRating.OnSmileySelectedListener() {
            @Override
            public void onSmileySelected(SmileyRating.Type type) {

            }
        });

        later = findViewById(R.id.later);
        submit = findViewById(R.id.submit);
        later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating_view.animate().alpha(0.0f);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean("rate", true).apply();
                rating_view.animate().alpha(0.0f);
                try {
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + getPackageName())));
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                }
            }
        });

        TextView exit_yes = findViewById(R.id.exit_yes);
        exit_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
        });

        TextView exit_no = findViewById(R.id.exit_no);
        exit_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManage.getInstance(thankyou.this).show_INTERSTIAL(thankyou.this, new AppManage.MyCallback() {
                    @Override
                    public void callbackCall() {
                        finish();
                    }
                });


            }
        });

        AppManage.getInstance(this).nativeAd((ViewGroup) findViewById(R.id.ads), new nativ_listner() {
            @Override
            public void on_admob_loded(NativeAd ad, ViewGroup containor) {
                new view_Ad_sdk(thankyou.this).inflate_NATIV_ADMOB(ad, containor,false);
            }

            @Override
            public void on_facebook_loded(com.facebook.ads.NativeAd ad, ViewGroup containor) {
                new view_Ad_sdk(thankyou.this).inflate_NATIV_FB(ad, containor,false);

            }

            @Override
            public void on_startapp_loded(ArrayList<NativeAdDetails> ad, ViewGroup containor) {
                new view_Ad_sdk(thankyou.this).inflate_NB_STARTAPP(ad, containor,false);
            }
        });

    }
}