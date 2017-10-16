package com.tlkg.news.app.event;

import com.tlkg.news.app.base.BaseEvent;

/**
 * Created by wuxiaoqi on 2017/10/13.
 */

public class DetailPersonClienEvent extends BaseEvent {

    public String mLoadUrl;

    public String mTitle;

    public DetailPersonClienEvent(String loadUrl, String title) {
        this.mLoadUrl = loadUrl;
        this.mTitle = title;
    }
}
