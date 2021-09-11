package com.mynewacc.dreamteam11.cricket.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.List;

public class SessionUtils {
    private static final String DEV_ID = "dev_id";
    private static final String IS_DEV = "is_dev";
    private static final String LEVEL_VALUE = "level_value";
    private static final String PUSH_PREFERENCES = "PushPreferences";
    private static final String TAB_VALUE = "tab_value";
    private static SessionUtils appInstance;
    private static SharedPreferences.Editor editor;
    private Context mContext;

    private SessionUtils(Context context) {
        this.mContext = context;
    }

    public static SessionUtils getInstance(Context context) {
        if (appInstance == null) {
            appInstance = new SessionUtils(context);
        }
        return appInstance;
    }

    public boolean isDev() {
        return this.mContext.getSharedPreferences(PUSH_PREFERENCES, 0).getBoolean(IS_DEV, false);
    }

    public String getDevId() {
        return this.mContext.getSharedPreferences(PUSH_PREFERENCES, 0).getString(DEV_ID, "");
    }

    public String getTabValue() {
        try {
            return this.mContext.getSharedPreferences(PUSH_PREFERENCES, 0).getString(TAB_VALUE, AppUtils.ALL_L);
        } catch (Exception e) {
            e.printStackTrace();
            return AppUtils.ALL_L;
        }
    }

    public void setTabValue(String str) {
        try {
            SharedPreferences.Editor edit = this.mContext.getSharedPreferences(PUSH_PREFERENCES, 0).edit();
            editor = edit;
            edit.putString(TAB_VALUE, str);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLevel(List<String> list) {
        try {
            HashSet hashSet = new HashSet(list);
            SharedPreferences.Editor edit = this.mContext.getSharedPreferences(PUSH_PREFERENCES, 0).edit();
            editor = edit;
            edit.putStringSet(LEVEL_VALUE, hashSet);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
