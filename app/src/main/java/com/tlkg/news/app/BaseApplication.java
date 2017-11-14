package com.tlkg.news.app;

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
        return sContext;
    }

    public static String getStringId(int resId) {
        return getAppContext().getString(resId);
    }


    public static int getColorId(int colorId) {
        return getAppContext().getResources().getColor(colorId);
    }
}
