package com.tlkg.news.app.event;

import com.tlkg.news.app.base.BaseEvent;

/**
 * Created by wuxiaoqi on 2017/12/25.
 * 我的页面头像平移坐标整件
 */

public class AnimTranEvent extends BaseEvent {
    public int x;
    public int y;

    public AnimTranEvent(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
