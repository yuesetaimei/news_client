package com.tlkg.news.app;

import com.tlkg.news.app.http.HttpUtils;

/**
 * Created by wuxiaoqi on 2017/9/18.
 */

public class NewsClientApplication extends BaseApplication {

    private final String TAG = "NewsClientApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        HttpUtils.getInstance().init(getAppContext(), BuildConfig.DEBUG);
    }
}
