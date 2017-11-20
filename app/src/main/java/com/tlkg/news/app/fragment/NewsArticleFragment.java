package com.tlkg.news.app.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.tlkg.news.app.R;
import com.tlkg.news.app.base.BaseListFragment;
import com.tlkg.news.app.bean.LoadingBean;
import com.tlkg.news.app.bean.LoadingEndBean;
import com.tlkg.news.app.bean.MultiNewsArticleDataBean;
import com.tlkg.news.app.binder.LoadingEndViewBinder;
import com.tlkg.news.app.binder.LoadingViewBinder;
import com.tlkg.news.app.binder.news.NewsArticleImgViewBinder;
import com.tlkg.news.app.binder.news.NewsArticleTextViewBinder;
import com.tlkg.news.app.binder.news.NewsArticleVideoViewBinder;
import com.tlkg.news.app.presenter.NewsArticlePresenter;
import com.tlkg.news.app.util.OnLoadMoreListener;

import java.util.List;

import me.drakeet.multitype.ClassLinker;
import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.Items;

/**
 * Created by wuxiaoqi on 2017/11/14.
 */

public class NewsArticleFragment extends BaseListFragment implements NewsArticlePresenter.INewsArticleView {

    private static final String TAG = "NewsArticleFragment";

    private String categoryId;

    private NewsArticlePresenter presenter;

    public static NewsArticleFragment getInstance(String categoryId) {
        Bundle bundle = new Bundle();
        bundle.putString(TAG, categoryId);
        NewsArticleFragment fragment = new NewsArticleFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initData(Bundle bundle) {
        categoryId = getArguments().getString(TAG);
    }

    @Override
    protected void initPresenter() {
        presenter = new NewsArticlePresenter(this);
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
    public void registerAdapter() {
        adapter.register(MultiNewsArticleDataBean.class)
                .to(new NewsArticleImgViewBinder(),
                        new NewsArticleVideoViewBinder(),
                        new NewsArticleTextViewBinder())
                .withClassLinker(new ClassLinker<MultiNewsArticleDataBean>() {
                    @NonNull
                    @Override
                    public Class<? extends ItemViewBinder<MultiNewsArticleDataBean, ?>> index(@NonNull MultiNewsArticleDataBean item) {
                        if (item.isHas_video()) {
                            return NewsArticleVideoViewBinder.class;
                        }
                        if (null != item.getImage_list() && item.getImage_list().size() > 0) {
                            return NewsArticleImgViewBinder.class;
                        }
                        return NewsArticleTextViewBinder.class;
                    }
                });
        adapter.register(LoadingBean.class, new LoadingViewBinder());
        adapter.register(LoadingEndBean.class, new LoadingEndViewBinder());
    }

    @Override
    public void onRefresh() {
        presenter.load();
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
    public void onSetAdapter(List<MultiNewsArticleDataBean> list) {
        swipeRefreshLayout.setRefreshing(false);
        Items newItems = new Items(list);
        newItems.add(new LoadingBean());
        oldItems.clear();
        oldItems.addAll(newItems);
        adapter.notifyDataSetChanged();
        canLoadMore = true;
    }

    @Override
    public void onShowNoMore() {
        if (oldItems.size() > 0) {
            Items newItems = new Items(oldItems);
            newItems.remove(newItems.size() - 1);
            newItems.add(new LoadingEndBean());
            adapter.setItems(newItems);
            adapter.notifyDataSetChanged();
        } else if (oldItems.size() == 0) {
            oldItems.add(new LoadingEndBean());
            adapter.setItems(oldItems);
            adapter.notifyDataSetChanged();
        }
        canLoadMore = false;
    }

    @Override
    public void onHideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public String getCategory() {
        return categoryId;
    }
}
