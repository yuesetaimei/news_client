package com.tlkg.news.app.presenter;

import com.tlkg.news.app.base.BasePresenter;
import com.tlkg.news.app.bean.HotMovieBean;
import com.tlkg.news.app.http.HttpClient;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wuxiaoqi on 2017/10/11.
 */

public class DoubanTopPresenter extends BasePresenter<DoubanTopPresenter.IDoubanTopView> {

    private static final int LOAD_COUNT = 50;//一次加载50条数据

    public DoubanTopPresenter(IDoubanTopView view) {
        super(view);
    }

    @Override
    public void load() {
        isLoadMore = false;
        loadDoubanTop250(0, LOAD_COUNT);
    }

    @Override
    public void loadMore(int pageCount, int pageIndex) {
        isLoadMore = true;
        loadDoubanTop250(pageCount, LOAD_COUNT);
    }

    private void loadDoubanTop250(int start, int count) {
        if (isLoading) {
            return;
        }
        isLoading = true;
        if (!isLoadMore) mView.onLoadStart();
        HttpClient.Builder.getDoubanServer().getMovieTop250(start, count)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<HotMovieBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onLoadErr(e.getMessage());
                        isLoading = false;
                    }

                    @Override
                    public void onNext(HotMovieBean hotMovieBean) {
                        isLoading = false;
                        if (isLoadMore) {
                            mView.onLoadMoreComplete(hotMovieBean);
                        } else {
                            mView.onLoadComplete(hotMovieBean);
                        }
                    }
                });
    }

    public interface IDoubanTopView extends IView {
        /**
         * 加载完成
         *
         * @param data
         */
        void onLoadComplete(HotMovieBean data);

        /**
         * 加载更多完成
         *
         * @param data
         */
        void onLoadMoreComplete(HotMovieBean data);

    }
}
