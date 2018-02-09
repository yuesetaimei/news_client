package com.tlkg.news.app.presenter;

import com.tlkg.news.app.NewsClientApplication;
import com.tlkg.news.app.R;
import com.tlkg.news.app.base.BasePresenter;
import com.tlkg.news.app.bean.BeautyBean;
import com.tlkg.news.app.http.HttpClient;
import com.tlkg.news.app.util.CommonSettingUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wuxiaoqi on 2017/9/29.
 * 福利Presenter
 */

public class WalfarePresenter extends BasePresenter<WalfarePresenter.IWelfareView> {

    public WalfarePresenter(IWelfareView view) {
        super(view);
    }

    @Override
    public void load() {
        isLoadMore = false;
        if (CommonSettingUtil.getInstance().getIsNoPhotoMode()) {
            mView.onLoadErr(NewsClientApplication.getStringId(R.string.only_wifi_load_img));
        } else {
            loadBeautyBean(PAGE_COUNT, 1);
        }
    }


    @Override
    public void loadMore(int pageCount, int pageIndex) {
        isLoadMore = true;
        loadBeautyBean(PAGE_COUNT, pageIndex);
    }

    private void loadBeautyBean(int pageCount, int pageIndex) {
        if (isLoading) {
            return;
        }
        isLoading = true;
        if (!isLoadMore) mView.onLoadStart();

        HttpClient.Builder.getGankServer().getBeautyBean(pageCount, pageIndex)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<BeautyBean>() {
                    @Override
                    public void onError(Throwable e) {
                        isLoading = false;
                        mView.onLoadErr(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BeautyBean beautyBean) {
                        isLoading = false;
                        if (isLoadMore) {
                            mView.onLoadMoreComplete(beautyBean);
                        } else {
                            mView.onLoadComplete(beautyBean);
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
        void onLoadComplete(BeautyBean data);

        /**
         * 加载更多完成
         *
         * @param data
         */
        void onLoadMoreComplete(BeautyBean data);

    }
}
