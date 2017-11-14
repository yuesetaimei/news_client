package com.tlkg.news.app.presenter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.tlkg.news.app.base.BasePresenter;
import com.tlkg.news.app.bean.MultiNewsArticleBean;
import com.tlkg.news.app.bean.MultiNewsArticleDataBean;
import com.tlkg.news.app.http.HttpUtils;
import com.tlkg.news.app.http.IMobileNewsApi;
import com.tlkg.news.app.util.DataUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by wuxiaoqi on 2017/11/14.
 */

public class NewsArticlePresenter extends BasePresenter<NewsArticlePresenter.INewsArticleView> {

    private static final String TAG = "NewsArticlePresenter";
    private List<MultiNewsArticleDataBean> dataList = new ArrayList<>();
    private String category;
    private String time;
    private Gson gson = new Gson();
    private Random random = new Random();

    public NewsArticlePresenter(INewsArticleView view) {
        super(view);
        this.time = DataUtils.getCurrentTimeStamp();
    }

    @Override
    public void load() {
        mView.onLoadStart();
        if (dataList.size() != 0) {
            dataList.clear();
            time = DataUtils.getCurrentTimeStamp();
        }
        loadMore(0, 0);
    }

    @Override
    public void loadMore(int pageCount, int pageIndex) {
        if (this.category == null) {
            this.category = mView.getCategory();
        }

        // 释放内存
        if (dataList.size() > 150) {
            dataList.clear();
        }

        getRandom()
                .subscribeOn(Schedulers.io())
                .switchMap(new Function<MultiNewsArticleBean, Observable<MultiNewsArticleDataBean>>() {
                    @Override
                    public Observable<MultiNewsArticleDataBean> apply(@NonNull MultiNewsArticleBean multiNewsArticleBean) throws Exception {
                        List<MultiNewsArticleDataBean> dataList = new ArrayList<>();
                        for (MultiNewsArticleBean.DataBean dataBean : multiNewsArticleBean.getData()) {
                            dataList.add(gson.fromJson(dataBean.getContent(), MultiNewsArticleDataBean.class));
                        }
                        return Observable.fromIterable(dataList);
                    }
                })
                .filter(new Predicate<MultiNewsArticleDataBean>() {
                    @Override
                    public boolean test(@NonNull MultiNewsArticleDataBean dataBean) throws Exception {
                        time = dataBean.getBehot_time();
                        if (TextUtils.isEmpty(dataBean.getSource())) {
                            return false;
                        }
                        try {
                            // 过滤头条问答新闻
                            if (dataBean.getSource().contains("头条问答")
                                    || dataBean.getTag().contains("ad")
                                    || dataBean.getSource().contains("悟空问答")) {
                                return false;
                            }
                            // 过滤头条问答新闻
                            if (dataBean.getRead_count() == 0 || TextUtils.isEmpty(dataBean.getMedia_name())) {
                                String title = dataBean.getTitle();
                                if (title.lastIndexOf("？") == title.length() - 1) {
                                    return false;
                                }
                            }
                        } catch (NullPointerException e) {
                        }
                        // 过滤重复新闻(与上次刷新的数据比较)
                        for (MultiNewsArticleDataBean bean : dataList) {
                            if (bean.getTitle().equals(dataBean.getTitle())) {
                                return false;
                            }
                        }
                        return true;
                    }
                })
                .toList()
                .map(new Function<List<MultiNewsArticleDataBean>, List<MultiNewsArticleDataBean>>() {
                    @Override
                    public List<MultiNewsArticleDataBean> apply(@NonNull List<MultiNewsArticleDataBean> list) throws Exception {
                        // 过滤重复新闻(与本次刷新的数据比较,因为使用了2个请求,数据会有重复)
                        for (int i = 0; i < list.size() - 1; i++) {
                            for (int j = list.size() - 1; j > i; j--) {
                                if (list.get(j).getTitle().equals(list.get(i).getTitle())) {
                                    list.remove(j);
                                }
                            }
                        }
                        return list;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<MultiNewsArticleDataBean>>() {
                    @Override
                    public void accept(@NonNull List<MultiNewsArticleDataBean> list) throws Exception {
                        if (null != list && list.size() > 0) {
                            doSetAdapter(list);
                        } else {
                            doShowNoMore();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mView.onLoadErr("");
                    }
                });
    }

    public void doSetAdapter(List<MultiNewsArticleDataBean> list) {
        dataList.addAll(list);
        mView.onSetAdapter(dataList);
        mView.onHideLoading();
    }

    public void doShowNoMore() {
        mView.onHideLoading();
        mView.onShowNoMore();
    }

    public interface INewsArticleView extends IView {
        /**
         * 加载完成
         *
         * @param list
         */
        void onSetAdapter(List<MultiNewsArticleDataBean> list);

        void onShowNoMore();

        void onHideLoading();

        /**
         * 获取加载的id
         *
         * @return
         */
        String getCategory();
    }

    private Observable<MultiNewsArticleBean> getRandom() {

        int i = random.nextInt(10);
        if (i % 2 == 0) {
            Observable<MultiNewsArticleBean> ob1 = HttpUtils.getInstance().getRetrofit().create(IMobileNewsApi.class)
                    .getNewsArticle(this.category, this.time);
            return ob1;
        } else {
            Observable<MultiNewsArticleBean> ob2 = HttpUtils.getInstance().getRetrofit().create(IMobileNewsApi.class)
                    .getNewsArticle2(this.category, this.time);
            return ob2;
        }
    }
}
