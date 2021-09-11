package com.mynewacc.dreamteam11.cricket.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.mynewacc.dreamteam11.R;
import com.mynewacc.dreamteam11.cricket.Utils.AppUtils;
import com.mynewacc.dreamteam11.cricket.Utils.SessionUtils;
import com.pesonal.adsdk.AppManage;

public class CricketStartActivity extends AppCompatActivity {
    SessionUtils sessionUtils;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_cricket_start);

        this.sessionUtils = SessionUtils.getInstance(this);



        View.OnTouchListener touch_anim = new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                try {
                    int action = motionEvent.getAction();
                    if (action == 0) {
                        view.clearAnimation();
                        view.animate().scaleX(0.92f).setDuration(50).start();
                        view.animate().scaleY(0.92f).setDuration(50).start();
                    } else if (action == 1) {
                        view.clearAnimation();
                        view.animate().scaleX(1.0f).setDuration(50).start();
                        view.animate().scaleY(1.0f).setDuration(50).start();
                    } else if (2 != motionEvent.getAction()) {
                        view.clearAnimation();
                        view.animate().scaleX(1.0f).setDuration(50).start();
                        view.animate().scaleY(1.0f).setDuration(50).start();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        };
        findViewById(R.id.tvAllMatch).setOnTouchListener(touch_anim);
        findViewById(R.id.tvCricket).setOnTouchListener(touch_anim);
        findViewById(R.id.tvFootball).setOnTouchListener(touch_anim);
        findViewById(R.id.tvHockey).setOnTouchListener(touch_anim);
        findViewById(R.id.tvNBA).setOnTouchListener(touch_anim);

        findViewById(R.id.tvAllMatch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionUtils.setTabValue(AppUtils.ALL_L);
                detailsView();
            }
        });

        findViewById(R.id.tvCricket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionUtils.setTabValue(AppUtils.CRICKET_L);
                detailsView();
            }
        });

        findViewById(R.id.tvFootball).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionUtils.setTabValue(AppUtils.FOOTBALL_L);
                detailsView();
            }
        });

        findViewById(R.id.tvHockey).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionUtils.setTabValue(AppUtils.HOCKEY_L);
                detailsView();
            }
        });

        findViewById(R.id.tvNBA).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionUtils.setTabValue(AppUtils.NBA_L);
                detailsView();
            }
        });


    }

    @Override
    public void onBackPressed() {
        AppManage.getInstance(CricketStartActivity.this).show_INTERSTIAL(CricketStartActivity.this, new AppManage.MyCallback() {
            @Override
            public void callbackCall() {
                CricketStartActivity.super.onBackPressed();
            }
        });
    }

    public void detailsView() {
        AppManage.getInstance(CricketStartActivity.this).show_INTERSTIAL(CricketStartActivity.this,  new AppManage.MyCallback() {
            @Override
            public void callbackCall() {
                startActivity(new Intent(CricketStartActivity.this, HomeActivity.class));
            }
        });
    }


}
