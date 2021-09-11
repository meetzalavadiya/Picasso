package com.mynewacc.dreamteam11.guide1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.mynewacc.dreamteam11.R;
import com.pesonal.adsdk.AppManage;


public class GuideListActivity extends AppCompatActivity {
    LinearLayout festcirclelive_linear_dvr;
    LinearLayout festcirclelive_linear_features;
    LinearLayout festcirclelive_linear_internet;
    LinearLayout festcirclelive_linear_remote;
    LinearLayout festcirclelive_linear_review;
    LinearLayout festcirclelive_linear_setbox;
    LinearLayout festcirclelive_linear_voice;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_guide_list);

//        AppManage.getInstance(this).nativeAd((ViewGroup) findViewById(R.id.ads1));
//        AppManage.getInstance(this).nativeAd((ViewGroup) findViewById(R.id.ads2));


//        AppManage.getInstance(this).nativeAd((ViewGroup) findViewById(R.id.ads1), new nativ_Listner() {
//            @Override
//            public void inflate_admob(NativeAd nativeAd) {
//                new Inflate_ADS(GuideListActivity.this).inflate_NATIV_ADMOB(nativeAd, (ViewGroup) findViewById(R.id.ads1));
//            }
//        });
//        AppManage.getInstance(this).nativeAd((ViewGroup) findViewById(R.id.ads2), new nativ_Listner() {
//            @Override
//            public void inflate_admob(NativeAd nativeAd) {
//                new Inflate_ADS(GuideListActivity.this).inflate_NATIV_ADMOB(nativeAd, (ViewGroup) findViewById(R.id.ads2));
//            }
//        });



        festcirclelive_linear_review = (LinearLayout) findViewById(R.id.linear_review);
        festcirclelive_linear_setbox = (LinearLayout) findViewById(R.id.linear_setbox);
        festcirclelive_linear_remote = (LinearLayout) findViewById(R.id.linear_remote);
        festcirclelive_linear_internet = (LinearLayout) findViewById(R.id.linear_internet);
        festcirclelive_linear_voice = (LinearLayout) findViewById(R.id.linear_voice);
        festcirclelive_linear_dvr = (LinearLayout) findViewById(R.id.linear_dvr);
        festcirclelive_linear_features = (LinearLayout) findViewById(R.id.linear_features);


        this.festcirclelive_linear_review.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                startDetail("key", "review");
            }
        });
        this.festcirclelive_linear_setbox.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                startDetail("key", "setbox");
            }
        });
        this.festcirclelive_linear_remote.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                startDetail("key", "remote");
            }
        });
        this.festcirclelive_linear_internet.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                startDetail("key", "internet");
            }
        });
        this.festcirclelive_linear_voice.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                startDetail("key", "voice");
            }
        });
        this.festcirclelive_linear_dvr.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                startDetail("key", "dvr");
            }
        });
        this.festcirclelive_linear_features.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                startDetail("key", "features");
            }
        });
    }

    public void startDetail(String key, String value) {
        Intent intent = new Intent(GuideListActivity.this, DetailActivity.class);
        intent.putExtra(key, value);
        AppManage.getInstance(GuideListActivity.this).show_INTERSTIAL(GuideListActivity.this, true, new AppManage.MyCallback() {
            @Override
            public void callbackCall() {
                startActivity(intent);
            }
        });

    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
