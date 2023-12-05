package com.hardik.utils;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class MyApplication extends Application {
    private static final String TAG = "MyApp";
    private static MyApplication instance;
    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "MyApp onCreate");

        instance = this;
        appContext = getApplicationContext();
        // Perform any initialization tasks or setup here
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public static Context getAppContext() {
        return appContext;
    }
    // You can add other methods or variables as needed
}
