package com.tlkg.news.app.fragment;

import android.widget.Toast;

import com.tlkg.news.app.R;
import com.tlkg.news.app.base.BaseListFragment;
import com.tlkg.news.app.bean.JokeContentBean;
import com.tlkg.news.app.bean.LoadingBean;
import com.tlkg.news.app.bean.LoadingEndBean;
import com.tlkg.news.app.binder.LoadingEndViewBinder;
import com.tlkg.news.app.binder.LoadingViewBinder;
import com.tlkg.news.app.binder.joke.JokeContentViewBinder;
import com.tlkg.news.app.presenter.JokePresenter;
import com.tlkg.news.app.util.OnLoadMoreListener;

import java.util.List;

import me.drakeet.multitype.Items;

/**
 * Created by wuxiaoqi on 2017/11/29.
 */

public class JokeFragment extends BaseListFragment implements JokePresenter.IJokeView {

    private static final String TAG = "JokeFragment";

    private JokePresenter presenter;

    public static JokeFragment getInstance() {
        return new JokeFragment();
    }

    @Override
    protected void initPresenter() {
        presenter = new JokePresenter(this);
        recyclerView.addOnScrollListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (canLoadMore) {
                    canLoadMore = false;
                    presenter.load();
                }
            }
        });
        presenter.load();
    }

    @Override
    protected void registerAdapter() {
        adapter.register(JokeContentBean.DataBean.GroupBean.class, new JokeContentViewBinder());
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
    public void onSetAdapter(List<JokeContentBean.DataBean.GroupBean> list) {
        swipeRefreshLayout.setRefreshing(false);
        Items newItems = new Items(list);
        newItems.add(new LoadingBean());
        oldItems.clear();
        oldItems.addAll(newItems);
        adapter.notifyDataSetChanged();
        canLoadMore = true;
    }

    @Override
    public void onRefresh() {
        presenter.load();
    }

    @Override
    public void noShowMore() {
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
}
