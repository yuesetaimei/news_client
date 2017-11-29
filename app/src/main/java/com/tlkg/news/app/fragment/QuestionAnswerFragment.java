package com.tlkg.news.app.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.tlkg.news.app.R;
import com.tlkg.news.app.base.BaseListFragment;
import com.tlkg.news.app.bean.LoadingBean;
import com.tlkg.news.app.bean.LoadingEndBean;
import com.tlkg.news.app.bean.QuestionAnswerDataBean;
import com.tlkg.news.app.binder.LoadingEndViewBinder;
import com.tlkg.news.app.binder.LoadingViewBinder;
import com.tlkg.news.app.binder.questionanswer.QuestionAnswerTextViewBinder;
import com.tlkg.news.app.binder.questionanswer.QuestionanswerOneImgViewBinder;
import com.tlkg.news.app.binder.questionanswer.WendaArticleThreeImgViewBinder;
import com.tlkg.news.app.presenter.QuestionAnswerPresenter;
import com.tlkg.news.app.util.OnLoadMoreListener;

import java.util.List;

import me.drakeet.multitype.ClassLinker;
import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.Items;

/**
 * Created by wuxiaoqi on 2017/11/15.
 */

public class QuestionAnswerFragment extends BaseListFragment implements QuestionAnswerPresenter.IQuestionAnswerView {

    private static final String TAG = "QuestionAnswerFragment";

    private QuestionAnswerPresenter presenter;

    public static QuestionAnswerFragment getInstance() {
        return new QuestionAnswerFragment();
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    protected void initPresenter() {
        presenter = new QuestionAnswerPresenter(this);
        recyclerView.addOnScrollListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (canLoadMore) {
                    canLoadMore = false;
                    presenter.loadMore(0, 0);
                }
            }
        });
        presenter.load();
    }

    @Override
    protected void registerAdapter() {
        adapter.register(QuestionAnswerDataBean.class)
                .to(new QuestionAnswerTextViewBinder(),
                        new QuestionanswerOneImgViewBinder(),
                        new WendaArticleThreeImgViewBinder())
                .withClassLinker(new ClassLinker<QuestionAnswerDataBean>() {
                    @NonNull
                    @Override
                    public Class<? extends ItemViewBinder<QuestionAnswerDataBean, ?>> index(@NonNull QuestionAnswerDataBean item) {
                        if (null != item.getExtraBean().getWenda_image() &&
                                null != item.getExtraBean().getWenda_image().getThree_image_list() &&
                                item.getExtraBean().getWenda_image().getThree_image_list().size() > 0) {
                            return WendaArticleThreeImgViewBinder.class;
                        }
                        if (null != item.getExtraBean().getWenda_image() &&
                                null != item.getExtraBean().getWenda_image().getLarge_image_list() &&
                                item.getExtraBean().getWenda_image().getLarge_image_list().size() > 0) {
                            return QuestionanswerOneImgViewBinder.class;
                        }
                        return QuestionAnswerTextViewBinder.class;
                    }
                });
        adapter.register(LoadingBean.class, new LoadingViewBinder());
        adapter.register(LoadingEndBean.class, new LoadingEndViewBinder());
    }

    @Override
    public void onLoadErr(String msg) {
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();
        oldItems.clear();
        adapter.notifyDataSetChanged();
        canLoadMore = false;
    }

    @Override
    public void onLoadStart() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onRefresh() {
        presenter.load();
    }

    @Override
    public void onSetAdapter(List<QuestionAnswerDataBean> list) {
        swipeRefreshLayout.setRefreshing(false);
        Items newItems = new Items(list);
        newItems.add(new LoadingBean());
        oldItems.clear();
        oldItems.addAll(newItems);
        adapter.notifyDataSetChanged();
        canLoadMore = true;
    }
}
