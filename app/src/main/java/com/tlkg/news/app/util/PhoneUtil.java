package com.tlkg.news.app.util;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.tlkg.news.app.constant.ConstantImageUrl;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by wuxiaoqi on 2017/10/10.
 */

public final class PhoneUtil {

    /**
     * 判断网络是否可用
     *
     * @param context
     * @return
     */
    public final static boolean isNetworkConnect(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 随机颜色
     */
    public static int randomColor() {
        Random random = new Random();
        int red = random.nextInt(150) + 50;//50-199
        int green = random.nextInt(150) + 50;//50-199
        int blue = random.nextInt(150) + 50;//50-199
        return Color.rgb(red, green, blue);
    }


    /**
     * 随机图片
     *
     * @return
     */
    public static String randomPic() {
        int i = new Random().nextInt(ConstantImageUrl.TRANSITION_URLS.length);
        String pic_url = ConstantImageUrl.TRANSITION_URLS[i];
        return pic_url;
    }

    /**
     * 判断WIFI网络是否可用
     */
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            // 获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            // 获取NetworkInfo对象
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            //判断NetworkInfo对象是否为空 并且类型是否为WIFI
            if (null != networkInfo && networkInfo.getType() == ConnectivityManager.TYPE_WIFI)
                return networkInfo.isAvailable();
        }
        return false;
    }


    /**
     * 判断MOBILE网络是否可用
     */
    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            //获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            //获取NetworkInfo对象
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            //判断NetworkInfo对象是否为空 并且类型是否为MOBILE
            if (null != networkInfo && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE)
                return networkInfo.isAvailable();
        }
        return false;
    }

    /**
     * 生成 as 和 cp , 用作 API 的请求参数
     * <p>
     * function () {
     * var t = Math.floor((new Date).getTime() / 1e3), i = t.toString(16).toUpperCase(), e = md5(t).toString().toUpperCase();
     * if (8 != i.length)return {as: "479BB4B7254C150", cp: "7E0AC8874BB0985"};
     * for (var s = e.slice(0, 5), o = e.slice(-5), n = "", a = 0; 5 > a; a++)n += s[a] + i[a];
     * for (var l = "", r = 0; 5 > r; r++)l += i[r + 3] + o[r];
     * return {as: "A1" + n + i.slice(-3), cp: i.slice(0, 3) + l + "E1"}
     * }
     * </p>
     */
    public static Map<String, String> getAsCp() {
        int t = (int) (System.currentTimeMillis() / 1000);
        String i = Integer.toHexString(t).toUpperCase();
        String e = getMD5(t + "").toUpperCase();

        String s = e.substring(0, 5);
        String o = e.substring(e.length() - 5, e.length());
        String n = "";
        for (int j = 0; 5 > j; j++) {
            n += s.substring(j, j + 1) + i.substring(j, j + 1);
        }

        String l = "";
        for (int r = 0; 5 > r; r++) {
            l += i.substring(r + 3, r + 3 + 1) + o.substring(r, r + 1);
        }

        String as = "A1" + n + i.substring(i.length() - 3, i.length());
        String cp = i.substring(0, 3) + l + "E1";

        Map<String, String> map = new HashMap<>();
        map.put("as", as);
        map.put("cp", cp);
        return map;
    }

    /**
     * 对字符串 MD5 加密
     */
    public static String getMD5(String str) {
        try {
            // 生成一个 MD5 加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算 MD5 函数
            md.update(str.getBytes());
            // digest() 最后确定返回 MD5 hash 值, 返回值为8为字符串
            // 因为 MD5 hash 值是16位的hex值, 实际上就是8位的字符
            // BigInteger 函数则将8位的字符串转换成 16 位 hex 值, 用字符串来表示得到字符串形式的 hash 值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
        }
        return "";
    }
}
