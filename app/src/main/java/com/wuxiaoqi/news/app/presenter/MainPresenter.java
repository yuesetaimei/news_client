package com.wuxiaoqi.news.app.presenter;

import com.wuxiaoqi.news.app.base.BasePresenter;
import com.wuxiaoqi.news.app.bean.BannerBean;
import com.wuxiaoqi.news.app.http.HttpClient;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wuxiaoqi on 2017/9/23.
 */

public class MainPresenter implements BasePresenter {
    private IMainView mView;

    public MainPresenter(IMainView view) {
        this.mView = view;
    }

    @Override
    public void start() {
        HttpClient.Builder.getTingServer().getBannerPage()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<BannerBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BannerBean bannerBean) {
                        if (mView != null) {
                            mView.setBannerData(bannerBean);
                        }
                    }
                });
    }

    public interface IMainView{
        void setBannerData(BannerBean bannerBean);
    }
}
