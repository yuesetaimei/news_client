package com.wuxiaoqi.news.app;

import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * Created by wuxiaoqi on 2017/9/18.
 */

public class BaseApplication extends Application {

    private final String TAG = "BaseApplication";

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Log.i(TAG, "onCreate");
        }
        sContext = getApplicationContext();
    }


    public static Context getAppContext() {
        if (sContext == null) {
            sContext = getAppContext();
        }
        return sContext;
    }
}
