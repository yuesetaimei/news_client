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

//    private static final String TAG = "CommonSettingUtil";

    /**
     * 主题颜色key
     */
    private static final String THEME_COLOR = "theme_color";

    /**
     * 切换无图模式
     */
    private static final String SWITCH_NOPHOTOMODE = "switch_noWifiLoadImg";

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
     * 获取是否开启视频强制横屏
     */
    public boolean getIsVideoForceLandscape() {
        return sharedPreferences.getBoolean("video_force_landscape", true);
    }

    /**
     * 获取滑动返回值
     */
    public int getSlidable() {
        String s = sharedPreferences.getString("slidable", "1");
        return Integer.parseInt(s);
    }

    /**
     * 获取主题颜色
     *
     * @return color
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
     * 获取是否上滑列表导航按钮隐藏
     */
    public boolean getIsSlideHideNavBtn() {
        return sharedPreferences.getBoolean("nav_btn", true);
    }

    /**
     * 获取是否开启夜间模式
     */
    public boolean getIsNightMode() {
        return sharedPreferences.getBoolean("switch_nightMode", false);
    }

    /**
     * 保存设置夜间模式
     *
     * @param nightMode 值
     */
    public void setNightMode(boolean nightMode) {
        sharedPreferences.edit().putBoolean("switch_nightMode", nightMode).apply();
    }

    /**
     * 获取是否开启导航栏上色
     */
    public boolean getNavBar() {
        return sharedPreferences.getBoolean("nav_bar", false);
    }


    /**
     * 设置主题颜色
     *
     * @param color
     */
    public void setThemeColor(int color) {
        sharedPreferences.edit().putInt(THEME_COLOR, color).apply();
    }

    public boolean getWifiAutoPlayVidoe() {
        return sharedPreferences.getBoolean("video_auto_play", true) && PhoneUtil.isWifiConnected(NewsClientApplication.getAppContext());
    }

    /**
     * 获取字体大小
     */
    public int getTextSize() {
        return sharedPreferences.getInt(TEXT_SIZE, 16);
    }

    public void setTextSize(int size) {
        sharedPreferences.edit().putInt(TEXT_SIZE, size).apply();
    }

    /**
     * 获取是否开启自动切换夜间模式
     */
    public boolean getIsAutoNightMode() {
        return sharedPreferences.getBoolean("auto_swith_night_mode", false);
    }

    public String getNightStartHour() {
        return sharedPreferences.getString("night_startHour", "22");
    }

    public void setNightStartHour(String nightStartHour) {
        sharedPreferences.edit().putString("night_startHour", nightStartHour).apply();
    }

    public String getNightStartMinute() {
        return sharedPreferences.getString("night_startMinute", "00");
    }

    public void setNightStartMinute(String nightStartMinute) {
        sharedPreferences.edit().putString("night_startMinute", nightStartMinute).apply();
    }

    public String getDayStartHour() {
        return sharedPreferences.getString("day_startHour", "06");
    }

    public void setDayStartHour(String dayStartHour) {
        sharedPreferences.edit().putString("day_startHour", dayStartHour).apply();
    }

    public String getDayStartMinute() {
        return sharedPreferences.getString("day_startMinute", "00");
    }

    public void setDayStartMinute(String dayStartMinute) {
        sharedPreferences.edit().putString("day_startMinute", dayStartMinute).apply();
    }

}
