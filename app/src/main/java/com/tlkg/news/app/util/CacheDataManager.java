package com.tlkg.news.app.util;

import android.content.Context;
import android.os.Environment;

import com.tlkg.news.app.NewsClientApplication;
import com.tlkg.news.app.event.ClearCacheEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.math.BigDecimal;

/**
 * Created by wuxiaoqi on 2018/2/23.
 * 缓存管理
 */

public class CacheDataManager {

    /**
     * 获取缓存大小
     *
     * @return 返回字符串
     */
    public static String getTotalCacheSize() {
        Context appContext = NewsClientApplication.getAppContext();
        long cacheSize = getFolderSize(appContext.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(appContext.getExternalCacheDir());
        }
        return getFormatSize(cacheSize);
    }

    /**
     * 清除所有缓存
     */
    public static void clearAllCache() {
        Context appContext = NewsClientApplication.getAppContext();
        deleteDir(appContext.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(appContext.getExternalCacheDir());
        }
        EventBus.getDefault().post(new ClearCacheEvent());
    }

    /**
     * 递归删除文件夹下文件
     *
     * @param dir 要删除的文件夹
     */
    private static void deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] list = dir.list();
            for (String s : list) {
                deleteDir(new File(dir, s));
            }
        }
        if (dir != null) {
            dir.delete();
        }
    }

    /**
     * 递归遍历文件夹获取文件夹下文件总大小
     *
     * @param file 文件
     * @return 返回总大小
     */
    private static long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File f : fileList) {
                if (f.isDirectory()) {
                    size += getFolderSize(f);
                } else {
                    size += f.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }


    /**
     * 格式化缓存字符串
     *
     * @param size 缓存大小
     * @return 返回字符串形式
     */
    private static String getFormatSize(double size) {
        double kByte = size / 1024;
        if (kByte < 1) {
            return size + "Byte";
        }

        double mByte = kByte / 1024;
        if (mByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gByte = mByte / 1024;
        if (gByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(mByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double tByte = gByte / 1024;
        if (tByte < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP) + "GB";
        }

        BigDecimal result4 = new BigDecimal(Double.toString(tByte));
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP) + "TB";
    }
}
