package com.tlkg.news.app.presenter;

import com.tlkg.news.app.base.BasePresenter;
import com.tlkg.news.app.bean.AndroidBean;
import com.tlkg.news.app.bean.BeautyBean;
import com.tlkg.news.app.http.HttpClient;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wuxiaoqi on 2017/9/29.
 * 福利Presenter
 */

public class WalfarePresenter extends BasePresenter {
    private IWelfareView mView;

    public WalfarePresenter(IWelfareView view) {
        this.mView = view;
    }

    @Override
    public void load() {
        isLoadMore = false;
        loadBeautyBean(PAGE_COUNT, 1);
    }


    @Override
    protected void loadMore(int pageCount, int pageIndex) {
        isLoadMore = true;
        loadBeautyBean(PAGE_COUNT, pageIndex);
    }

    private void loadBeautyBean(int pageCount, int pageIndex) {
        if (isLoading) {
            return;
        }
        isLoading = true;
        mView.onLoadStart();
        HttpClient.Builder.getGankServer().getBeautyBean(pageCount, pageIndex)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<BeautyBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoading = false;
                        mView.onLoadErr(e.getMessage());
                    }

                    @Override
                    public void onNext(BeautyBean beautyBean) {
                        isLoading = false;
                        if (isLoadMore) {
                            mView.onLoadMore(beautyBean);
                        } else {
                            mView.onLoad(beautyBean);
                        }
                    }
                });
    }


    public interface IWelfareView extends IView {
        /**
         * 加载完成
         *
         * @param data
         */
        void onLoad(BeautyBean data);

        /**
         * 加载更多完成
         *
         * @param data
         */
        void onLoadMore(BeautyBean data);

    }
}
