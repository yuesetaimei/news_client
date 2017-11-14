package com.tlkg.news.app.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tlkg.news.app.R;
import com.tlkg.news.app.base.BaseFragment;
import com.tlkg.news.app.bean.LoadingBean;
import com.tlkg.news.app.bean.LoadingEndBean;
import com.tlkg.news.app.bean.MultiNewsArticleDataBean;
import com.tlkg.news.app.binder.LoadingEndViewBinder;
import com.tlkg.news.app.binder.LoadingViewBinder;
import com.tlkg.news.app.binder.news.NewsArticleImgViewBinder;
import com.tlkg.news.app.binder.news.NewsArticleTextViewBinder;
import com.tlkg.news.app.binder.news.NewsArticleVideoViewBinder;
import com.tlkg.news.app.presenter.NewsArticlePresenter;
import com.tlkg.news.app.util.CommonSettingUtil;
import com.tlkg.news.app.util.OnLoadMoreListener;

import java.util.List;

import butterknife.InjectView;
import me.drakeet.multitype.ClassLinker;
import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by wuxiaoqi on 2017/11/14.
 */

public class NewsArticleFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, NewsArticlePresenter.INewsArticleView {

    private static final String TAG = "NewsArticleFragment";

    private String categoryId;

    @InjectView(R.id.fragment_list_refreshlayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @InjectView(R.id.fragment_list_recyclerview)
    RecyclerView recyclerView;

    protected boolean canLoadMore = false;

    private Items items;

    private MultiTypeAdapter adapter;

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
    public void initView(View view) {
        presenter = new NewsArticlePresenter(this);
        recyclerView.setHasFixedSize(true);
        swipeRefreshLayout.setColorSchemeColors(CommonSettingUtil.getInstance().getThemeColor());
        swipeRefreshLayout.setOnRefreshListener(this);
        adapter = new MultiTypeAdapter(items = new Items());
        registerAdapter();
        recyclerView.setAdapter(adapter);
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

    private void registerAdapter() {
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
    public int getContentView() {
        return R.layout.fragment_list;
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onRefresh() {
        presenter.load();
    }

    @Override
    public void onLoadErr(String msg) {
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();
        adapter.setItems(new Items());
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
        items.clear();
        items.addAll(newItems);
        adapter.notifyDataSetChanged();
        canLoadMore = true;
    }

    @Override
    public void onShowNoMore() {
        if (items.size() > 0) {
            Items newItems = new Items(items);
            newItems.remove(newItems.size() - 1);
            newItems.add(new LoadingEndBean());
            adapter.setItems(newItems);
            adapter.notifyDataSetChanged();
        } else if (items.size() == 0) {
            items.add(new LoadingEndBean());
            adapter.setItems(items);
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
