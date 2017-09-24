package com.tlkg.news.app.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.View;

import com.tlkg.news.app.R;
import com.tlkg.news.app.adapter.RecommentFragmentAdapter;
import com.tlkg.news.app.base.BaseFragment;
import com.tlkg.news.app.ui.view.ChoiceScrollViewPager;
import com.tlkg.news.app.ui.view.ChoiceSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by wuxiaoqi on 2017/9/21.
 * 推荐Fragment
 */

public class RecommentFragment extends BaseFragment {

    private final String TAG = "RecommentFragment";

    @InjectView(R.id.fragment_recomment_refreshLayout)
    ChoiceSwipeRefreshLayout swipeRefreshLayout;

    @InjectView(R.id.fragment_recomment_tablayout)
    TabLayout tabLayout;

    @InjectView(R.id.fragment_recomment_viewpager)
    ChoiceScrollViewPager viewPager;

    private List<BaseFragment> fragments;
    private List<String> titleList;

    private RecommentFragmentAdapter mFragmentAdapter;

    public static Fragment getInstance() {
        return new RecommentFragment();
    }

    @Override
    protected void initData(Bundle bundle) {
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_recomment;
    }

    @Override
    public void initView(View view) {
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
//                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_blue_light);
        swipeRefreshLayout.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        initFragmentList();
        mFragmentAdapter = new RecommentFragmentAdapter(getChildFragmentManager(), fragments, titleList);
        viewPager.setAdapter(mFragmentAdapter);
        viewPager.setOffscreenPageLimit(fragments.size() - 1);//预加载
        mFragmentAdapter.notifyDataSetChanged();
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initFragmentList() {
        if (titleList == null)
            titleList = new ArrayList<>();
        if (fragments == null)
            fragments = new ArrayList<>();

        titleList.clear();
        titleList.add("最新");
        titleList.add("热点");
        titleList.add("干货定制");
        titleList.add("安卓");

        fragments.clear();
        fragments.add(new NewestFragment());
        fragments.add(new HotspotFragment());
        fragments.add(new CustomizedFragment());
        fragments.add(new AndroidFragment());
    }

    @Override
    public void initEvent() {

    }
}
