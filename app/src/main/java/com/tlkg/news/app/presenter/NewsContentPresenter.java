package com.tlkg.news.app.presenter;

import android.text.TextUtils;

import com.tlkg.news.app.base.BasePresenter;
import com.tlkg.news.app.bean.MultiNewsArticleDataBean;
import com.tlkg.news.app.bean.NewsContentBean;
import com.tlkg.news.app.http.HttpUtils;
import com.tlkg.news.app.http.INewsApi;
import com.tlkg.news.app.util.CommonSettingUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by wuxiaoqi on 2018/2/2.
 */

public class NewsContentPresenter extends BasePresenter<NewsContentPresenter.INewsContentView> {

    MultiNewsArticleDataBean dataBean;

    public NewsContentPresenter(INewsContentView view, MultiNewsArticleDataBean dataBean) {
        super(view);
        this.dataBean = dataBean;
    }

    @Override
    public void load() {
        if (dataBean == null) return;

        final String url = dataBean.getDisplay_url();
        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                        try {
                            Response<ResponseBody> response = HttpUtils.getRetrofit().create(INewsApi.class)
                                    .getNewsContentRedirectUrl(url).execute();
                            // 获取重定向后的 URL 用于拼凑API
                            if (response.isSuccessful()) {
                                String httpUrl = response.raw().request().url().toString();
                                if (!TextUtils.isEmpty(httpUrl) && httpUrl.contains("toutiao")) {
                                    String api = httpUrl + "info/";
                                    e.onNext(api);
                                } else {
                                    e.onError(new Throwable());
                                }
                            } else {
                                e.onError(new Throwable());
                            }
                        } catch (Exception e1) {
                            e.onComplete();
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .switchMap(new Function<String, ObservableSource<NewsContentBean>>() {
                    @Override
                    public ObservableSource<NewsContentBean> apply(String s) throws Exception {
                        return HttpUtils.getRetrofit().create(INewsApi.class).getNewsContent(s);
                    }
                })
                .map(new Function<NewsContentBean, String>() {
                    @Override
                    public String apply(@NonNull NewsContentBean bean) throws Exception {
                        return getHTML(bean);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        mView.onSetWebView(s, true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.onSetWebView(null, false);
                    }

                    @Override
                    public void onComplete() {
                        doShowNetError();
                    }
                });
    }

    private String getHTML(NewsContentBean bean) {
        String title = bean.getData().getTitle();
        String content = bean.getData().getContent();
        if (content != null) {

            String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/news_light.css\" type=\"text/css\">";
            if (CommonSettingUtil.getInstance().getIsNightMode()) {
                css = css.replace("news_light", "news_dark");
            }

            String html = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">" +
                    css +
                    "<body>\n" +
                    "<article class=\"article-container\">\n" +
                    "    <div class=\"article__content article-content\">" +
                    "<h1 class=\"article-title\">" +
                    title +
                    "</h1>" +
                    content +
                    "    </div>\n" +
                    "</article>\n" +
                    "</body>\n" +
                    "</html>";

            return html;
        } else {
            return null;
        }
    }

    public void doShowNetError() {
        mView.onHideLoading();
        mView.onShowNetError();
    }

    public interface INewsContentView extends IView {
        void onSetWebView(String url, boolean flag);

        void onHideLoading();

        void onShowNetError();
    }
}
