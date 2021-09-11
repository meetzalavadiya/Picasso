package com.mynewacc.dreamteam11.guide1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.mynewacc.dreamteam11.R;
import com.mynewacc.dreamteam11.ads.Globs;
import com.mynewacc.dreamteam11.cricket.Activity.CricketStartActivity;
import com.pesonal.adsdk.AppManage;
import com.pesonal.adsdk.AppOpen;

public class StartActivity extends AppOpen {

    LinearLayout animation_view;
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_start);
        fetchAd(new splshADlistner() {
            @Override
            public void onsuccess() {

            }
        });



        animation_view = findViewById(R.id.animation_view);

        Globs.btn_zooming(animation_view, StartActivity.this);
        animation_view.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                AppManage.getInstance(StartActivity.this).show_INTERSTIAL(StartActivity.this, true, new AppManage.MyCallback() {
                    @Override
                    public void callbackCall() {
                        startActivity(new Intent(StartActivity.this, CricketStartActivity.class));
                    }
                });
            }
        });

    }



    @Override
    public void onBackPressed() {
        AppManage.getInstance(StartActivity.this).show_INTERSTIAL(StartActivity.this, true, new AppManage.MyCallback() {
            @Override
            public void callbackCall() {
                finish();
            }
        });

    }


}
