package com.mynewacc.dreamteam11.cricket.NetworkUtils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mynewacc.dreamteam11.R;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class ApiHelper {
    AlertDialog dialog;
    ApiListener listener;
    Context mActivity;
    private RequestQueue mRequestQueue;

    public ApiHelper(Context context, ApiListener apiListener) {
        this.mActivity = context;
        this.listener = apiListener;
        this.mRequestQueue = Volley.newRequestQueue(context);
        initProgress(context);
    }

    public void requestGetStringApi(String str, final int i, boolean z) {
        try {
            StringRequest stringRequest = new StringRequest(0, str, new Response.Listener<String>() {

                public void onResponse(String str) {
                    ApiHelper.this.hideProgress();
                    Log.e("BEFORE_SAVE_RESPONSE", str.toString());
                    ApiHelper.this.listener.onResponse(str, i);
                }
            }, new Response.ErrorListener() {

                @Override 
                public void onErrorResponse(VolleyError volleyError) {
                    ApiHelper.this.hideProgress();
                    Log.e("onErrorResponse", "onErrorResponse: " + volleyError.toString());
                    ApiHelper.this.listener.onResponseError(volleyError, i);
                }
            });
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(15000, 1, 1.0f));
            stringRequest.setShouldCache(false);
            this.mRequestQueue.add(stringRequest);
            if (z) {
                showProgress();
            }
        } catch (Exception e) {
            Log.e("requestGetStringApi", "requestGetStringApi: " + e.toString());
            e.printStackTrace();
        }
    }

    public void requestJsonPostData(String str, final int i, JSONObject jSONObject, final String str2, boolean z) {
        try {
            final String jSONObject2 = jSONObject.toString();
            StringRequest r11 = new StringRequest(1, str, new Response.Listener<String>() {

                public void onResponse(String str) {
                    ApiHelper.this.hideProgress();
                    ApiHelper.this.listener.onResponse(str, i);
                    Log.e("BEFORE_SAVE_RESPONSE", str.toString());
                }
            }, new Response.ErrorListener() {

                @Override 
                public void onErrorResponse(VolleyError volleyError) {
                    ApiHelper.this.hideProgress();
                    Toast.makeText(ApiHelper.this.mActivity, "Server not responding", 0).show();
                }
            }) {

                @Override 
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override 
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap hashMap = new HashMap();
                    hashMap.put("Authorization", "key=" + str2);
                    hashMap.put("Content-Type", "application/json");
                    return hashMap;
                }

                @Override 
                public byte[] getBody() throws AuthFailureError {
                    try {
                        String str = jSONObject2;
                        if (str != null) {
                            return str.getBytes("utf-8");
                        }
                        return null;
                    } catch (UnsupportedEncodingException unused) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", jSONObject2, "utf-8");
                        return null;
                    }
                }

                @Override 
                public Response<String> parseNetworkResponse(NetworkResponse networkResponse) {
                    return Response.success(networkResponse != null ? String.valueOf(networkResponse.statusCode) : "", HttpHeaderParser.parseCacheHeaders(networkResponse));
                }
            };
            r11.setRetryPolicy(new DefaultRetryPolicy(15000, 1, 1.0f));
            r11.setShouldCache(false);
            MySingleton.getInstance(this.mActivity).addToRequestQueue(r11);
            if (z) {
                showProgress();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initProgress(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.dialog_progress, (ViewGroup) null, false));
        AlertDialog create = builder.create();
        this.dialog = create;
        create.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.dialog.setCanceledOnTouchOutside(false);
    }

    public void showProgress() {
        try {
            AlertDialog alertDialog = this.dialog;
            if (alertDialog != null && !alertDialog.isShowing()) {
                this.dialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideProgress() {
        try {
            AlertDialog alertDialog = this.dialog;
            if (alertDialog != null && alertDialog.isShowing()) {
                this.dialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
