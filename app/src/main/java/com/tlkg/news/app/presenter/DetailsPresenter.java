package com.tlkg.news.app.presenter;

import com.tlkg.news.app.base.BasePresenter;
import com.tlkg.news.app.bean.MovieDetailBean;
import com.tlkg.news.app.http.HttpClient;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wuxiaoqi on 2017/10/13.
 */

public class DetailsPresenter extends BasePresenter<DetailsPresenter.IDetailsView> {


    public DetailsPresenter(IDetailsView view) {
        super(view);
    }

    @Override
    public void load() {
        loadDetailsInfo();
    }

    private void loadDetailsInfo() {
        if (isLoading) {
            return;
        }
        isLoading = true;
        mView.onLoadStart();
        HttpClient.Builder.getDoubanServer().getMovieDetail(mView.getMovieId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<MovieDetailBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onLoadErr(e.getMessage());
                        isLoading = false;
                    }

                    @Override
                    public void onNext(MovieDetailBean movieDetailBean) {
                        mView.onLoadComplete(movieDetailBean);
                        isLoading = false;
                    }
                });
    }

    public interface IDetailsView extends IView {
        /**
         * 加载完成
         *
         * @param data
         */
        void onLoadComplete(MovieDetailBean data);

        /**
         * 获取要查询的电影id
         *
         * @return
         */
        String getMovieId();
    }
}
