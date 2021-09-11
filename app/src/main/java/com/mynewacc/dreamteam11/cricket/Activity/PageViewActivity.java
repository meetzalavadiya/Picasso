package com.mynewacc.dreamteam11.cricket.Activity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.VolleyError;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.gson.GsonBuilder;
import com.mynewacc.dreamteam11.cricket.Model.PostBean;
import com.mynewacc.dreamteam11.cricket.Models.Entry;
import com.mynewacc.dreamteam11.cricket.Models.PostData;
import com.mynewacc.dreamteam11.cricket.NetworkUtils.ApiHelper;
import com.mynewacc.dreamteam11.cricket.NetworkUtils.ApiListener;
import com.mynewacc.dreamteam11.cricket.Utils.AppUtils;
import com.mynewacc.dreamteam11.cricket.Utils.PicassoImageGetter;
import com.mynewacc.dreamteam11.cricket.Utils.SessionUtils;
import com.mynewacc.dreamteam11.R;
import com.squareup.picasso.Picasso;

public class PageViewActivity extends AppCompatActivity implements ApiListener {
    ApiHelper apiHelper;
    PicassoImageGetter imageGetter;
    PostBean item;
    SwipeRefreshLayout swipe_refresh;
    String title;
    TextView tv_content;
    TextView tv_title;
    String url;

    @Override
    public void onResponseError(VolleyError volleyError, int i) {
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_page_view);
        this.apiHelper = new ApiHelper(this, this);
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(ImagesContract.URL)) {
            this.url = extras.getString(ImagesContract.URL);
            this.title = extras.getString(AppUtils.PUSH_TITLE);
        }
        Picasso.get().load(R.drawable.screen_bg).into((ImageView) findViewById(R.id.iv_bg));
        initView();
        initListener();
        apiHit();
        SessionUtils.getInstance(this).isDev();
    }

    private void initView() {
        this.tv_title = (TextView) findViewById(R.id.tv_title);
        this.tv_content = (TextView) findViewById(R.id.tv_content);
        this.swipe_refresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        this.imageGetter = new PicassoImageGetter(this.tv_content, this);
    }

    private void initListener() {
        this.swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            /* class com.yd.expert.predictionforwinningteam.fentacyteamexpert.AppData.Activity.PageViewActivity.AnonymousClass1 */

            @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
            public void onRefresh() {
                PageViewActivity.this.apiHit();
                PageViewActivity.this.swipe_refresh.setRefreshing(false);
            }
        });
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void apiHit() {
        if (AppUtils.isNetConnected(this)) {
            ApiHelper apiHelper2 = this.apiHelper;
            apiHelper2.requestGetStringApi(this.url + "?alt=json", 1, true);
            return;
        }
        this.tv_content.setText(" ");
    }

    @Override // androidx.activity.ComponentActivity
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // com.yd.expert.predictionforwinningteam.fentacyteamexpert.AppData.NetworkUtils.ApiListener
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

    private void fillView() {
        Spanned spanned;
        this.tv_title.setText(this.item.getTitle());
        if (Build.VERSION.SDK_INT >= 24) {
            spanned = Html.fromHtml(this.item.getContent(), 0, this.imageGetter, null);
        } else {
            spanned = Html.fromHtml(this.item.getContent(), this.imageGetter, null);
        }
        this.tv_content.setText(spanned);
        this.tv_content.setMovementMethod(LinkMovementMethod.getInstance());
        this.swipe_refresh.setRefreshing(false);
    }

    private PostBean getPostData(Entry entry) {
        try {
            PostBean postBean = new PostBean();
            postBean.setLinkId(entry.getId().get$t());
            postBean.setTitle(entry.getTitle().get$t());
            postBean.setUrl(AppUtils.httpsUrl(entry.getLink().get(1).getHref()));
            postBean.setContent(entry.getContent().get$t());
            return postBean;
        } catch (Exception e) {
            e.printStackTrace();
            return new PostBean();
        }
    }
}
