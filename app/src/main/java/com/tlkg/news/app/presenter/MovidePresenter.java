package com.tlkg.news.app.presenter;

import com.google.gson.Gson;
import com.tlkg.news.app.NewsClientApplication;
import com.tlkg.news.app.R;
import com.tlkg.news.app.base.BasePresenter;
import com.tlkg.news.app.bean.HotMovieBean;
import com.tlkg.news.app.http.HttpClient;
import com.tlkg.news.app.shared.HotMovieCacheSharedPreferences;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wuxiaoqi on 2017/10/11.
 */

public class MovidePresenter extends BasePresenter<MovidePresenter.IMovieView> {

    public MovidePresenter(IMovieView view) {
        super(view);
    }

    @Override
    public void load() {
        mView.onLoadStart();
        getHotMovieBean();
    }

    /**
     * 获取热映电影数据
     */
    private void getHotMovieBean() {
        if (HotMovieCacheSharedPreferences.isDayCache(NewsClientApplication.getAppContext())) {
            //从缓存中加载
            String hotMovieDataString = HotMovieCacheSharedPreferences.getHotMovieData(NewsClientApplication.getAppContext());
            HotMovieBean hotMovieBean = new Gson().fromJson(hotMovieDataString, HotMovieBean.class);
            mView.onLoadComplete(hotMovieBean);
            return;
        }
        HotMovieCacheSharedPreferences.clearHotMovieCache(NewsClientApplication.getAppContext());
        HttpClient.Builder.getDoubanServer().getHotMovie()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<HotMovieBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onLoadErr(e.getMessage());
                    }

                    @Override
                    public void onNext(HotMovieBean hotMovieBean) {
                        if (hotMovieBean != null && hotMovieBean.getSubjects() != null) {
                            String jsonData = new Gson().toJson(hotMovieBean);
                            HotMovieCacheSharedPreferences.saveHotMovieData(NewsClientApplication.getAppContext(), jsonData);
                            mView.onLoadComplete(hotMovieBean);
                        } else {
                            mView.onLoadErr(NewsClientApplication.getAppContext().getString(R.string.result_data_fail));
                        }
                    }
                });
    }


    public interface IMovieView extends IView {
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
