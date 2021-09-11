package com.mynewacc.dreamteam11.guide1;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mynewacc.dreamteam11.R;

public class DetailActivity extends AppCompatActivity {
    LinearLayout festcirclelive_linear_content;
    LinearLayout festcirclelive_linear_title;
    TextView festcirclelive_text_content;
    TextView festcirclelive_text_title;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_detail);

//        AppManage.getInstance(this).banner((ViewGroup) findViewById(R.id.ads));
////        AppManage.getInstance(this).nativeAd((ViewGroup) findViewById(R.id.ads1));
//
//        AppManage.getInstance(this).nativeAd((ViewGroup) findViewById(R.id.ads1), new nativ_Listner() {
//            @Override
//            public void inflate_admob(NativeAd nativeAd) {
//                new Inflate_ADS(DetailActivity.this).inflate_NATIV_ADMOB(nativeAd, (ViewGroup) findViewById(R.id.ads1));
//            }
//        });

        this.festcirclelive_linear_title = (LinearLayout) findViewById(R.id.linear_title);
        this.festcirclelive_linear_content = (LinearLayout) findViewById(R.id.linear_content);
        this.festcirclelive_text_title = (TextView) findViewById(R.id.text_title);
        this.festcirclelive_text_content = (TextView) findViewById(R.id.text_content);

        String stringExtra = getIntent().getStringExtra("key");

        if (stringExtra.equalsIgnoreCase("review")) {
            this.festcirclelive_text_title.setText(getResources().getString(R.string.review));
            this.festcirclelive_text_content.setText(getResources().getString(R.string.review_content));
        }
        if (stringExtra.equalsIgnoreCase("setbox")) {
            this.festcirclelive_text_title.setText(getResources().getString(R.string.setbox));
            this.festcirclelive_text_content.setText(getResources().getString(R.string.setbox_content));
        }
        if (stringExtra.equalsIgnoreCase("remote")) {
            this.festcirclelive_text_title.setText(getResources().getString(R.string.remote));
            this.festcirclelive_text_content.setText(getResources().getString(R.string.remote_content));
        }
        if (stringExtra.equalsIgnoreCase("internet")) {
            this.festcirclelive_text_title.setText(getResources().getString(R.string.internet));
            this.festcirclelive_text_content.setText(getResources().getString(R.string.internet_content));
        }
        if (stringExtra.equalsIgnoreCase("voice")) {
            this.festcirclelive_text_title.setText(getResources().getString(R.string.voice));
            this.festcirclelive_text_content.setText(getResources().getString(R.string.voice_content));
        }
        if (stringExtra.equalsIgnoreCase("dvr")) {
            this.festcirclelive_text_title.setText(getResources().getString(R.string.dvr));
            this.festcirclelive_text_content.setText(getResources().getString(R.string.dvr_content));
        }
        if (stringExtra.equalsIgnoreCase("features")) {
            this.festcirclelive_text_title.setText(getResources().getString(R.string.features));
            this.festcirclelive_text_content.setText(getResources().getString(R.string.features_content));
        }
    }


}
