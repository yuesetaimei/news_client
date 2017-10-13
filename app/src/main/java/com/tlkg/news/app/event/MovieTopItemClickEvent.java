package com.tlkg.news.app.event;

import com.tlkg.news.app.base.BaseEvent;
import com.tlkg.news.app.bean.HotMovieBean;

/**
 * Created by wuxiaoqi on 2017/10/12.
 * 电影Top条目点击事件
 */

public class MovieTopItemClickEvent extends BaseEvent {
    public HotMovieBean.SubjectsBean mBean;

    public MovieTopItemClickEvent(HotMovieBean.SubjectsBean bean) {
        this.mBean = bean;
    }
}
