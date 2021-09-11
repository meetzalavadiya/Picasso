package com.mynewacc.dreamteam11.ads;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.mynewacc.dreamteam11.R;


public class Globs {

    //SHARE APP
    public static void shareApps(Context context, String bitmapPath) {
        Uri bitmapUri = Uri.parse(bitmapPath);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/png");
        intent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
        String package_name = context.getPackageName();
        intent.putExtra(Intent.EXTRA_TEXT, "\n*" + "Pikashow : Live Fantasy Cricket"+ "*\n\n" +
                "\uD83D\uDD25 Free Live Cricket, Football, Hokey and NBA.\n" +
                "\uD83D\uDD25 Watch All Live TV in HD Quality.\n" +
                "\uD83D\uDD25 Watch IPL and WorldCup.\n" +
                "\uD83D\uDD25 All Content are FREE NO NEEDED ANY MEMBERSHIP.\n" +
                "\n\uD83D\uDC47    \uD83D\uDC47    \uD83D\uDC47   \uD83D\uDC47\n" +
                "https://play.google.com/store/apps/details?id=" + package_name + "");
        try {
            context.startActivity(Intent.createChooser(intent, "Share via"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, " Sorry, Not able to Share!", Toast
                    .LENGTH_SHORT).show();
        }
    }

    public static void btn_zooming(View view,Context context){
        try {


            Animation zoomin = AnimationUtils.loadAnimation(context, R.anim.zoom_in_btn);
            Animation zoomout = AnimationUtils.loadAnimation(context, R.anim.zoom_out_btn);

            zoomin.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    view.startAnimation(zoomout);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            zoomout.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    view.startAnimation(zoomin);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            view.startAnimation(zoomin);
        } catch (Exception e) {

        }
    }


    public static void touch(final View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
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
        });

    }

    public static void rateus(Context context) {
        try {
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + context.getPackageName())));
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }
    }



}
