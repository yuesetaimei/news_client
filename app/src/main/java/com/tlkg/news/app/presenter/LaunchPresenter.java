package com.tlkg.news.app.presenter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.tlkg.news.app.NewsClientApplication;
import com.tlkg.news.app.base.BasePresenter;
import com.tlkg.news.app.bean.BingPicBean;
import com.tlkg.news.app.http.HttpClient;
import com.tlkg.news.app.shared.BingPicCacheSharedPreferences;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by wuxiaoqi on 2017/9/18.
 */

public class LaunchPresenter extends BasePresenter<LaunchPresenter.ILaunchView> {

    public LaunchPresenter(ILaunchView view) {
        super(view);
    }

    @Override
    public void load() {
        getBingPicData();
    }

    private void getBingPicData() {
        if (BingPicCacheSharedPreferences.isDayCache(NewsClientApplication.getAppContext())) {//从缓存中获取图片地址
            String bingPicData = BingPicCacheSharedPreferences.getBingPicData(NewsClientApplication.getAppContext());
            BingPicBean bingPicBean = new Gson().fromJson(bingPicData, BingPicBean.class);
            int index = BingPicCacheSharedPreferences.getBingPicIndex(NewsClientApplication.getAppContext()) + 1;//每次显示加1
            BingPicCacheSharedPreferences.saveBingPicIndex(NewsClientApplication.getAppContext(), index);
            int size = bingPicBean.getShowapi_res_body().getList().size();
            mView.setAdDataSuccess(bingPicBean.getShowapi_res_body().getList().get(index % size).get_$720x1280().replace('*', 'x'),
                    bingPicBean.getShowapi_res_body().getList().get(index % size).getTitle());
            return;
        }
        BingPicCacheSharedPreferences.clearBingPicCache(NewsClientApplication.getAppContext());
        HttpClient.Builder.getAdPicServer().getAdPic()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<BingPicBean>() {

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BingPicBean bingPicBean) {
                        if (bingPicBean != null && bingPicBean.getShowapi_res_body() != null &&
                                bingPicBean.getShowapi_res_body().getList() != null &&
                                bingPicBean.getShowapi_res_body().getList().get(0) != null &&
                                !TextUtils.isEmpty(bingPicBean.getShowapi_res_body().getList().get(0).getPic())) {
                            mView.setAdDataSuccess(bingPicBean.getShowapi_res_body().getList().get(0).get_$720x1280().replace('*', 'x'),
                                    bingPicBean.getShowapi_res_body().getList().get(0).getTitle());

                            String json = new Gson().toJson(bingPicBean);
                            BingPicCacheSharedPreferences.saveBingPicData(NewsClientApplication.getAppContext(),
                                    json,
                                    bingPicBean.getShowapi_res_body().getList().size());
                        }
                    }
                });
    }


    public interface ILaunchView {
        void setAdDataSuccess(String bingPicUrl, String title);
    }
}
