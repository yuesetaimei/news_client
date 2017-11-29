package com.tlkg.news.app.presenter;

import com.tlkg.news.app.base.BasePresenter;
import com.tlkg.news.app.bean.JokeContentBean;
import com.tlkg.news.app.http.HttpUtils;
import com.tlkg.news.app.http.IJokeApi;
import com.tlkg.news.app.util.DataUtils;
import com.tlkg.news.app.util.PhoneUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wuxiaoqi on 2017/11/29.
 */

public class JokePresenter extends BasePresenter<JokePresenter.IJokeView> {

    private static final String TAG = "JokePresenter";

    private List<JokeContentBean.DataBean.GroupBean> groupList = new ArrayList<>();
    private String time;

    public JokePresenter(IJokeView view) {
        super(view);
        this.time = DataUtils.getCurrentTimeStamp();
    }

    @Override
    public void load() {
        Map<String, String> map = PhoneUtil.getAsCp();

        HttpUtils.getRetrofit().create(IJokeApi.class).getJokeContent(time, map.get("as"), map.get("cp"))
                .subscribeOn(Schedulers.io())
                .map(new Function<JokeContentBean, List<JokeContentBean.DataBean.GroupBean>>() {
                    @Override
                    public List<JokeContentBean.DataBean.GroupBean> apply(JokeContentBean jokeContentBean) throws Exception {
                        List<JokeContentBean.DataBean> data = jokeContentBean.getData();
                        for (JokeContentBean.DataBean dataBean : data) {
                            groupList.add(dataBean.getGroup());
                        }
                        time = jokeContentBean.getNext().getMax_behot_time() + "";
                        return groupList;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<JokeContentBean.DataBean.GroupBean>>() {
                    @Override
                    public void accept(List<JokeContentBean.DataBean.GroupBean> groupBeans) throws Exception {
                        if (groupBeans.size() > 0) {
                            mView.onSetAdapter(groupBeans);
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

    public interface IJokeView extends IView {
        /**
         * 加载完成
         *
         * @param list
         */
        void onSetAdapter(List<JokeContentBean.DataBean.GroupBean> list);

        /**
         * 没有更多了
         */
        void noShowMore();
    }
}
