package com.mynewacc.dreamteam11.cricket.Activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.mynewacc.dreamteam11.R;
import com.mynewacc.dreamteam11.ads.Globs;
import com.mynewacc.dreamteam11.ads.PrivayPolicy;
import com.mynewacc.dreamteam11.guide1.StartActivity;
import com.pesonal.adsdk.AppManage;


public class A5_Start2 extends AppCompatActivity {
    ImageView share_red, rate_red, privacy_red;
    LinearLayout animation_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a5_start2);
        privacy_red = findViewById(R.id.privacy_red);
        rate_red = findViewById(R.id.rate_red);
        share_red = findViewById(R.id.share_red);
        Globs.touch(privacy_red);
        Globs.touch(rate_red);
        Globs.touch(share_red);



        animation_view = findViewById(R.id.animation_view);

        Globs.btn_zooming(animation_view, A5_Start2.this);
        animation_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManage.getInstance(A5_Start2.this).show_INTERSTIAL(A5_Start2.this, new AppManage.MyCallback() {
                    @Override
                    public void callbackCall() {
                        Intent intent = new Intent(A5_Start2.this, StartActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });


        privacy_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManage.getInstance(A5_Start2.this).show_INTERSTIAL(A5_Start2.this, new AppManage.MyCallback() {
                    @Override
                    public void callbackCall() {
                        startActivity(new Intent(A5_Start2.this, PrivayPolicy.class));
                    }
                });
            }
        });
        rate_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                }
            }
        });
        share_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.banner);
                String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), icon, System.currentTimeMillis() + "title", null);
                Globs.shareApps(A5_Start2.this, bitmapPath);

            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        AppManage.getInstance(A5_Start2.this).show_INTERSTIAL(A5_Start2.this, new AppManage.MyCallback() {
            @Override
            public void callbackCall() {
                finish();
            }
        });
    }
}
