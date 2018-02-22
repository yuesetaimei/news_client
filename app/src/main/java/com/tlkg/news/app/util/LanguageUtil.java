package com.tlkg.news.app.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * Created by wuxiaoqi on 2018/2/22.
 * 更改显示语言工具类
 */

public class LanguageUtil {

    /**
     * 更改应用语言
     *
     * @param context
     * @param locale  语言地区
     */
    public static void changeAppLanguage(Context context, Locale locale) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
        resources.updateConfiguration(configuration, metrics);
    }

    public static void setChangeLanguage(Context context, SharedPreferences sharedPreferences) {
        String language = sharedPreferences.getString("language", "0");
        switch (Integer.parseInt(language)) {
            case 0:
                LanguageUtil.changeAppLanguage(context, Locale.SIMPLIFIED_CHINESE);
                break;
            case 1:
                LanguageUtil.changeAppLanguage(context, Locale.TRADITIONAL_CHINESE);
                break;
            case 2:
                LanguageUtil.changeAppLanguage(context, Locale.US);
                break;
            case 3:
                LanguageUtil.changeAppLanguage(context, Locale.JAPAN);
                break;
            default:
                break;
        }
    }
}
