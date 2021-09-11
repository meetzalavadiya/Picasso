package com.pesonal.adsdk;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;

import com.bumptech.glide.Glide;

public class Qureka_Icon extends androidx.appcompat.widget.AppCompatImageView {

    public SharedPreferences mysharedpreferences;
    SharedPreferences.Editor editor;
    public Context context;

    public Qureka_Icon(@NonNull Context context) {
        super(context);
        this.context = context;
        final String gamezoap_link = AppManage.getInstance((Activity) context).get_Qureka_link();
        mysharedpreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        editor = mysharedpreferences.edit();

        if (gamezoap_link.equalsIgnoreCase("")) {
            setVisibility(View.GONE);
        } else {
            setVisibility(View.VISIBLE);

            if (AppManage.getInstance((Activity) context).get_gif().contains(".gif")) {
                Glide.with(context)
                        .asGif()
                        .load(AppManage.getInstance((Activity) context).get_gif())
                        .into(this);
            } else {
                Glide.with(context)
                        .load(AppManage.getInstance((Activity) context).get_gif())
                        .into(this);
            }


            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    openPortal(gamezoap_link);
                }
            });

        }

    }

    public Qureka_Icon(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        final String gamezoap_link = AppManage.getInstance((Activity) context).get_Qureka_link();
        mysharedpreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        editor = mysharedpreferences.edit();

        if (gamezoap_link.equalsIgnoreCase("")) {
            setVisibility(View.GONE);
        } else {
            setVisibility(View.VISIBLE);
            if (AppManage.getInstance((Activity) context).get_gif().contains(".gif")) {
                Glide.with(context)
                        .asGif()
                        .load(AppManage.getInstance((Activity) context).get_gif())

                        .into(this);
            } else {
                Glide.with(context)
                        .load(AppManage.getInstance((Activity) context).get_gif())
                        .into(this);
            }

            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    openPortal(gamezoap_link);
                }
            });

        }
    }

    public Qureka_Icon(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        final String gamezoap_link = AppManage.getInstance((Activity) context).get_Qureka_link();
        mysharedpreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        editor = mysharedpreferences.edit();

        if (gamezoap_link.equalsIgnoreCase("")) {
            setVisibility(View.GONE);
        } else {
            setVisibility(View.VISIBLE);
            if (AppManage.getInstance((Activity) context).get_gif().contains(".gif")) {
                Glide.with(context)
                        .asGif()
                        .load(AppManage.getInstance((Activity) context).get_gif())
                        .into(this);
            } else {
                Glide.with(context)
                        .load(AppManage.getInstance((Activity) context).get_gif())
                        .into(this);
            }
            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    openPortal(gamezoap_link);
                }
            });

        }
    }

    private void openPortal(String link) {
        String url = link;
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(Color.parseColor("#3d51b9"));
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.intent.setPackage("com.android.chrome");
        customTabsIntent.launchUrl(context, Uri.parse(url));
    }


}
