package com.tlkg.news.app.presenter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.tlkg.news.app.base.BasePresenter;
import com.tlkg.news.app.bean.QuestionAnswerDataBean;
import com.tlkg.news.app.bean.WendaArticleBean;
import com.tlkg.news.app.http.HttpUtils;
import com.tlkg.news.app.http.IMobileNewsApi;
import com.tlkg.news.app.util.DataUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wuxiaoqi on 2017/11/15.
 */

public class QuestionAnswerPresenter extends BasePresenter<QuestionAnswerPresenter.IQuestionAnswerView> {

    private static final String TAG = "QuestionAnswerPresenter";

    private String time;

    private Gson gson = new Gson();

    private List<QuestionAnswerDataBean> dataList = new ArrayList<>();

    public QuestionAnswerPresenter(QuestionAnswerPresenter.IQuestionAnswerView view) {
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
        // 释放内存
        if (dataList.size() > 100) {
            dataList.clear();
        }

        HttpUtils.getInstance().getRetrofit().create(IMobileNewsApi.class)
                .getWendaArticle(time)
                .subscribeOn(Schedulers.io())
                .switchMap(new Function<WendaArticleBean, Observable<QuestionAnswerDataBean>>() {
                    @Override
                    public Observable<QuestionAnswerDataBean> apply(@NonNull WendaArticleBean wendaArticleBean) throws Exception {

                        List<QuestionAnswerDataBean> list = new ArrayList<>();
                        for (WendaArticleBean.DataBean bean : wendaArticleBean.getData()) {
                            QuestionAnswerDataBean contentBean = gson.fromJson(bean.getContent(), QuestionAnswerDataBean.class);
                            list.add(contentBean);
                        }
                        return Observable.fromIterable(list);
                    }
                })
                .filter(new Predicate<QuestionAnswerDataBean>() {
                    @Override
                    public boolean test(@NonNull QuestionAnswerDataBean wendaArticleDataBean) throws Exception {
                        return !TextUtils.isEmpty(wendaArticleDataBean.getQuestion());
                    }
                })
                .map(new Function<QuestionAnswerDataBean, QuestionAnswerDataBean>() {
                    @Override
                    public QuestionAnswerDataBean apply(@NonNull QuestionAnswerDataBean bean) throws Exception {

                        QuestionAnswerDataBean.ExtraBean extraBean = gson.fromJson(bean.getExtra(), QuestionAnswerDataBean.ExtraBean.class);
                        QuestionAnswerDataBean.QuestionBean questionBean = gson.fromJson(bean.getQuestion(), QuestionAnswerDataBean.QuestionBean.class);
                        QuestionAnswerDataBean.AnswerBean answerBean = gson.fromJson(bean.getAnswer(), QuestionAnswerDataBean.AnswerBean.class);
                        bean.setExtraBean(extraBean);
                        bean.setQuestionBean(questionBean);
                        bean.setAnswerBean(answerBean);

                        time = bean.getBehot_time();
                        return bean;
                    }
                })
                .filter(new Predicate<QuestionAnswerDataBean>() {
                    @Override
                    public boolean test(@NonNull QuestionAnswerDataBean wendaArticleDataBean) throws Exception {
                        for (QuestionAnswerDataBean bean : dataList) {
                            if (bean.getQuestionBean().getTitle().equals(wendaArticleDataBean.getQuestionBean().getTitle())) {
                                return false;
                            }
                        }
                        return true;
                    }
                })
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<QuestionAnswerDataBean>>() {
                    @Override
                    public void accept(@NonNull List<QuestionAnswerDataBean> wendaArticleDataBeen) throws Exception {
                        dataList.addAll(wendaArticleDataBeen);
                        mView.onSetAdapter(dataList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mView.onLoadErr(throwable.getMessage());
                    }
                });
    }

    public interface IQuestionAnswerView extends IView {
        /**
         * 加载完成
         *
         * @param list
         */
        void onSetAdapter(List<QuestionAnswerDataBean> list);

    }
}


