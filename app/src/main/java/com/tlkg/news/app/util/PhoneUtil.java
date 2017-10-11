package com.tlkg.news.app.util;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.tlkg.news.app.constant.ConstantImageUrl;

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
}
