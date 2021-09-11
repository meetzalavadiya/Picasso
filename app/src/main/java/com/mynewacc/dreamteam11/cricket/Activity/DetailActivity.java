package com.mynewacc.dreamteam11.cricket.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.VolleyError;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.gson.GsonBuilder;
import com.mynewacc.dreamteam11.cricket.Adapter.ListViewAdapter;
import com.mynewacc.dreamteam11.cricket.Model.PostBean;
import com.mynewacc.dreamteam11.cricket.Models.Entry;
import com.mynewacc.dreamteam11.cricket.Models.PostData;
import com.mynewacc.dreamteam11.cricket.NetworkUtils.ApiHelper;
import com.mynewacc.dreamteam11.cricket.NetworkUtils.ApiListener;
import com.mynewacc.dreamteam11.cricket.Utils.AppUtils;
import com.mynewacc.dreamteam11.cricket.Utils.DatabaseHelper;
import com.mynewacc.dreamteam11.cricket.Utils.PicassoImageGetter;
import com.mynewacc.dreamteam11.R;
import com.pesonal.adsdk.AppManage;
import com.pesonal.adsdk.AppOpen;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DetailActivity extends AppOpen implements ApiListener {
    ApiHelper apiHelper;
    DatabaseHelper helper;
    PicassoImageGetter imageGetter;
    boolean isData;
    PostBean item;
    ImageView ivTeam1;
    ImageView ivTeam2;
    RelativeLayout rlProTeam;
    SwipeRefreshLayout swipe_refresh;
    TextView time;
    CountDownTimer timer;
    TextView tv_content;
    ImageView ivRefresh;
    TextView tv_title;
    String url;
    int pos;

    @Override
    public void onResponseError(VolleyError volleyError, int i) {
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_cricket_detail);

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
//                new Inflate_ADS(DetailActivity.this).inflate_NATIV_ADMOB(nativeAd, (ViewGroup) findViewById(R.id.ads1));
//            }
//        });

        this.helper = DatabaseHelper.getInstance(this);
        this.apiHelper = new ApiHelper(this, this);
        Bundle extras = getIntent().getExtras();
        pos =  extras.getInt("pos");
        if (extras != null && extras.containsKey("item")) {
            this.item = (PostBean) extras.getParcelable("item");
            this.isData = true;
            initView();
            initListener();
            setData();
            setTop(pos);
        }
        if (extras != null && extras.containsKey(ImagesContract.URL)) {
            this.url = extras.getString(ImagesContract.URL);
            this.isData = false;
        }
        initView();
        initListener();
        setData();
        new PostBean();
    }

    private void initView() {
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        this.tv_title = (TextView) findViewById(R.id.tv_title);
        this.time = (TextView) findViewById(R.id.time);
//        this.swipe_refresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        this.tv_content = (TextView) findViewById(R.id.tv_content);
        this.ivRefresh = (ImageView) findViewById(R.id.ivRefresh);
        this.rlProTeam = (RelativeLayout) findViewById(R.id.rlProTeam);
        this.imageGetter = new PicassoImageGetter(this.tv_content, this);
    }

    private void initListener() {
        ivRefresh.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                AppManage.getInstance(DetailActivity.this).show_INTERSTIAL(DetailActivity.this, new AppManage.MyCallback() {
                    @Override
                    public void callbackCall() {
                        apiHit();
                    }
                });

            }
        });
