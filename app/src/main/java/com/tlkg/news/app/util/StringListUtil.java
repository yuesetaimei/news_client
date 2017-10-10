package com.tlkg.news.app.util;

import android.text.TextUtils;

import com.tlkg.news.app.bean.BeautyBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuxiaoqi on 2017/10/10.
 */

public final class StringListUtil {

    /**
     * 将List<BeautyBean.ResultsBean> data 转化为ArrayList<String></String>
     *
     * @param data
     * @return
     */
    public static ArrayList<String> ResultsBeanToString(List<BeautyBean.ResultsBean> data) {
        if (data instanceof ArrayList == false) return null;
        ArrayList<String> list = new ArrayList<>();
        for (BeautyBean.ResultsBean bean : data) {
            if (TextUtils.isEmpty(bean.getUrl())) break;
            list.add(bean.getUrl());
        }
        return list;
    }
}
