package com.mynewacc.dreamteam11.ads;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mynewacc.dreamteam11.BuildConfig;
import com.mynewacc.dreamteam11.R;
import com.mynewacc.dreamteam11.cricket.Activity.Privacy_Policy;
import com.pesonal.adsdk.ADS_SplashActivity;
import com.pesonal.adsdk.getDataListner;

import org.json.JSONObject;

public class SplashActivity extends ADS_SplashActivity {

    LinearLayout retry_view;
    TextView retry_buttton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_splash);

        retry_view = findViewById(R.id.retry_view);
        retry_buttton = findViewById(R.id.retry_buttton);


        ADSinit(SplashActivity.this, BuildConfig.VERSION_CODE, retry_view, retry_buttton, new getDataListner() {
            @Override
            public void onsuccess() {
                startActivity(new Intent(SplashActivity.this, Privacy_Policy.class));
                finish();
            }

            @Override
            public void onUpdate(String url) {
                update(url);
            }

            @Override
            public void onRedirect(String url) {
                redirect(url);
            }

            @Override
            public void reloadActivity() {
                startActivity(new Intent(SplashActivity.this, SplashActivity.class));
                finish();
            }

            @Override
            public void ongetExtradata(JSONObject extraData) {
            }

        });


    }


    void update(final String url) {
        final Dialog dialog = new Dialog(SplashActivity.this);
        dialog.setCancelable(false);
        View view = LayoutInflater.from(SplashActivity.this).inflate(R.layout.updatenewdialog, null);
        dialog.setContentView(view);
        TextView update;
        update = view.findViewById(R.id.update);

        TextView update_text_en = view.findViewById(R.id.update_text_en);
        TextView update_text_hi = view.findViewById(R.id.update_text_hi);

        update_text_en.setText("Please update the " + getResources().getString(R.string.app_name) + " , Do not miss latest Features.");
        update_text_hi.setText("कृपया " + getResources().getString(R.string.app_name) + " को अपडेट करें नवीनतम सुविधाओं को न चूकें");


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Uri marketUri = Uri.parse(url);
                    Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
                    startActivity(marketIntent);
                } catch (ActivityNotFoundException ignored) {
                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.create();
        }
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }

    void redirect(final String url) {

        final Dialog dialog = new Dialog(SplashActivity.this);
        dialog.setCancelable(false);
        View view = getLayoutInflater().inflate(R.layout.installnewappdialog, null);
        dialog.setContentView(view);
        TextView update;
        update = view.findViewById(R.id.update);


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Uri marketUri = Uri.parse(url);
                    Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
                    startActivity(marketIntent);
                } catch (Exception ignored) {
                    try {
                        Uri marketUri = Uri.parse(url);
                        Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
                        startActivity(marketIntent);
                    } catch (ActivityNotFoundException ignored1) {
                    }
                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.create();
        }

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }


}