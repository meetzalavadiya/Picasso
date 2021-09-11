package com.mynewacc.dreamteam11.cricket.NetworkUtils;

import com.android.volley.VolleyError;

public interface ApiListener {
    void onResponse(String str, int i);

    void onResponseError(VolleyError volleyError, int i);
}
