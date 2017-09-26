package com.tlkg.news.app.shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.tlkg.news.app.bean.BannerBean;
import com.tlkg.news.app.util.DataUtils;

import java.util.List;

/**
 * Created by wuxiaoqi on 2017/9/26.
 * 每日壁纸
 */

public final class BingPicCacheSharedPreferences {

    public static final String NAME = "bingpic_cache";

    public static final String DAY_CACHE = "day_cache";

    public static final String CACHE_COUNT = "cache_count";

    public static final String CACHE_DATA = "cache_data";

    public static final String BING_PIC_INDEX = "bing_pic_index";

    /**
     * 是否添加到缓存中
     *
     * @param context
     * @return
     */
    public static final boolean isDayCache(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(DAY_CACHE + DataUtils.getYMD(System.currentTimeMillis()), false);
    }


    /**
     * 保存bingpic数据到SharedPreferences
     *
     * @param context
     * @param
     */
    public static final void saveBingPicData(Context context, String bingpicBean, int size) {
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(CACHE_DATA, bingpicBean);
        edit.putInt(CACHE_COUNT, size);
        edit.apply();
        saveDayYMD(context);
        saveBingPicIndex(context, 0);
    }

    /**
     * 保存时间
     *
     * @param context
     */
    public static final void saveDayYMD(Context context) {
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .putBoolean(DAY_CACHE + DataUtils.getYMD(System.currentTimeMillis()), true)
                .apply();
    }

    /**
     * 保存当前显示的图片
     *
     * @param context
     * @param index
     */
    public static final void saveBingPicIndex(Context context, int index) {
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .putInt(BING_PIC_INDEX, index)
                .apply();
    }

    /**
     * 获取保存的图片数据
     *
     * @param context
     * @return
     */
    public static final String getBingPicData(Context context) {
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(CACHE_DATA, "");
    }

    /**
     * 获取上次显示的图片index
     *
     * @param context
     * @return
     */
    public static final int getBingPicIndex(Context context) {
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(BING_PIC_INDEX, 0);
    }

    /**
     * 清除数据
     *
     * @param context
     */
    public static final void clearBingPicCache(Context context) {
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .clear()
                .apply();
    }
}
