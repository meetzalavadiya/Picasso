package com.mynewacc.dreamteam11.cricket.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.VolleyError;
import com.google.gson.GsonBuilder;
import com.mynewacc.dreamteam11.cricket.Adapter.ListViewAdapter;
import com.mynewacc.dreamteam11.cricket.Adapter.TabViewAdapter;
import com.mynewacc.dreamteam11.cricket.Model.PostBean;
import com.mynewacc.dreamteam11.cricket.Model.PushBean;
import com.mynewacc.dreamteam11.cricket.Models.BloggerData;
import com.mynewacc.dreamteam11.cricket.Models.Category;
import com.mynewacc.dreamteam11.cricket.Models.Entry;
import com.mynewacc.dreamteam11.cricket.Models.Feed;
import com.mynewacc.dreamteam11.cricket.NetworkUtils.ApiHelper;
import com.mynewacc.dreamteam11.cricket.NetworkUtils.ApiListener;
import com.mynewacc.dreamteam11.cricket.Notification.MyNotificationActivity;
import com.mynewacc.dreamteam11.cricket.Utils.AppUtils;
import com.mynewacc.dreamteam11.cricket.Utils.DatabaseHelper;
import com.mynewacc.dreamteam11.cricket.Utils.SessionUtils;
import com.mynewacc.dreamteam11.R;
import com.pesonal.adsdk.AppManage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, ApiListener {
    public static int position;
    public static List<PostBean> postsList;
    ApiHelper apiHelper;
    int clickedPosition = 0;
    String count;
    DatabaseHelper dbHelper;
    boolean doubleBackToExitPressedOnce = false;
    List<String> levelList;
    ListViewAdapter listViewAdapter;
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            checkTab();
        }
    };
    RecyclerView recycler_tab;
    RecyclerView recycler_view;
    SessionUtils sessionUtils;
    private SwipeRefreshLayout swiperefresh;
    TabViewAdapter tabViewAdapter;
    TextView tv_refresh;
    TextView tv_titles;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_home);

        this.sessionUtils = SessionUtils.getInstance(this);
        this.apiHelper = new ApiHelper(this, this);
        this.dbHelper = DatabaseHelper.getInstance(this);
        this.count = getIntent().getStringExtra("item");
        this.tv_titles = (TextView) findViewById(R.id.tv_titles);
        String tabValue = this.sessionUtils.getTabValue();
        if (tabValue.equals(AppUtils.ALL_L)) {
            this.tv_titles.setText("All Match");
        } else if (tabValue.equals(AppUtils.CRICKET_L)) {
            this.tv_titles.setText(AppUtils.CRICKET_L);
        } else if (tabValue.equals(AppUtils.FOOTBALL_L)) {
            this.tv_titles.setText(AppUtils.FOOTBALL_L);
        } else if (tabValue.equals(AppUtils.HOCKEY_L)) {
            this.tv_titles.setText(AppUtils.HOCKEY_L);
        } else if (tabValue.equals(AppUtils.NBA_L)) {
            this.tv_titles.setText(AppUtils.NBA_L);
        }
        initView();
        initListener();
        checkTab();
    }

    private void initView() {
        this.tv_refresh = (TextView) findViewById(R.id.tv_refresh);
        this.swiperefresh = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        this.recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        this.recycler_view.setLayoutManager(new LinearLayoutManager(this, 1, false));
        this.recycler_view.setItemAnimator(new DefaultItemAnimator());
        this.recycler_tab = (RecyclerView) findViewById(R.id.recycler_tab);
        this.recycler_tab.setLayoutManager(new LinearLayoutManager(this, 0, false));
        this.recycler_tab.setItemAnimator(new DefaultItemAnimator());
    }

    private void initListener() {
        this.swiperefresh.setOnRefreshListener(this);
        this.tv_refresh.setOnClickListener(this);
    }

    private void checkTab() {
        String tabValue = this.sessionUtils.getTabValue();
        if (tabValue == "") {
            this.sessionUtils.setTabValue(AppUtils.ALL_L);
            tabValue = AppUtils.ALL_L;
        }
        if (tabValue.equalsIgnoreCase(AppUtils.ALL_L)) {
            apiHit(AppUtils.getAllUrl());
        } else {
            apiHit(AppUtils.getLevelUrl(tabValue));
        }
    }

    private void apiHit(String str) {
        if (AppUtils.isNetConnected(this)) {
            this.tv_refresh.setVisibility(View.GONE);
            this.apiHelper.requestGetStringApi(str, 1234, true);
            return;
        }
        List<PostBean> list = postsList;
        if (list == null || list.size() <= 0) {
            this.tv_refresh.setVisibility(View.VISIBLE);
        }
    }

    private void setAdapter(List<PostBean> list, List<String> list2) {
        if (this.tabViewAdapter == null || this.levelList.size() <= 0) {
            this.levelList = list2;
            TabViewAdapter tabViewAdapter2 = new TabViewAdapter(this, list2);
            this.tabViewAdapter = tabViewAdapter2;
            this.recycler_tab.setAdapter(tabViewAdapter2);
            this.tabViewAdapter.setOnItemClickListener(new TabViewAdapter.OnItemClickListener() {

                @Override
                public void onItemClick(View view, int i) {

                    AppManage.getInstance(HomeActivity.this).show_INTERSTIAL(HomeActivity.this, new AppManage.MyCallback() {
                        @Override
                        public void callbackCall() {


                            if (levelList != null && levelList.size() > i) {

                                if (levelList.get(i).equals(AppUtils.ALL_L)) {
                                    tv_titles.setText("All Match");
                                } else if (levelList.get(i).equals(AppUtils.CRICKET_L)) {
                                    tv_titles.setText(AppUtils.CRICKET_L);
                                } else if (levelList.get(i).equals(AppUtils.FOOTBALL_L)) {
                                    tv_titles.setText(AppUtils.FOOTBALL_L);
                                } else if (levelList.get(i).equals(AppUtils.HOCKEY_L)) {
                                    tv_titles.setText(AppUtils.HOCKEY_L);
                                } else if (levelList.get(i).equals(AppUtils.NBA_L)) {
                                    tv_titles.setText(AppUtils.NBA_L);
                                }


                                Log.e("===", "onItemClick: " + levelList.get(i));
                                sessionUtils.setTabValue(levelList.get(i));
                                tabViewAdapter.notifyDataSetChanged();
                                checkTab();
                            }
                        }
                    });

                }
            });
        } else {
            this.levelList.clear();
            this.levelList.addAll(list2);
            this.tabViewAdapter.notifyDataSetChanged();
        }
        postsList = list;
        Log.e("====", "setAdapter: " + postsList);
        ListViewAdapter listViewAdapter2 = new ListViewAdapter(this, postsList);
        this.listViewAdapter = listViewAdapter2;
        this.recycler_view.setAdapter(listViewAdapter2);
        this.listViewAdapter.setOnItemClickListener(new ListViewAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, final int i, final boolean z) {
                AppManage.getInstance(HomeActivity.this).show_INTERSTIAL(HomeActivity.this, new AppManage.MyCallback() {
                    @Override
                    public void callbackCall() {
                        if (AppUtils.isNetConnected(HomeActivity.this)) {
                            if (!z || !sessionUtils.isDev()) {
                                navigateToDetailPage(i);
                                clickedPosition = i;
                                return;
                            }
                            sendPushData(i);
                            HomeActivity.position = i;
                        }
                    }
                });
            }
        });
    }

    private void sendPushData(int i) {
        PostBean postBean = postsList.get(i);
        PushBean pushBean = new PushBean();
        pushBean.setLinkId(postBean.getLinkId());
        if (postBean.getImgUrl().size() > 0) {
            pushBean.setBanner(postBean.getImgUrl().remove(0));
        } else {
            pushBean.setBanner("");
        }
        pushBean.setLevel(postBean.getLevel());
        pushBean.setTitle(postBean.getTitle());
        pushBean.setDeadLine(postBean.getDeadLine());
        pushBean.setPostUrl(postBean.getUrl());
        pushBean.setPostTime(postBean.getPostTime());
        Intent intent = new Intent(this, MyNotificationActivity.class);
        intent.putExtra("bean", pushBean);
        startActivity(intent);
    }

    private void navigateToDetailPage(int i) {
        try {
            detailsView(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void detailsView(int i) {
        try {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("item", postsList.get(i));
            intent.putExtra("pos", i);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.tv_refresh) {
            checkTab();
        }
    }

    @Override
    public void onRefresh() {
        checkTab();
        this.swiperefresh.setRefreshing(false);
    }

    @Override
    public void onResponse(String str, int i) {
        if (str != null) {
            try {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.serializeNulls();
                BloggerData bloggerData = (BloggerData) gsonBuilder.create().fromJson(str, BloggerData.class);
                if (bloggerData.getFeed().getEntry() != null) {
                    if (bloggerData.getFeed().getEntry().size() > 0) {
                        List<String> levelList2 = getLevelList(bloggerData.getFeed().getCategory());
                        this.sessionUtils.setLevel(levelList2);
                        setAdapter(getList(bloggerData.getFeed()), levelList2);
                        return;
                    }
                }
                Toast.makeText(this, "No Data available", 1).show();
                postsList = new ArrayList();
                ArrayList arrayList = new ArrayList();
                arrayList.add(AppUtils.ALL_L);
                setAdapter(new ArrayList<PostBean>(), arrayList);
            } catch (Exception e) {
                Log.e("====", "onResponse: " + e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
    }

    private List<String> getLevelList(List<Category> list) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(AppUtils.ALL_L);
        for (Category category : list) {
            arrayList.add(category.getTerm());
        }
        return arrayList;
    }

    @Override
    public void onResponseError(VolleyError volleyError, int i) {
        List<PostBean> list = postsList;
        if (list == null || list.size() <= 0) {
            this.tv_refresh.setVisibility(View.VISIBLE);
        }
        Toast.makeText(this, "Server not responding", Toast.LENGTH_SHORT).show();
    }

    private List<PostBean> getList(Feed feed) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Entry entry : feed.getEntry()) {
            PostBean postData = getPostData(entry);
            if (Long.valueOf(postData.getDeadLine()).longValue() > System.currentTimeMillis()) {
                arrayList.add(postData);
            } else {
                arrayList2.add(postData);
            }
        }
        if (arrayList.size() > 1) {
            Collections.sort(arrayList);
        }
        Collections.sort(arrayList2);
        Collections.reverse(arrayList2);
        arrayList.addAll(arrayList2);
        return arrayList;
    }

    private PostBean getPostData(Entry entry) {
        try {
            PostBean postBean = new PostBean();
            postBean.setLinkId(entry.getId().get$t());
            postBean.setTitle(entry.getTitle().get$t());
            postBean.setUrl(AppUtils.httpsUrl(entry.getLink().get(3).getHref()));
            postBean.setPostTime(entry.getUpdated().get$t());
            postBean.setLevel(entry.getCategory().get(0).getTerm());
            postBean.setContent(entry.getContent().get$t());
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
    public void onBackPressed() {
        AppManage.getInstance(HomeActivity.this).show_INTERSTIAL(HomeActivity.this, new AppManage.MyCallback() {
            @Override
            public void callbackCall() {
                HomeActivity.super.onBackPressed();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        registerReceiver();
    }

    @Override
    public void onStop() {
        unregisterReceiver();
        super.onStop();
    }

    private void registerReceiver() {
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mMessageReceiver, new IntentFilter(AppUtils.PUSH_FILTER));
    }

    private void unregisterReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mMessageReceiver);
    }

    @Override
    public void onDestroy() {
        this.apiHelper = null;
        super.onDestroy();
    }

    public void Back(View view) {
        onBackPressed();
    }

}
