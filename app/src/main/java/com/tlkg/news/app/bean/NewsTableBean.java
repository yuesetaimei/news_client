package com.tlkg.news.app.bean;

import android.support.annotation.NonNull;

/**
 * Created by wuxiaoqi on 2017/11/14.
 */

public class NewsTableBean implements Comparable<NewsTableBean> {
    public String _id = "";
    public String name = "";
    public int isEnable = 1;
    public int position = 0;


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        NewsTableBean bean = (NewsTableBean) obj;
        if (isEnable != bean.isEnable) {
            return false;
        }
        if (position != bean.position) {
            return false;
        }
        if (!_id.equals(bean._id)) {
            return false;
        }
        if (!name.equals(bean.name)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = _id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + isEnable;
        result = 31 * result + position;
        return result;
    }


    @Override
    public int compareTo(@NonNull NewsTableBean o) {
        return this.position - o.position;
    }
}
