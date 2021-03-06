package com.tlkg.news.app.base;

/**
 * Created by wuxiaoqi on 2017/9/29.
 */

public abstract class BasePresenter<T> implements IBasePresenter {

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
    public static final int PAGE_COUNT = 20;

    protected T mView;

    public BasePresenter(T view) {
        if (view == null)
            throw new IllegalArgumentException("IView cannot is null");
        this.mView = view;
    }

    /**
     * 分页加载要重写这个方法
     *
     * @param pageCount
     * @param pageIndex
     */
    public void loadMore(int pageCount, int pageIndex) {
    }
}
