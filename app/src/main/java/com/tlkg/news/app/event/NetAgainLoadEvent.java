package com.tlkg.news.app.event;

import com.tlkg.news.app.base.BaseEvent;

/**
 * Created by wuxiaoqi on 2017/10/10.
 * 重新加载事件
 */

public class NetAgainLoadEvent extends BaseEvent {
    public int mPosition = -1;

    public NetAgainLoadEvent(int position) {
        this.mPosition = position;
    }
}
