package com.tlkg.news.app.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
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


    /**
     * String 转 Data
     */
    public static Date stringConvertDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        Date data = null;
        try {
            data = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 返回发布时间距离当前的时间
     */
    public static String timeAgo(Date createdTime) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA);
        if (createdTime != null) {
            long agoTimeInMin = (new Date(System.currentTimeMillis()).getTime() - createdTime.getTime()) / 1000 / 60;
            // 如果在当前时间以前一分钟内
            if (agoTimeInMin <= 1) {
                return "刚刚";
            } else if (agoTimeInMin <= 60) {
                // 如果传入的参数时间在当前时间以前10分钟之内
                return agoTimeInMin + "分钟前";
            } else if (agoTimeInMin <= 60 * 24) {
                return agoTimeInMin / 60 + "小时前";
            } else if (agoTimeInMin <= 60 * 24 * 2) {
                return agoTimeInMin / (60 * 24) + "天前";
            } else {
                return format.format(createdTime);
            }
        } else {
            return format.format(new Date(0));
        }
    }

    /**
     * 根据时间戳 返回发布时间距离当前的时间
     */
    public static String getTimeStampAgo(String timeStamp) {
        Long time = Long.valueOf(timeStamp);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        String string = sdf.format(time * 1000L);
        Date date = null;
        try {
            date = sdf.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeAgo(date);
    }

    public static String getCurrentTimeStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }
}
