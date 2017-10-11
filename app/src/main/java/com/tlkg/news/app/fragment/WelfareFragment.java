package com.tlkg.news.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ScrollerCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.tlkg.news.app.NewsClientApplication;
import com.tlkg.news.app.R;
import com.tlkg.news.app.activity.BigImageViewActivity;
import com.tlkg.news.app.adapter.WelfareRecyclerAdapter;
import com.tlkg.news.app.base.BaseEvent;
import com.tlkg.news.app.base.BaseFragment;
import com.tlkg.news.app.base.BasePresenter;
import com.tlkg.news.app.bean.BeautyBean;
import com.tlkg.news.app.event.NetAgainLoadEvent;
import com.tlkg.news.app.event.NetworkErrEvent;
import com.tlkg.news.app.event.WelfareClickEvent;
import com.tlkg.news.app.presenter.WalfarePresenter;
import com.tlkg.news.app.ui.view.ChoiceSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
        swipeRefreshLayout.init();
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
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                if (recyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE) {
//                    Glide.with(getContext()).pauseRequests();
//                } else {
//                    Glide.with(getContext()).resumeRequests();
//                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        mAdapter.setLoadMoreListener(new WelfareRecyclerAdapter.ILoadMoreListener() {
            @Override
            public void onLoadMore() {
                presenter.loadMore(BasePresenter.PAGE_COUNT, mAdapter.getItemCount() / 20 + 1);
            }
        });
        presenter.load();
    }

    @Override
    public void initEvent() {
    }

    @Override
    public void onLoadComplete(BeautyBean data) {
        swipeRefreshLayout.setRefreshing(false);
        mAdapter.updateBeauty(data.getResults());
    }

    @Override
    public void onLoadMoreComplete(BeautyBean data) {
        swipeRefreshLayout.setRefreshing(false);
        mAdapter.updateMoreBeauty(data.getResults());
    }

    @Override
    public void onLoadErr(String msg) {
        swipeRefreshLayout.setRefreshing(false);
        EventBus.getDefault().post(new NetworkErrEvent(1));
        Toast.makeText(NewsClientApplication.getAppContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadStart() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Subscribe
    public void onEvent(BaseEvent event) {
        if (event instanceof WelfareClickEvent) {
            WelfareClickEvent welfareClickEvent = (WelfareClickEvent) event;
            BigImageViewActivity.startActivity(getActivity(), welfareClickEvent.mList, welfareClickEvent.mPosition);//跳转查看大图Activity
        } else if (event instanceof NetAgainLoadEvent) {
            NetAgainLoadEvent netAgainLoadEvent = (NetAgainLoadEvent) event;
            if (netAgainLoadEvent.mPosition == 1) {
                presenter.load();
            }
        }
    }
}
