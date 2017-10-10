package com.tlkg.news.app.event;

import com.tlkg.news.app.base.BaseEvent;

import java.util.ArrayList;

/**
 * Created by wuxiaoqi on 2017/10/10.
 * 点击福利item事件
 */

public class WelfareClickEvent extends BaseEvent {
    public int mPosition;
    public ArrayList<String> mList;

    public WelfareClickEvent(ArrayList<String> list, int position) {
        this.mList = list;
        this.mPosition = position;
    }
}
