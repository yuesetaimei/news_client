package com.tlkg.news.app;

import android.support.v7.app.AppCompatDelegate;

import com.tlkg.news.app.http.HttpUtils;
import com.tlkg.news.app.util.CommonSettingUtil;
import com.tlkg.news.app.util.LanguageUtil;

import java.util.Calendar;

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
        setNightMode();
    }

    /**
     * 设置夜间模式
     */
    private void setNightMode() {
        if (CommonSettingUtil.getInstance().getIsAutoNightMode()) {
            int nightStartHour = Integer.parseInt(CommonSettingUtil.getInstance().getNightStartHour());
            int nightStartMinute = Integer.parseInt(CommonSettingUtil.getInstance().getNightStartMinute());
            int dayStartHour = Integer.parseInt(CommonSettingUtil.getInstance().getDayStartHour());
            int dayStartMinute = Integer.parseInt(CommonSettingUtil.getInstance().getDayStartMinute());

            Calendar calendar = Calendar.getInstance();
            int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
            int currentMinute = calendar.get(Calendar.MINUTE);

            int nightValue = nightStartHour * 60 + nightStartMinute;
            int dayValue = dayStartHour * 60 + dayStartMinute;
            int currentValue = currentHour * 60 + currentMinute;

            if (currentValue >= nightValue || currentValue < dayValue) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                CommonSettingUtil.getInstance().setNightMode(true);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                CommonSettingUtil.getInstance().setNightMode(false);
            }
        } else {
            if (CommonSettingUtil.getInstance().getIsNightMode()) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }
    }
}
