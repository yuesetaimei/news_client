package com.tlkg.news.app.presenter;

/**
 * Created by wuxiaoqi on 2017/9/29.
 */

public interface IView {
    /**
     * 加载出错回调
     * @param msg
     */
    void onLoadErr(String msg);

    /**
     * 开始加载
     */
    void onLoadStart();
}
