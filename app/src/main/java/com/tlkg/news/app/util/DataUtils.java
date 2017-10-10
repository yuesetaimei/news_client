package com.tlkg.news.app.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by wuxiaoqi on 2017/9/23.
 * 时间工具转换类
 */

public final class DataUtils {

    public static final long ONE_DAY_TIME = 1000 * 60 * 60 * 24;

    /**
     * 获取年月日
     *
     * @param time
     * @param format
     * @return
     */
    public final static String getYMD(long time, String format) {
        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = new SimpleDateFormat(format);
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        return simpleDateFormat.format(time);
    }


    /**
     * 获取年月日
     *
     * @param time
     * @return
     */
    public final static String getYMD(long time) {
        return getYMD(time, "yyyyMMdd");
    }

    /**
     * 获取年月日小时分钟秒
     *
     * @param time
     * @return
     */
    public final static String getYMDHS(long time) {
        return getYMD(time, "yyyy-MM-dd-HH-mm-ss");
    }

    /**
     * 获取毫秒值
     *
     * @param time
     * @param format
     * @return
     */
    public final static long getLongTime(String time, String format) {
        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = new SimpleDateFormat(format);
        Calendar calendar = new GregorianCalendar();
        try {
            calendar.setTime(simpleDateFormat.parse(time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return calendar.getTimeInMillis();
    }

    /**
     * 获取毫秒值
     *
     * @param time
     * @return
     */
    public final static long getLongTime(String time) {
        return getLongTime(time, "yyyyMMdd");
    }
}
