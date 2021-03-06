package com.pesonal.adsdk;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.onesignal.NotificationExtenderService;
import com.onesignal.OSNotificationReceivedResult;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class NotificationService extends NotificationExtenderService {

    private NotificationManager mNotificationManager;
    private String NOTIFICATION_CHANNEL_ID = "status_app";
    private String message, bigpicture, title, url, id, type, status_type, title_name;


    @Override
    protected boolean onNotificationProcessing(OSNotificationReceivedResult receivedResult) {

        title = receivedResult.payload.title;
        message = receivedResult.payload.body;
        bigpicture = receivedResult.payload.bigPicture;

        try {
            url = receivedResult.payload.additionalData.getString("external_link");
            type = receivedResult.payload.additionalData.getString("type");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            switch (type) {
                case "single_status":
                    id = receivedResult.payload.additionalData.getString("id");
                    status_type = receivedResult.payload.additionalData.getString("status_type");
                    title_name = receivedResult.payload.additionalData.getString("title");
                    break;
                case "category":
                    id = receivedResult.payload.additionalData.getString("id");
                    title_name = receivedResult.payload.additionalData.getString("title");
                    break;
                case "account_status":
                    id = receivedResult.payload.additionalData.getString("id");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        sendNotification();
        return true;
    }

    @SuppressLint("WrongConstant")
    private void sendNotification() {
        Log.e("log_noty", "sendNotification: "+"Enter" );
        mNotificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);

        Intent intent;
        if (!url.equals("false") && !url.trim().isEmpty()) {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
        } else {
            intent = new Intent("android.intent.action.MAIN");

        }

        NotificationChannel mChannel;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getResources().getString(R.string.app_name);// The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;
            mChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance);
            mNotificationManager.createNotificationChannel(mChannel);
        }

        Random random_code = new Random();
        int code = random_code.nextInt(9999 - 1000) + 1000;

        PendingIntent contentIntent = PendingIntent.getActivity(this, code, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setSound(uri)
                .setAutoCancel(true)
                .setChannelId(NOTIFICATION_CHANNEL_ID)
                .setLights(Color.RED, 800, 800);

        mBuilder.setSmallIcon(getNotificationIcon(mBuilder));

        mBuilder.setContentTitle(title);
        mBuilder.setTicker(message);

        if (bigpicture != null) {
            mBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(getBitmapFromURL(bigpicture)).setSummaryText(message));
        } else {
            mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));
        }

        Random random = new Random();
        int m = random.nextInt(9999 - 1000) + 1000;

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(m, mBuilder.build());
    }

    private int getNotificationIcon(NotificationCompat.Builder notificationBuilder) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setColor(getColour());
            return R.drawable.notification;
        } else {
            return R.drawable.notification;
        }
    }

    private int getColour() {
        return Color.parseColor("#f23b57");
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            return null;
        }
    }
}