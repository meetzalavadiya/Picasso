package com.mynewacc.dreamteam11.cricket.Activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mynewacc.dreamteam11.cricket.Model.PostBean;
import com.mynewacc.dreamteam11.cricket.Utils.AppUtils;
import com.mynewacc.dreamteam11.R;
import com.pesonal.adsdk.AppManage;
import com.pesonal.adsdk.AppOpen;

import com.squareup.picasso.Picasso;

public class GlTeamActivity extends AppOpen {
    PostBean item;
    ImageView ivProTeam;
    ImageView ivRefresh;
    TextView time;
    CountDownTimer timer;
    TextView tv_title;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_gl_team);

        fetchAd(new splshADlistner() {
            @Override
            public void onsuccess() {

            }
        });

//        AppManage.getInstance(this).nativeAd((ViewGroup) findViewById(R.id.ads1));

//
//        AppManage.getInstance(this).nativeAd((ViewGroup) findViewById(R.id.ads1), new nativ_Listner() {
//            @Override
//            public void inflate_admob(NativeAd nativeAd) {
//                new Inflate_ADS(GlTeamActivity.this).inflate_NATIV_ADMOB(nativeAd, (ViewGroup) findViewById(R.id.ads1));
//            }
//        });

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("item")) {
            this.item = (PostBean) extras.getParcelable("item");
            initView();
            initListener();
            setData();
        }
    }

    private void initView() {
        this.tv_title = (TextView) findViewById(R.id.tv_title);
        this.time = (TextView) findViewById(R.id.time);
        this.ivProTeam = (ImageView) findViewById(R.id.ivProTeam);
        this.ivRefresh = (ImageView) findViewById(R.id.ivRefresh);
    }

    private void initListener() {
        this.ivRefresh.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                AppManage.getInstance(GlTeamActivity.this).show_INTERSTIAL(GlTeamActivity.this, new AppManage.MyCallback() {
                    @Override
                    public void callbackCall() {
                        loadImg();
                    }
                });

            }
        });
    }

    private void loadImg() {
        if (AppUtils.isNetConnected(this)) {
            Picasso.get().load(this.item.getImgUrl().get(1)).into(this.ivProTeam);
        }
    }

    private void setData() {
        loadImg();
        fillView();
    }

    private void fillView() {
        getTitle(this.item.getTitle());
        deadLineUpdate();
    }

    private void getTitle(String str) {
        try {
            String str2 = str.split("\\|")[0];
            TextView textView = this.tv_title;
            textView.setText(this.item.getLevel() + ":" + str2);
        } catch (Exception unused) {
            TextView textView2 = this.tv_title;
            textView2.setText(this.item.getLevel() + ":" + str);
        }
    }

    private void deadLineUpdate() {
        try {
            if (this.item.getDeadLine() != null) {
                if (Long.valueOf(this.item.getDeadLine()).longValue() > System.currentTimeMillis()) {
                    this.time.setTextColor(getResources().getColor(R.color.colorActive));
                    long longValue = Long.valueOf(this.item.getDeadLine()).longValue() - System.currentTimeMillis();
                    CountDownTimer countDownTimer = this.timer;
                    if (countDownTimer != null) {
                        countDownTimer.cancel();
                    }
                    this.timer = new CountDownTimer(longValue, 1000) {

                        public void onFinish() {
                            time.setText("🕒 Deadline End");
                            time.setTextColor(getResources().getColor(R.color.colorDead));
                        }

                        public void onTick(long j) {
                            int i = (int) (j / 3600000);
                            String str = String.format("%02d", Integer.valueOf(i)) + "h " + String.format("%02d", Integer.valueOf((int) ((j / 60000) % 60))) + "m " + String.format("%02d", Integer.valueOf(((int) (j / 1000)) % 60)) + "s";
                            time.setText("🕒 " + str);
                        }
                    }.start();
                    return;
                }
                this.time.setText("🕒 Deadline End");
                this.time.setTextColor(getResources().getColor(R.color.colorDead));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sharePost() {
        String str;
        if (this.item.getDeadLine() == null || this.item.getDeadLine().trim().length() <= 0) {
            str = "🕒 " + AppUtils.getDate(this.item.getPostTime().split("T")[0]);
        } else {
            str = "🕒 Deadline : " + AppUtils.getMilliesToStr2(Long.valueOf(this.item.getDeadLine()).longValue());
        }
        AppUtils.shareIt(this, shareTitle(this.item.getLevel() + " | " + this.item.getTitle()) + "\n" + str + "\n" + "🏆 Select team and win more." + "\n" + "\n" + "👉 " + AppUtils.AppURL + getPackageName());
    }

    private String shareTitle(String str) {
        try {
            String[] split = str.split("\\|");
            return split[0] + " | " + split[1];
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        AppManage.getInstance(GlTeamActivity.this).show_INTERSTIAL(GlTeamActivity.this, new AppManage.MyCallback() {
            @Override
            public void callbackCall() {
                GlTeamActivity.super.onBackPressed();
            }
        });
    }

    public void Back(View view) {
        onBackPressed();
    }
}
