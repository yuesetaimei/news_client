package com.tlkg.news.app.shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.tlkg.news.app.util.DataUtils;

/**
 * Created by wuxiaoqi on 2017/10/11.
 */

public final class HotMovieCacheSharedPreferences {

    public static final String NAME = "hotmovie_cache";

    public static final String DAY_CACHE = "day_cache";

    public static final String CACHE_DATA = "cache_data";

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
     * hot movie数据到SharedPreferences
     *
     * @param context
     * @param
     */
    public static final void saveHotMovieData(Context context, String hotMovieData) {
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(CACHE_DATA, hotMovieData);
        edit.apply();
        saveDayYMD(context);
    }


    /**
     * 获取保存的数据
     *
     * @param context
     * @return
     */
    public static final String getHotMovieData(Context context) {
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(CACHE_DATA, "");
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
     * 清除数据
     *
     * @param context
     */
    public static final void clearHotMovieCache(Context context) {
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .clear()
                .apply();
    }
}
