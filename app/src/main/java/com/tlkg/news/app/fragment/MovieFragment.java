package com.tlkg.news.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.tlkg.news.app.NewsClientApplication;
import com.tlkg.news.app.R;
import com.tlkg.news.app.activity.DoubanTopActivity;
import com.tlkg.news.app.adapter.MovieRecyclerAdapter;
import com.tlkg.news.app.base.BaseEvent;
import com.tlkg.news.app.base.BaseFragment;
import com.tlkg.news.app.bean.HotMovieBean;
import com.tlkg.news.app.event.DoubanTop250CliekEvent;
import com.tlkg.news.app.presenter.MovidePresenter;
import com.tlkg.news.app.ui.view.ChoiceSwipeRefreshLayout;

import org.greenrobot.eventbus.Subscribe;

import butterknife.InjectView;

/**
 * Created by wuxiaoqi on 2017/9/24.
 * 电影Fragment
 */

public class MovieFragment extends BaseFragment implements MovidePresenter.IMovieView {

    private static final String TAG = "MovieFragment";

    @InjectView(R.id.fragment_movie_refreshLayout)
    ChoiceSwipeRefreshLayout mRefreshLayout;

    @InjectView(R.id.fragment_movie_recyclerview)
    RecyclerView mRecyclerView;

    private MovidePresenter mPresenter;

    private MovieRecyclerAdapter mAdapter;

    private View mHeadView = null;

    public static Fragment getInstance() {
        return new MovieFragment();
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    public int getContentView() {
        return R.layout.fragment_movie;
    }

    @Override
    public void initView(View view) {
        mPresenter = new MovidePresenter(this);
        mRefreshLayout.init();
        setAdapter();
    }

    private void setAdapter() {
        mAdapter = new MovieRecyclerAdapter();
        mAdapter.setEnableLoadMore(false);
        LinearLayoutManager ll = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(ll);
        mRecyclerView.setAdapter(mAdapter);
        if (mHeadView == null) {
            mHeadView = View.inflate(getContext(), R.layout.item_movie_head, null);
        }
        mAdapter.addHeadView(mHeadView);
        mAdapter.setHeadViewHolder(new MovieRecyclerAdapter.HeadViewHoler(mHeadView));
    }

    @Override
    public void initEvent() {
        mPresenter.load();
    }

    @Override
    public void onLoadErr(String msg) {
        mRefreshLayout.setRefreshing(false);
        Toast.makeText(NewsClientApplication.getAppContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadStart() {
        mRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onLoadComplete(HotMovieBean data) {
        mRefreshLayout.setRefreshing(false);
        mRefreshLayout.setEnabled(false);
        mAdapter.refreshData(data.getSubjects());
    }

    @Override
    public void onLoadMoreComplete(HotMovieBean data) {
        //暂时没有加载更多
    }

    @Subscribe
    public void onEvent(BaseEvent event) {
        if (event instanceof DoubanTop250CliekEvent) {
            DoubanTopActivity.startActivity(getActivity());
        }
    }
}
