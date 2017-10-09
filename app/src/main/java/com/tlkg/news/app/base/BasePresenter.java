package com.tlkg.news.app.base;

/**
 * Created by wuxiaoqi on 2017/9/29.
 */

public abstract class BasePresenter implements IBasePresenter {

    /**
     * 是否加载中
     */
    protected boolean isLoading = false;

    /**
     * 是否加载更多
     */
    protected boolean isLoadMore = false;

    /**
     * 默认加载数量
     */
    protected static final int PAGE_COUNT = 20;

    /**
     * 分页加载要重写这个方法
     * @param pageCount
     * @param pageIndex
     */
    protected void loadMore(int pageCount, int pageIndex) {
    }
}
