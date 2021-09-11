package com.mynewacc.dreamteam11.guide1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.mynewacc.dreamteam11.R;
import com.mynewacc.dreamteam11.cricket.Activity.CricketStartActivity;
import com.pesonal.adsdk.AppManage;

public class MainActivity extends AppCompatActivity {
    LinearLayout festcirclelive_linear_guide;
    LinearLayout festcirclelive_linear_start;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);

//        AppManage.getInstance(this).banner((ViewGroup) findViewById(R.id.ads));
////        AppManage.getInstance(this).nativeAd((ViewGroup) findViewById(R.id.ads1));
//
//
//        AppManage.getInstance(this).nativeAd((ViewGroup) findViewById(R.id.ads1), new nativ_Listner() {
//            @Override
//            public void inflate_admob(NativeAd nativeAd) {
//                new Inflate_ADS(MainActivity.this).inflate_NATIV_ADMOB(nativeAd, (ViewGroup) findViewById(R.id.ads1));
//            }
//        });


        festcirclelive_linear_start = (LinearLayout) findViewById(R.id.linear_start);
        festcirclelive_linear_guide = (LinearLayout) findViewById(R.id.linear_guide);


        festcirclelive_linear_start.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                AppManage.getInstance(MainActivity.this).show_INTERSTIAL(MainActivity.this, true, new AppManage.MyCallback() {
                    @Override
                    public void callbackCall() {
                        startActivity(new Intent(MainActivity.this, CricketStartActivity.class));
                        finish();
                    }
                });
            }
        });

        festcirclelive_linear_guide.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                AppManage.getInstance(MainActivity.this).show_INTERSTIAL(MainActivity.this, true, new AppManage.MyCallback() {
                    @Override
                    public void callbackCall() {
                        startActivity(new Intent(MainActivity.this, GuideListActivity.class));
                        finish();
                    }
                });
            }
        });

    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(MainActivity.this, StartActivity.class));
        finish();
    }


}
