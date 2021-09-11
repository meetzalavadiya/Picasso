package com.mynewacc.dreamteam11.cricket.NetworkUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MySingleton {
    private static Context mCtx;
    private static MySingleton mInstance;
    private Gson gson;
    private ImageLoader mImageLoader = new ImageLoader(this.mRequestQueue, new ImageLoader.ImageCache() {
        /* class com.yd.expert.predictionforwinningteam.fentacyteamexpert.AppData.NetworkUtils.MySingleton.AnonymousClass1 */
        private final LruCache<String, Bitmap> cache = new LruCache<>(20);

        @Override // com.android.volley.toolbox.ImageLoader.ImageCache
        public Bitmap getBitmap(String str) {
            return this.cache.get(str);
        }

        @Override // com.android.volley.toolbox.ImageLoader.ImageCache
        public void putBitmap(String str, Bitmap bitmap) {
            this.cache.put(str, bitmap);
        }
    });
    private RequestQueue mRequestQueue = getRequestQueue();

    private MySingleton(Context context) {
        mCtx = context;
    }

    public static synchronized MySingleton getInstance(Context context) {
        MySingleton mySingleton;
        synchronized (MySingleton.class) {
            synchronized (MySingleton.class) {
                if (mInstance == null) {
                    mInstance = new MySingleton(context);
                }
                mySingleton = mInstance;
            }
            return mySingleton;
        }
    }

    public RequestQueue getRequestQueue() {
        if (this.mRequestQueue == null) {
            this.mRequestQueue = Volley.newRequestQueue(mCtx);
        }
        return this.mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }

    public Gson getGson() {
        if (this.gson == null) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.serializeNulls();
            this.gson = gsonBuilder.create();
        }
        return this.gson;
    }

    public ImageLoader getImageLoader() {
        return this.mImageLoader;
    }
}
