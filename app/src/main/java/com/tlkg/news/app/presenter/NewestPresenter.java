package com.tlkg.news.app.presenter;

import com.tlkg.news.app.NewsClientApplication;
import com.tlkg.news.app.base.BasePresenter;
import com.tlkg.news.app.bean.BannerBean;
import com.tlkg.news.app.http.HttpClient;
import com.tlkg.news.app.shared.BannerCacheSharedPreferences;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wuxiaoqi on 2017/9/24.
 */

public class NewestPresenter extends BasePresenter<NewestPresenter.INewestView> {

    public NewestPresenter(INewestView view) {
        super(view);
    }

    @Override
    public void load() {
        getBannerData();
    }

    private void getBannerData() {
        if (BannerCacheSharedPreferences.isDayCache(NewsClientApplication.getAppContext())) {
            BannerBean bannerData = BannerCacheSharedPreferences.getBannerData(NewsClientApplication.getAppContext());
            setBannerList(bannerData);
            return;
        }
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
                        BannerCacheSharedPreferences.clearBannerCache(NewsClientApplication.getAppContext());
                        BannerCacheSharedPreferences.saveBannerData(NewsClientApplication.getAppContext(), bannerBean);
                        setBannerList(bannerBean);
                    }
                });
    }

    private void setBannerList(BannerBean bannerBean) {
        List<String> list = new ArrayList<String>();
        List<BannerBean.ResultBeanXXXXXXXXXXXXXXXXX.FocusBean.ResultBeanXXXXXXXXXXXXXXX> result = bannerBean.getResult().getFocus().getResult();
        for (int i = 0; i < result.size(); i++) {
            list.add(result.get(i).getRandpic());
        }
        if (mView != null) {
            mView.setBannerData(list);
        }
    }

    public interface INewestView {
        void setBannerData(List<String> list);
    }
}
