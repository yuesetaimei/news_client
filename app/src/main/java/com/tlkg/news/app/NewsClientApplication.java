package com.tlkg.news.app;

import com.tlkg.news.app.http.HttpUtils;
import com.tlkg.news.app.util.LanguageUtil;

/**
 * Created by wuxiaoqi on 2017/9/18.
 * 入口
 */

public class NewsClientApplication extends BaseApplication {

//    private final String TAG = "NewsClientApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        LanguageUtil.setChangeLanguage(this, getSharedPreferences(getPackageName() + "_preferences", MODE_PRIVATE));
        HttpUtils.getInstance().init(getAppContext(), BuildConfig.DEBUG);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Glide.get(getAppContext()).clearDiskCache();
//            }
//        }).start();
    }
}
