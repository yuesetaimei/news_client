package com.tlkg.news.app.http;

import com.tlkg.news.app.bean.AndroidBean;
import com.tlkg.news.app.bean.BannerBean;
import com.tlkg.news.app.bean.BeautyBean;
import com.tlkg.news.app.bean.BingPicBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by wuxiaoqi on 2017/9/22.
 */

public interface HttpClient {

    class Builder {
        public static HttpClient getTingServer() {
            return HttpUtils.getInstance().getTingServer(HttpClient.class);
        }

        public static HttpClient getAdPicServer() {
            return HttpUtils.getInstance().getAdPicServer(HttpClient.class);
        }

        public static HttpClient getGankServer() {
            return HttpUtils.getInstance().getGankServer(HttpClient.class);
        }
    }

    /**
     * 首页轮播图
     */
    @GET("ting?from=android&version=5.8.1.0&channel=ppzs&operator=3&method=baidu.ting.plaza.index&cuid=89CF1E1A06826F9AB95A34DC0F6AAA14")
    Observable<BannerBean> getBannerPage();

    /**
     * 广告页图片
     *
     * @return
     */
    @GET("?showapi_appid=46833&showapi_sign=9b0e9f8c3fc843ab90451fe6522e6d7c")
    Observable<BingPicBean> getAdPic();

    /**
     * 获取Android数据
     *
     * @return
     */
    @GET("Android/{pageCount}/{pageIndex}")
    Observable<AndroidBean> getAndroidBean(@Path("pageCount") int pageCount, @Path("pageIndex") int pageIndex);

    /**
     * 获取美女图片数据
     *
     * @return
     */
    @GET("福利/{pageCount}/{pageIndex}")
    Observable<BeautyBean> getBeautyBean(@Path("pageCount") int pageCount, @Path("pageIndex") int pageIndex);
}
