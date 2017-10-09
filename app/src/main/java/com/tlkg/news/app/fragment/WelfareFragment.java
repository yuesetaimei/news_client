package com.tlkg.news.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.tlkg.news.app.NewsClientApplication;
import com.tlkg.news.app.R;
import com.tlkg.news.app.adapter.WelfareRecyclerAdapter;
import com.tlkg.news.app.base.BaseFragment;
import com.tlkg.news.app.bean.BeautyBean;
import com.tlkg.news.app.presenter.WalfarePresenter;
import com.tlkg.news.app.ui.view.ChoiceSwipeRefreshLayout;

import java.util.List;

import butterknife.InjectView;

/**
 * Created by wuxiaoqi on 2017/9/24.
 * 福利Fragment
 */

public class WelfareFragment extends BaseFragment implements WalfarePresenter.IWelfareView {

    private static final String TAG = "WelfareFragment";

    @InjectView(R.id.fragment_welfare_refreshLayout)
    ChoiceSwipeRefreshLayout swipeRefreshLayout;

    @InjectView(R.id.fragment_welfare_recyclerview)
    RecyclerView recyclerView;

    private WalfarePresenter presenter;

    private WelfareRecyclerAdapter mAdapter;

    public static Fragment getInstance() {
        return new WelfareFragment();
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    public int getContentView() {
        return R.layout.fragment_welfare;
    }

    @Override
    public void initView(View view) {
        presenter = new WalfarePresenter(this);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
//                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_blue_light);
        swipeRefreshLayout.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.load();
            }
        });
        mAdapter = new WelfareRecyclerAdapter();
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(mAdapter);
        presenter.load();
    }

    @Override
    public void initEvent() {
    }

    @Override
    public void onLoad(BeautyBean data) {
        swipeRefreshLayout.setRefreshing(false);
        mAdapter.updateBeauty(data.getResults());
    }

    @Override
    public void onLoadMore(BeautyBean data) {
        swipeRefreshLayout.setRefreshing(false);
        mAdapter.updateMoreBeauty(data.getResults());
    }

    @Override
    public void onLoadErr(String msg) {
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(NewsClientApplication.getAppContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadStart() {
        swipeRefreshLayout.setRefreshing(true);
    }
}