//        this.swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//
//            @Override
//            public void onRefresh() {
//                apiHit();
//                swipe_refresh.setRefreshing(false);
//            }
//        });
        this.rlProTeam.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (AppUtils.isNetConnected(DetailActivity.this)) {
                    openRiskyTeam();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        AppManage.getInstance(DetailActivity.this).show_INTERSTIAL(DetailActivity.this, new AppManage.MyCallback() {
            @Override
            public void callbackCall() {
                DetailActivity.super.onBackPressed();
            }
        });
    }

    private void openRiskyTeam() {
        try {
            final Intent intent = new Intent(this, GlTeamActivity.class);
            intent.putExtra("item", this.item);
            AppManage.getInstance(DetailActivity.this).show_INTERSTIAL(DetailActivity.this, new AppManage.MyCallback() {
                @Override
                public void callbackCall() {
                    startActivity(intent);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void apiHit() {
        if (AppUtils.isNetConnected(this)) {
            boolean z = this.isData;
            if (z && this.item != null) {
                ApiHelper apiHelper2 = this.apiHelper;
                apiHelper2.requestGetStringApi(this.item.getUrl() + "?alt=json", 1, true);
            } else if (!z && this.url != null) {
                ApiHelper apiHelper3 = this.apiHelper;
                apiHelper3.requestGetStringApi(this.url + "?alt=json", 1, true);
            }
        }
    }

    private void setData() {
        Spanned spanned;
        getTitle(this.item.getTitle());
        deadLineUpdate();
        if (this.item.getImgUrl().size() == 2) {
            this.rlProTeam.setVisibility(0);
        } else {
            this.rlProTeam.setVisibility(8);
        }
        try {
            if (this.helper.checkIfExist(this.item.getLinkId())) {
                this.helper.pushSeen(this.item.getLinkId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            spanned = Html.fromHtml(this.item.getContent().replace("Dream Guru","Thop Guru"), 0, this.imageGetter, null);
        } else {
            spanned = Html.fromHtml(this.item.getContent().replace("Dream Guru","Thop Guru"), this.imageGetter, null);
        }
        this.tv_content.setText(spanned);
        Log.e("++++", "setData: " + ((Object) spanned));
        this.tv_content.setMovementMethod(LinkMovementMethod.getInstance());
//        this.swipe_refresh.setRefreshing(false);
    }

    private void fillView() {
        Spanned spanned;
        getTitle(this.item.getTitle());
        deadLineUpdate();
        try {
            if (this.helper.checkIfExist(this.item.getLinkId())) {
                this.helper.pushSeen(this.item.getLinkId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            spanned = Html.fromHtml(this.item.getContent(), 0, this.imageGetter, null);
        } else {
            spanned = Html.fromHtml(this.item.getContent(), this.imageGetter, null);
        }
        this.tv_content.setText(spanned);
        Log.e("++++", "setData: " + ((Object) spanned));
        this.tv_content.setMovementMethod(LinkMovementMethod.getInstance());
//        this.swipe_refresh.setRefreshing(false);
    }

    private void getTitle(String str) {
        try {
            String str2 = str.split("\\|")[0];
            TextView textView = this.tv_title;
            StringBuilder sb = new StringBuilder();
            sb.append(this.item.getLevel());
            sb.append(":");
            sb.append(str2);
            textView.setText(sb.toString());
            Log.e("taxtitle", "taxtitle: " + sb.toString());
            Log.e("items123", "items123: " + ListViewAdapter.split2[0]);
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

    @Override
    public void onResponse(String str, int i) {
        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.serializeNulls();
            this.item = getPostData(((PostData) gsonBuilder.create().fromJson(str, PostData.class)).getEntry());
            fillView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private PostBean getPostData(Entry entry) {
        try {
            PostBean postBean = new PostBean();
            postBean.setLinkId(entry.getId().get$t());
            postBean.setTitle(entry.getTitle().get$t());
            postBean.setUrl(AppUtils.httpsUrl(entry.getLink().get(3).getHref()));
            postBean.setPostTime(entry.getUpdated().get$t());
            postBean.setContent(entry.getContent().get$t());
            postBean.setLevel(entry.getCategory().get(0).getTerm());
            postBean.setImgUrl(extractUrls(entry.getContent().get$t()));
            String titleTimeLine = AppUtils.getTitleTimeLine(entry.getTitle().get$t());
            if (titleTimeLine == null) {
                titleTimeLine = AppUtils.getMilliesForPostTime(entry.getPublished().get$t().split("T")[0]);
            }
            postBean.setDeadLine(titleTimeLine);
            return postBean;
        } catch (Exception e) {
            e.printStackTrace();
            return new PostBean();
        }
    }

    public static List<String> extractUrls(String str) {
        ArrayList arrayList = new ArrayList();
        Matcher matcher = Pattern.compile("https?:/(?:/[^/]+)+\\.(?:jpg|jpeg|gif|png)", 2).matcher(str);
        while (matcher.find()) {
            arrayList.add(str.substring(matcher.start(0), matcher.end(0)));
        }
        return arrayList;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void Back(View view) {
        onBackPressed();
    }


    public void setTop(int pos){

        int drawableId;
        String[] split;
        String[] split2;

        final ImageView iv_team1;
        ImageView iv_team2;
        final TextView time;
        CountDownTimer timer = null;
        TextView tvProTeam;
        TextView tvTeam;
        TextView tv_team1;
        TextView tv_team2;
        TextView tv_title1;

        tv_title1 = (TextView) findViewById(R.id.tv_title1);
        time = (TextView) findViewById(R.id.time1);
        tv_team1 = (TextView) findViewById(R.id.tv_team1);
        tv_team2 = (TextView) findViewById(R.id.tv_team2);
        iv_team1 = (ImageView) findViewById(R.id.iv_team1);
        iv_team2 = (ImageView) findViewById(R.id.iv_team2);
        tvTeam = (TextView) findViewById(R.id.tvTeam);
        tvProTeam = (TextView) findViewById(R.id.tvProTeam);

        final PostBean postBean = ListViewAdapter.customList.get(pos);

        int size = postBean.getImgUrl().size();
        if (size == 0) {
            tvTeam.setText("Team can't Added");
            tvProTeam.setText("Team can't Added");
        } else if (size == 1) {
            tvTeam.setVisibility(0);
            tvProTeam.setText("Team can't Added");
        } else if (size == 2) {
            tvTeam.setVisibility(0);
            tvProTeam.setVisibility(0);
        }
        try {
            String[] split3 = postBean.getTitle().split("\\|");
            split = split3;
            split2 = split3[0].split("(?i)vs");
            String str = split[1];
            try {
                drawableId = getDrawableId(postBean.getLevel());
                Picasso.get().load(drawableId).into(iv_team1);
                Picasso.get().load(drawableId).into(iv_team2);
            } catch (Exception e) {
                Log.e("Exception456", "Exception456: " + e.toString());
                e.printStackTrace();
            }
            getColorId(postBean.getLevel());
        tv_title1.setText(str);
            tv_team1.setText(split2[0]);
            tv_team2.setText(split2[1]);
        } catch (Exception e2) {
            Log.e("Exception789", "Exception789: " + e2.toString());
            tv_title1.setText(postBean.getTitle());
        }
        try {
            if (postBean.getDeadLine() == null) {
                time.setText("🕒 " + AppUtils.getDate(postBean.getPostTime().split("T")[0]));
                time.setTextColor(getResources().getColor(R.color.white));
            } else if (Long.valueOf(postBean.getDeadLine()).longValue() > System.currentTimeMillis()) {
                time.setTextColor(getResources().getColor(R.color.white));
                long longValue = Long.valueOf(postBean.getDeadLine()).longValue() - System.currentTimeMillis();
                if (timer != null) {
                    timer.cancel();
                }
                timer = new CountDownTimer(longValue, 1000) {

                    public void onFinish() {
                        TextView textView = time;
                        textView.setText("🕒 " + AppUtils.getMilliesToStr2(Long.valueOf(postBean.getDeadLine()).longValue()));
                        time.setTextColor(getResources().getColor(R.color.white));
                    }

                    public void onTick(long j) {
                        int i = (int) (j / 3600000);
                        String str = String.format("%02d", Integer.valueOf(i)) + "h " + String.format("%02d", Integer.valueOf((int) ((j / 60000) % 60))) + "m " + String.format("%02d", Integer.valueOf(((int) (j / 1000)) % 60)) + "s";
                        time.setText("🕒 " + str);
                    }
                }.start();
            } else {
                TextView textView2 = time;
                textView2.setText("🕒 " + AppUtils.getMilliesToStr2(Long.valueOf(postBean.getDeadLine()).longValue()));
            }
        } catch (Exception e3) {
            Log.e("Exception123", "Exception123: " + e3.toString());
            e3.printStackTrace();
        }
    }

    private int getDrawableId(String str) {
        char c;
        switch (str.hashCode()) {
            case -2128102205:
                if (str.equals(AppUtils.HOCKEY_L)) {
                    c = 3;
                    break;
                }
            case -1598014511:
                if (str.equals(AppUtils.CRICKET_L)) {
                    c = 0;
                    break;
                }
            case 77069:
                if (str.equals(AppUtils.NBA_L)) {
                    c = 4;
                    break;
                }
            case 459313037:
                if (str.equals(AppUtils.FOOTBALL_L)) {
                    c = 1;
                    break;
                }
            case 713821076:
                if (str.equals(AppUtils.KABADDI_L)) {
                    c = 2;
                    break;
                }
            default:
                c = 65535;
                break;
        }
        if (c == 0) {
            return R.drawable.cricket;
        }
        if (c == 1) {
            return R.drawable.football;
        }
        if (c == 2) {
            return R.drawable.kabaddi;
        }
        if (c != 3) {
            return c != 4 ? R.drawable.cricket : R.drawable.nba;
        }
        return R.drawable.hockey;
    }

    private int getColorId(String str) {
        char c;
        switch (str.hashCode()) {
            case -2128102205:
                if (str.equals(AppUtils.HOCKEY_L)) {
                    c = 3;
                    break;
                }
            case -1598014511:
                if (str.equals(AppUtils.CRICKET_L)) {
                    c = 0;
                    break;
                }
            case 77069:
                if (str.equals(AppUtils.NBA_L)) {
                    c = 4;
                    break;
                }
            case 459313037:
                if (str.equals(AppUtils.FOOTBALL_L)) {
                    c = 1;
                    break;
                }
            case 713821076:
                if (str.equals(AppUtils.KABADDI_L)) {
                    c = 2;
                    break;
                }
            default:
                c = 65535;
                break;
        }
        if (c == 0) {
            return R.color.colorAccentAlfa1;
        }
        if (c == 1) {
            return R.color.colorAccentAlfa2;
        }
        if (c != 2) {
            return c != 3 ? R.color.colorAccentAlfa1 : R.color.colorAccentAlfa4;
        }
        return R.color.colorAccentAlfa3;
    }

}
