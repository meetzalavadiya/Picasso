package com.mynewacc.dreamteam11.cricket.Utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.gms.common.internal.ImagesContract;
import com.mynewacc.dreamteam11.cricket.Activity.HomeActivity;
import com.mynewacc.dreamteam11.cricket.Activity.PageViewActivity;
import com.mynewacc.dreamteam11.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AppUtils {
    public static final String ABOUT_URL = "https://www.blogger.com/feeds/3998811667077147654/pages/default/5308565878036593311";
    public static final String ABOUT_US = "About Us";
    public static final String ALL_L = "All";
    public static final String ANNOUNCEMENT_LEVEL = "Announcement";
    public static final String ANNOUNCEMENT_URL = "https://www.blogger.com/feeds/3998811667077147654/pages/default/1984405392143589423";
    public static final String APP_ABOUT_US = "About Us/posts/0000011100000033";
    public static final String APP_ANNOUNCEMENT = "Announcement/posts/0000011100000022";
    public static final String APP_DISCLAIMER = "Disclaimer/posts/0000011100000055";
    public static final String APP_PRIVACY = "Privacy policy/posts/0000011100000044";
    public static final String APP_PROMOTION = "Promotional/posts/0000011100000066";
    public static final String APP_RATE = "RateUs/posts/0000011100000010";
    public static final String APP_RATE_LEVEL = "RateUs";
    public static final String APP_UPDATE = "AppUpdate/posts/0000011100000011";
    public static final String APP_UPDATE_LEVEL = "AppUpdate";
    public static final String AppURL = "https://play.google.com/store/apps/details?id=";
    public static final String BANNER_VALUE = "bannerValue";
    public static final String BANNER_YES = "1";
    private static final String BASE_URL = "https://www.googleapis.com/blogger/v3/blogs/";
    private static final String BLOG_DOMAIN = "https://dreamgurupro.blogspot.com";
    public static final String CRICKET_L = "Cricket";
    public static final int CRICKET_VALUE = 0;
    public static final String CURRENT_TIME = "currentTime";
    public static final String DEAD_LINE = "deadLine";
    public static final String DISCLAIMER = "Disclaimer";
    public static final String DISCLAIMER_URL = "https://www.blogger.com/feeds/3998811667077147654/pages/default/5795333555867906522";
    private static final String FEED_SUB_DOMAIN = "/feeds/posts/default";
    public static final String FOOTBALL_L = "Football";
    public static final int FOOTBALL_VALUE = 1;
    public static final String HOCKEY_L = "Hockey";
    public static final int HOCKEY_VALUE = 3;
    public static final int IMPORTANCE_DEFAULT = 3;
    public static final String IMPORTANCE_DEFAULT_STATUS = "Default notification with Sound";
    public static final int IMPORTANCE_HIGH = 4;
    public static final String IMPORTANCE_HIGH_STATUS = "A preview of the notifications will appear at the top of the screen with the sound";
    public static final int IMPORTANCE_LOW = 2;
    public static final String IMPORTANCE_LOW_STATUS = "Default notification without Sound";
    public static final String IS_SEEN = "is_seen";
    public static final String KABADDI_L = "Kabaddi";
    public static final int KABADDI_VALUE = 2;
    public static final String KEY_ALL = "all";
    public static final String NBA_L = "NBA";
    public static final int NBA_VALUE = 4;
    public static final String NOTIFY_KEY = "notify_key";
    public static final String POST_ADD_KEY = "post_add";
    public static final String POST_AND_PUSH_ADD_KEY = "post_and_push";
    public static final String POST_CONTENT = "post_content";
    public static final String POST_IMG = "post_img";
    public static final String POST_RESET_KEY = "post_reset";
    public static final String POST_TIME = "post_time";
    public static final String POST_TITLE = "post_title";
    public static final String POST_URL = "post_url";
    public static final String PRIVACY_POLICY = "Privacy policy";
    public static final String PRIVACY_URL = "https://www.blogger.com/feeds/3998811667077147654/pages/default/6080570719012714382";
    public static final String PROMOTION_LEVEL = "Promotional";
    public static final String PUSH_ADD_KEY = "push_add";
    public static final String PUSH_BANNER = "banner";
    public static final String PUSH_BODY = "body";
    public static final String PUSH_FILTER = "fantasy_cricket_team_push";
    public static final String PUSH_ID = "linkId";
    public static final String PUSH_KEY = "key";
    public static final String PUSH_KEY_PASS = "pass";
    public static final String PUSH_LEVEL = "level";
    public static final String PUSH_RESET_KEY = "push_reset";
    public static final int PUSH_SILENT = 1;
    public static final String PUSH_SILENT_STATUS = "Turn off notifications for a while.";
    public static final String PUSH_TITLE = "title";
    public static final String PUSH_VALUE = "pushValue";

    public static final String getAllUrl() {
        return "https://dreamgurupro.blogspot.com/feeds/posts/default?alt=json&max-results=25";
    }

    public static String getLevel(int i) {
        if (i != 0) {
            return i != 1 ? i != 2 ? i != 3 ? i != 4 ? CRICKET_L : NBA_L : HOCKEY_L : KABADDI_L : FOOTBALL_L;
        }
        return null;
    }

    public static String getNotificationStatus(int i) {
        return i != 1 ? i != 2 ? (i == 3 || i != 4) ? IMPORTANCE_DEFAULT_STATUS : IMPORTANCE_HIGH_STATUS : IMPORTANCE_LOW_STATUS : PUSH_SILENT_STATUS;
    }

    public static final String getLevelUrl(String str) {
        return "https://dreamgurupro.blogspot.com/feeds/posts/default/-/" + str + "?alt=json&max-results=10";
    }

    public static boolean isNetConnected(Activity activity) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) activity.getSystemService("connectivity")).getActiveNetworkInfo();
        boolean z = activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
        if (!z) {
            Toast.makeText(activity, "Please check your Internet connection", Toast.LENGTH_SHORT).show();
        }
        return z;
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager;
        try {
            View currentFocus = activity.getCurrentFocus();
            if (currentFocus != null && (inputMethodManager = (InputMethodManager) activity.getSystemService("input_method")) != null && inputMethodManager.isActive()) {
                inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 2);
            }
        } catch (Exception e) {
            Log.e("Exception", "hide keyboard exception" + e.toString());
            e.printStackTrace();
        }
    }

    public static int getPostId(String str) {
        try {
            return Integer.parseInt(str.substring(str.length() - 5));
        } catch (Exception unused) {
            return (int) System.currentTimeMillis();
        }
    }

    public static String getTitleTimeInMillies(String str) {
        try {
            return String.valueOf(new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(str).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return str;
        }
    }

    public static String getTitleTimeLine(String str) {
        String[] split = str.split("\\|");
        if (split.length == 3) {
            return getTitleTimeInMillies(split[2].trim());
        }
        return null;
    }

    public static String getMilliesToStr2(long j) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(j);
        return new SimpleDateFormat("MMM dd | hh:mm a").format(instance.getTime());
    }

    public static String getDate(String str) {
        try {
            return new SimpleDateFormat("MMM dd, yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
            return str;
        }
    }

    public static String getMilliesForPostTime(String str) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Calendar instance = Calendar.getInstance();
            instance.setTime(simpleDateFormat.parse(str));
            return instance.getTimeInMillis() + "";
        } catch (ParseException e) {
            e.printStackTrace();
            return System.currentTimeMillis() + "";
        }
    }

    public static Pair<String, String> getChannel(int i) {
        if (i == 4) {
            return new Pair<>("IMPORTANCE_HIGH", "IMPORTANCE_HIGH");
        }
        if (i == 3) {
            return new Pair<>("IMPORTANCE_DEFAULT", "IMPORTANCE_DEFAULT");
        }
        if (i != 2) {
            return new Pair<>("IMPORTANCE_HIGH", "IMPORTANCE_HIGH");
        }
        return new Pair<>("IMPORTANCE_LOW", "IMPORTANCE_LOW");
    }

    public static int getTabImgId(String str) {
        char c;
        switch (str.hashCode()) {
            case -2128102205:
                if (str.equals(HOCKEY_L)) {
                    c = 3;
                    break;
                }
            case -1598014511:
                if (str.equals(CRICKET_L)) {
                    c = 0;
                    break;
                }
            case 77069:
                if (str.equals(NBA_L)) {
                    c = 4;
                    break;
                }
            case 459313037:
                if (str.equals(FOOTBALL_L)) {
                    c = 1;
                    break;
                }
            case 713821076:
                if (str.equals(KABADDI_L)) {
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

    public static String httpsUrl(String str) {
        return str.contains("http:") ? str.replace("http:", "https:") : str;
    }

    public static long getDelayTimeMilliseconds(int i) {
        return System.currentTimeMillis() + ((long) (i * 60 * 1000));
    }

    public static String getMilliesToStr(long j) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(j);
        return new SimpleDateFormat("MMM dd, yyyy | hh:mm a").format(instance.getTime());
    }

    public static long getStrToMillies(String str) {
        Calendar instance = Calendar.getInstance();
        try {
            instance.setTime(new SimpleDateFormat("MMM dd, yyyy | hh:mm a").parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return instance.getTimeInMillis();
    }

    public static Intent getIntent(Context context, String str, String str2, String str3) {
        if (str2.contains("/pages/")) {
            return iPageNav(context, str2, str3);
        }
        if (str.equalsIgnoreCase(APP_UPDATE) || str.equalsIgnoreCase(APP_RATE)) {
            return rateUsIntent(context);
        }
        if (str.equalsIgnoreCase(APP_PROMOTION)) {
            return externalLink(context, str2);
        }
        return iDetailNav(context, str2);
    }

    public static Intent externalLink(Context context, String str) {
        return new Intent("android.intent.action.VIEW", Uri.parse(str));
    }

    private static Intent iPageNav(Context context, String str, String str2) {
        if (str2.equalsIgnoreCase(APP_UPDATE_LEVEL)) {
            return rateUsIntent(context);
        }
        Intent intent = new Intent(context, PageViewActivity.class);
        intent.putExtra(ImagesContract.URL, str);
        intent.putExtra(PUSH_TITLE, str2);
        return intent;
    }

    private static Intent iDetailNav(Context context, String str) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.setFlags(268468224);
        return intent;
    }


    public static void shareIt(Activity activity, String str) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.SUBJECT", "Take a look at \"" + activity.getString(R.string.app_name) + "\"");
        intent.putExtra("android.intent.extra.TEXT", str);
        activity.startActivity(Intent.createChooser(intent, "Share via"));
    }

    public static Intent rateUsIntent(Context context) {
        try {
            return new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + context.getPackageName()));
        } catch (ActivityNotFoundException unused) {
            return new Intent("android.intent.action.VIEW", Uri.parse(AppURL + context.getPackageName()));
        }
    }

    public static int getSmartBannerHeightDp(Activity activity) {
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        float f = ((float) displayMetrics.heightPixels) / displayMetrics.density;
        return Math.round(((float) (f > 800.0f ? 100 : f > 400.0f ? 58 : 38)) * displayMetrics.density);
    }

    public static int getSmartBannerHeightDpFb(Activity activity) {
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        return Math.round(((float) (((float) displayMetrics.heightPixels) / displayMetrics.density > 800.0f ? 90 : 50)) * displayMetrics.density);
    }

}
