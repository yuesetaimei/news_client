package com.tlkg.news.app.presenter;

import com.tlkg.news.app.base.BasePresenter;
import com.tlkg.news.app.bean.JokeCommentBean;
import com.tlkg.news.app.http.HttpUtils;
import com.tlkg.news.app.http.IJokeApi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wuxiaoqi on 2017/12/5.
 */

public class JokeContentPresenter extends BasePresenter<JokeContentPresenter.IJokeContentView> {

    private static final String TAG = "JokeContentPresenter";

    private String mJokeId;

    private int mJokeCommentCount;

    private int offset = 0;

    private List<JokeCommentBean.DataBean.RecentCommentsBean> commentsList = new ArrayList<>();

    public JokeContentPresenter(IJokeContentView view, String jokeId, int jokeCommentCount) {
        super(view);
        this.mJokeId = jokeId;
        this.mJokeCommentCount = jokeCommentCount;
    }

    @Override
    public void load() {
        mView.onLoadStart();
        loadData();
    }

    @Override
    public void loadMore(int pageCount, int pageIndex) {
        offset += pageCount;
        loadData();
    }

    private void loadData() {
        HttpUtils.getRetrofit().create(IJokeApi.class).getJokeComment(mJokeId, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<JokeCommentBean, List<JokeCommentBean.DataBean.RecentCommentsBean>>() {
                    @Override
                    public List<JokeCommentBean.DataBean.RecentCommentsBean> apply(JokeCommentBean jokeCommentBean) throws Exception {
                        return jokeCommentBean.getData().getRecent_comments();
                    }
                }).subscribe(new Consumer<List<JokeCommentBean.DataBean.RecentCommentsBean>>() {
            @Override
            public void accept(List<JokeCommentBean.DataBean.RecentCommentsBean> recentCommentsBeans) throws Exception {
                if (recentCommentsBeans.size() > 0) {
                    commentsList.addAll(recentCommentsBeans);
                    mView.onSetAdapter(commentsList);
                } else {
                    mView.noShowMore();
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                mView.onLoadErr(throwable.getMessage());
            }
        });
    }

    public interface IJokeContentView extends IView {
        /**
         * 加载完成
         *
         * @param list
         */
        void onSetAdapter(List<JokeCommentBean.DataBean.RecentCommentsBean> list);

        /**
         * 没有更多了
         */
        void noShowMore();
    }
}
