package com.tlkg.news.app.event;

import com.tlkg.news.app.base.BaseEvent;
import com.tlkg.news.app.bean.HotMovieBean;

/**
 * Created by wuxiaoqi on 2017/10/12.
 * 电影条目点击事件
 */

public class MovieItemClickEvent extends BaseEvent {
    public HotMovieBean.SubjectsBean mBean;

    public MovieItemClickEvent(HotMovieBean.SubjectsBean bean) {
        this.mBean = bean;
    }
}
