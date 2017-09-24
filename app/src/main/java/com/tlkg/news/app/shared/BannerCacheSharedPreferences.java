package com.tlkg.news.app.shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.tlkg.news.app.bean.BannerBean;
import com.tlkg.news.app.util.DataUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuxiaoqi on 2017/9/23.
 */

public final class BannerCacheSharedPreferences {
    public static final String NAME = "banner_cache";

    public static final String SAVE_TIME = "save_time";

    public static final String DAY_CACHE = "day_cache";

    public static final String CACHE_COUNT = "cache_count";

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
     * 保存Bannder图片到SharedPreferences
     *
     * @param context
     * @param bean
     */
    public static final void saveBannerData(Context context, BannerBean bean) {
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        List<BannerBean.ResultBeanXXXXXXXXXXXXXXXXX.FocusBean.ResultBeanXXXXXXXXXXXXXXX> result
                = bean.getResult().getFocus().getResult();
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt(CACHE_COUNT, result.size());
        for (int i = 0; i < result.size(); i++) {
            edit.putString(CACHE_DATA + i, result.get(i).getRandpic());
        }
        edit.apply();
        saveDayYMD(context);
    }


    /**
     * 从SharedPreferences中获取保存的Banner数据
     *
     * @param context
     * @return
     */
    public static final BannerBean getBannerData(Context context) {
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        int anInt = sharedPreferences.getInt(CACHE_COUNT, 0);
        if (anInt <= 0) {
            return null;
        }
        BannerBean bannerBean = new BannerBean();
        List<BannerBean.ResultBeanXXXXXXXXXXXXXXXXX.FocusBean.ResultBeanXXXXXXXXXXXXXXX> result = new ArrayList<>();
        BannerBean.ResultBeanXXXXXXXXXXXXXXXXX.FocusBean.ResultBeanXXXXXXXXXXXXXXX data;
        for (int i = 0; i < anInt; i++) {
            data = new BannerBean.ResultBeanXXXXXXXXXXXXXXXXX.FocusBean.ResultBeanXXXXXXXXXXXXXXX();
            data.setRandpic(sharedPreferences.getString(CACHE_DATA + i, ""));
            result.add(data);
        }
        bannerBean.setResult(new BannerBean.ResultBeanXXXXXXXXXXXXXXXXX());
        bannerBean.getResult().setFocus(new BannerBean.ResultBeanXXXXXXXXXXXXXXXXX.FocusBean());
        bannerBean.getResult().getFocus().setResult(result);
        return bannerBean;
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
     * 清除Banner数据
     *
     * @param context
     */
    public static final void clearBannerCache(Context context) {
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .clear()
                .apply();
    }
}
