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

    /**
     * 切换无图模式
     */
    private static final String SWITCH_NOPHOTOMODE = "switch_noPhotoMode";

    /**
     * 字体大小
     */
    private static final String TEXT_SIZE = "textsize";

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
     * 获取是否开启无图模式
     */
    public boolean getIsNoPhotoMode() {
        return sharedPreferences.getBoolean(SWITCH_NOPHOTOMODE, false) && PhoneUtil.isMobileConnected(NewsClientApplication.getAppContext());
    }

    /**
     * 设置主题颜色
     *
     * @param color
     */
    public void setThemeColor(int color) {
        sharedPreferences.edit().putInt(THEME_COLOR, color).apply();
    }

    /**
     * 获取字体大小
     */
    public int getTextSize() {
        return sharedPreferences.getInt(TEXT_SIZE, 16);
    }
}