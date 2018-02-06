package com.tlkg.news.app.presenter;

import android.util.Base64;

import com.tlkg.news.app.base.BasePresenter;
import com.tlkg.news.app.bean.VideoContentBean;
import com.tlkg.news.app.http.HttpUtils;
import com.tlkg.news.app.http.IVideoApi;

import java.util.Random;
import java.util.zip.CRC32;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wuxiaoqi on 2018/2/6.
 */

public class NewsVideoPresenter extends BasePresenter<NewsVideoPresenter.INewsVideoView> {

    public NewsVideoPresenter(INewsVideoView view) {
        super(view);
    }

    private static String getVideoContentApi(String videoid) {
        String VIDEO_HOST = "http://ib.365yg.com";
        String VIDEO_URL = "/video/urls/v/1/toutiao/mp4/%s?r=%s";
        String r = getRandom();
        String s = String.format(VIDEO_URL, videoid, r);
        CRC32 crc32 = new CRC32();
        crc32.update(s.getBytes());
        String crcString = crc32.getValue() + "";
        return VIDEO_HOST + s + "&s=" + crcString;
    }


    private static String getRandom() {
        Random random = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            result.append(random.nextInt(10));
        }
        return result.toString();
    }

    @Override
    public void load() {
        String url = getVideoContentApi(mView.getVideoid());
        HttpUtils.getRetrofit().create(IVideoApi.class).getVideoContent(url)
                .subscribeOn(Schedulers.io())
                .map(new Function<VideoContentBean, String>() {
                    @Override
                    public String apply(@NonNull VideoContentBean videoContentBean) throws Exception {
                        VideoContentBean.DataBean.VideoListBean videoList = videoContentBean.getData().getVideo_list();
                        if (videoList.getVideo_3() != null) {
                            String base64 = videoList.getVideo_3().getMain_url();
                            String url = (new String(Base64.decode(base64.getBytes(), Base64.DEFAULT)));
                            return url;
                        }

                        if (videoList.getVideo_2() != null) {
                            String base64 = videoList.getVideo_2().getMain_url();
                            String url = (new String(Base64.decode(base64.getBytes(), Base64.DEFAULT)));
                            return url;
                        }
                        if (videoList.getVideo_1() != null) {
                            String base64 = videoList.getVideo_1().getMain_url();
                            String url = (new String(Base64.decode(base64.getBytes(), Base64.DEFAULT)));
                            return url;
                        }
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.onSetVidoeUrl(s);
                        mView.onHideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mView.onShowNetError();
                        mView.onHideLoading();
                    }
                });
    }

    public interface INewsVideoView extends IView {
        void onSetVidoeUrl(String url);

        String getVideoid();

        void onHideLoading();

        void onShowNetError();
    }
}
