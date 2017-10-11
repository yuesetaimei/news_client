package com.tlkg.news.app.event;

import com.tlkg.news.app.base.BaseEvent;

/**
 * Created by wuxiaoqi on 2017/10/10.
 */

public class NetworkErrEvent extends BaseEvent {
    /**
     * 当前界面下标
     */
    public int mPosition = -1;

    public NetworkErrEvent(int position) {
        this.mPosition = position;
    }

}
