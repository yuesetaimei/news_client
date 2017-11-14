package com.tlkg.news.app.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tlkg.news.app.R;
import com.tlkg.news.app.base.BaseFragment;
import com.tlkg.news.app.util.CommonSettingUtil;

import butterknife.InjectView;

/**
 * Created by wuxiaoqi on 2017/11/14.
 */

public class NewsArticleFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "NewsArticleFragment";

    private String categoryId;

    @InjectView(R.id.fragment_list_refreshlayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @InjectView(R.id.fragment_list_recyclerview)
    RecyclerView recyclerView;

//    private  adapter;


    public static NewsArticleFragment getInstance(String categoryId) {
        Bundle bundle = new Bundle();
        bundle.putString(TAG, categoryId);
        NewsArticleFragment fragment = new NewsArticleFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    public void initView(View view) {
        recyclerView.setHasFixedSize(true);

        swipeRefreshLayout.setColorSchemeColors(CommonSettingUtil.getInstance().getThemeColor());
        swipeRefreshLayout.setOnRefreshListener(this);
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

    }
}
