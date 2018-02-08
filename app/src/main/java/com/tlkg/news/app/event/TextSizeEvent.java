package com.tlkg.news.app.event;

import com.tlkg.news.app.base.BaseEvent;

/**
 * Created by wuxiaoqi on 2018/2/8.
 */

public class TextSizeEvent extends BaseEvent {
    public int textSize;

    public TextSizeEvent(int textSize) {
        this.textSize = textSize;
    }
}
