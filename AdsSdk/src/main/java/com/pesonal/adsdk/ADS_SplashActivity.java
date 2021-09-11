package com.pesonal.adsdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.BuildConfig;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import static com.pesonal.adsdk.AppManage.mysharedpreferences;

public class ADS_SplashActivity extends AppCompatActivity {

    String ads_url = "https://autho-playstoreapps.com/api/get_app.php";

    private Runnable runnable;
    private Handler refreshHandler;
    boolean is_retry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void ADSinit(final Activity activity, final int vcode, final ViewGroup retry_view, final TextView retry_text, final getDataListner myCallback1) {
        if (!isNetworkAvailable()) {
            is_retry = false;
            retry_view.setVisibility(View.VISIBLE);
        }

        retry_view.setVisibility(View.GONE);
        refreshHandler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (isNetworkAvailable()) {
                    is_retry = true;
                    retry_text.setText("Retry");
                } else {
                    retry_view.setVisibility(View.VISIBLE);
                    is_retry = false;
                    retry_text.setText("Please Connect To Internet");
                }
                refreshHandler.postDelayed(this, 1000);
            }
        };
        refreshHandler.postDelayed(runnable, 1000);

        retry_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (is_retry) {
                    myCallback1.reloadActivity();
                } else {
                    startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                }
            }
        });


        mysharedpreferences = activity.getSharedPreferences(activity.getPackageName(), Context.MODE_PRIVATE);


        Calendar calender = Calendar.getInstance();
        calender.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        String currentDate = df.format(calender.getTime());


        int appDownload;
        String status = mysharedpreferences.getString("firsttime", "true");
        SharedPreferences.Editor editor = mysharedpreferences.edit();
        if (status.equals("true")) {
            editor.putString("date", currentDate).apply();
            editor.putString("firsttime", "false").apply();
            appDownload = 1;


        } else {
            String date = mysharedpreferences.getString("date", "");
            if (!currentDate.equals(date)) {
                editor.putString("date", currentDate).apply();
                appDownload = 2;
            } else {
                appDownload = 3;
            }

        }


        ads_url = ads_url + "?packageName=" + activity.getPackageName();
        ads_url = ads_url + "&apikey=" + getKeyHash(activity);


        ads_url = ads_url + "&appDownload=" + appDownload;

        if (BuildConfig.DEBUG) {
            ads_url = ads_url + "&debug=true";
        } else {
            ads_url = ads_url + "&debug=false";
        }


        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, ads_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String aa) {

                try {
                    JSONObject response =new JSONObject(aa);
                    boolean status = response.getBoolean("STATUS");
                    if (status) {
                        SharedPreferences.Editor editor1 = mysharedpreferences.edit();
                        editor1.putString("response", response.toString());
                        editor1.apply();

                        AppManage.getInstance(activity).getResponseFromPref(new getDataListner() {
                            @Override
                            public void onsuccess() {
                                myCallback1.onsuccess();
                            }

                            @Override
                            public void onUpdate(String url) {
                                myCallback1.onUpdate(url);
                            }

                            @Override
                            public void onRedirect(String url) {
                                myCallback1.onRedirect(url);
                            }

                            @Override
                            public void reloadActivity() {

                            }

                            @Override
                            public void ongetExtradata(JSONObject extraData) {
                                myCallback1.ongetExtradata(extraData);
                            }
                        }, vcode);
                    }


                } catch (Exception e) {
                    Log.e("my_log", "onResponse: "+e.getMessage() );
                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private String getKeyHash(Activity activity) {
        PackageInfo info;
        try {
            info = activity.getPackageManager().getPackageInfo(activity.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = (Base64.encodeToString(md.digest(), Base64.NO_WRAP));
                return something.replace("+", "*");
            }
        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();

        } catch (NoSuchAlgorithmException e) {

        } catch (Exception e) {

        }
        return null;
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager manager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            // Network is present and connected
            isAvailable = true;
        }
        return isAvailable;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        refreshHandler.removeCallbacks(runnable);
    }

}