package com.nishi.developer.retrofitvolleyexample.Volley;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SingletonRequestQueue {

    private static SingletonRequestQueue mInstance;
    private Context mContext;
    private RequestQueue mRequestQueue;

/*
    getInstance() and getRequestQueue() methods create an instance of
    SingletonRequestQueue and RequestQueue respectively for
    the first time and re-use it everywhere.
*/


    private SingletonRequestQueue(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized SingletonRequestQueue getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SingletonRequestQueue(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext);
        }
        return mRequestQueue;
    }
}
