package com.mynewacc.dreamteam11.cricket.fcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Pair;

import androidx.core.app.NotificationCompat;

import com.mynewacc.dreamteam11.cricket.Activity.HomeActivity;
import com.mynewacc.dreamteam11.cricket.Utils.AppUtils;
import com.mynewacc.dreamteam11.R;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;

public class NotificationHelper extends ContextWrapper {
    private static NotificationHelper nHelper;
    private NotificationManager manager;

    private NotificationHelper(Context context) {
        super(context);
    }

    public static NotificationHelper getHelper(Context context) {
        if (nHelper == null) {
            nHelper = new NotificationHelper(context);
        }
        return nHelper;
    }

    private void createChannel(Pair<String, String> pair, int i) {
        if (Build.VERSION.SDK_INT >= 26) {
            getManager().createNotificationChannel(new NotificationChannel((String) pair.first, (CharSequence) pair.second, i));
        }
    }

    public void cancelNotification(int i) {
        getManager().cancel(i);
    }

    public void cancelNotificationAll() {
        getManager().cancelAll();
    }

    public NotificationManager getManager() {
        if (this.manager == null) {
            this.manager = (NotificationManager) getSystemService("notification");
        }
        return this.manager;
    }

    public void sendNotificationB(JSONObject jSONObject, int i) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(536870912);
        PendingIntent activities = PendingIntent.getActivities(this, (int) System.currentTimeMillis(), new Intent[]{intent, AppUtils.getIntent(this, jSONObject.optString(AppUtils.PUSH_ID), jSONObject.optString(AppUtils.POST_URL), jSONObject.optString(AppUtils.PUSH_LEVEL))}, 134217728);
        Bitmap bitmapfromUrl = getBitmapfromUrl(jSONObject.optString(AppUtils.PUSH_BANNER));
        if (bitmapfromUrl == null) {
            bitmapfromUrl = BitmapFactory.decodeResource(getResources(), AppUtils.getTabImgId(jSONObject.optString(AppUtils.PUSH_LEVEL)));
        }
        Pair<String, String> channel = AppUtils.getChannel(i);
        createChannel(channel, i);
        Uri defaultUri = RingtoneManager.getDefaultUri(2);
        NotificationCompat.Builder color = new NotificationCompat.Builder(this, (String) channel.first).setSmallIcon(R.drawable.notification_icon).setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)).setContentTitle(jSONObject.optString(AppUtils.PUSH_TITLE)).setContentText(jSONObject.optString(AppUtils.PUSH_BODY)).setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmapfromUrl).bigLargeIcon(null)).setAutoCancel(true).setCategory(jSONObject.optString(AppUtils.PUSH_LEVEL)).setContentIntent(activities).setColor(getResources().getColor(R.color.colorPrimary));
        if (i == 4) {
            color.setPriority(1);
            color.setSound(defaultUri);
        } else if (i == 3) {
            color.setPriority(0);
            color.setSound(defaultUri);
        } else if (i == 2) {
            color.setPriority(0);
        } else {
            color.setPriority(1);
            color.setSound(defaultUri);
        }
        getManager().notify(AppUtils.getPostId(jSONObject.optString(AppUtils.PUSH_ID)), color.build());
    }

    public void sendNotification(JSONObject jSONObject, int i) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(536870912);
        PendingIntent activities = PendingIntent.getActivities(this, (int) System.currentTimeMillis(), new Intent[]{intent, AppUtils.getIntent(this, jSONObject.optString(AppUtils.PUSH_ID), jSONObject.optString(AppUtils.POST_URL), jSONObject.optString(AppUtils.PUSH_LEVEL))}, 134217728);
        Pair<String, String> channel = AppUtils.getChannel(i);
        createChannel(channel, i);
        Uri defaultUri = RingtoneManager.getDefaultUri(2);
        NotificationCompat.Builder color = new NotificationCompat.Builder(this, (String) channel.first).setSmallIcon(R.drawable.notification_icon).setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)).setContentTitle(jSONObject.optString(AppUtils.PUSH_TITLE)).setContentText(jSONObject.optString(AppUtils.PUSH_BODY)).setStyle(new NotificationCompat.BigTextStyle().bigText(jSONObject.optString(AppUtils.PUSH_BODY))).setAutoCancel(true).setCategory(jSONObject.optString(AppUtils.PUSH_LEVEL)).setContentIntent(activities).setColor(getResources().getColor(R.color.colorPrimary));
        if (i == 4) {
            color.setPriority(1);
            color.setSound(defaultUri);
        } else if (i == 3) {
            color.setPriority(0);
            color.setSound(defaultUri);
        } else if (i == 2) {
            color.setPriority(0);
        } else {
            color.setPriority(1);
            color.setSound(defaultUri);
        }
        getManager().notify(AppUtils.getPostId(jSONObject.optString(AppUtils.PUSH_ID)), color.build());
    }

    public Bitmap getBitmapfromUrl(String str) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();
            return BitmapFactory.decodeStream(httpURLConnection.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
