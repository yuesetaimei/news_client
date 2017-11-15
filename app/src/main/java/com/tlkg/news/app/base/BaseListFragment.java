package com.tlkg.news.app.base;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tlkg.news.app.R;
import com.tlkg.news.app.util.CommonSettingUtil;

import butterknife.InjectView;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by wuxiaoqi on 2017/11/15.
 * 推荐页基类Fragment
 */

public abstract class BaseListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    protected MultiTypeAdapter adapter;

    @InjectView(R.id.fragment_list_refreshlayout)
    protected SwipeRefreshLayout swipeRefreshLayout;

    @InjectView(R.id.fragment_list_recyclerview)
    protected RecyclerView recyclerView;

    protected Items oldItems;

    protected boolean canLoadMore = false;

    @Override
    protected void initData(Bundle bundle) {
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_list;
    }

    @Override
    public void initView(View view) {
        recyclerView.setHasFixedSize(true);
        swipeRefreshLayout.setColorSchemeColors(CommonSettingUtil.getInstance().getThemeColor());
        swipeRefreshLayout.setOnRefreshListener(this);
        adapter = new MultiTypeAdapter(oldItems = new Items());
        recyclerView.setAdapter(adapter);
        registerAdapter();
        initPresenter();
    }

    protected abstract void initPresenter();

    protected abstract void registerAdapter();

    @Override
    public void initEvent() {

    }

    @Override
    public void onRefresh() {

    }
}
