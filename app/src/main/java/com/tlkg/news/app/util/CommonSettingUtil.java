package com.tlkg.news.app.util;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;

import com.tlkg.news.app.NewsClientApplication;
import com.tlkg.news.app.R;

/**
 * Created by wuxiaoqi on 2017/11/13.
 * 通用设置
 */

public class CommonSettingUtil {

    private static final String TAG = "CommonSettingUtil";

    /**
     * 主题颜色key
     */
    private static final String THEME_COLOR = "theme_color";

    private CommonSettingUtil() {
    }

    private static final class CommonSettingUtilInstance {
        private static final CommonSettingUtil instance = new CommonSettingUtil();
    }

    public static CommonSettingUtil getInstance() {
        return CommonSettingUtilInstance.instance;
    }

    private SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(NewsClientApplication.getAppContext());


    /**
     * 获取主题颜色
     *
     * @return
     */
    public int getThemeColor() {
        int defaultColor = NewsClientApplication.getColorId(R.color.colorPrimary);
        int themeColor = sharedPreferences.getInt(THEME_COLOR, defaultColor);
        if ((themeColor != 0) && (Color.alpha(themeColor) != 255)) {
            themeColor = defaultColor;
        }
        return themeColor;
    }

    /**
     * 设置主题颜色
     *
     * @param color
     */
    public void setThemeColor(int color) {
        sharedPreferences.edit().putInt(THEME_COLOR, color).apply();
    }


}
