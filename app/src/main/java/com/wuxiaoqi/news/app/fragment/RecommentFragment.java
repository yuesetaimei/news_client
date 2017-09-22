package com.wuxiaoqi.news.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.wuxiaoqi.news.app.R;
import com.wuxiaoqi.news.app.base.BaseFragment;
import com.wuxiaoqi.news.app.ui.view.ChoiceScrollViewPager;

import butterknife.InjectView;

/**
 * Created by wuxiaoqi on 2017/9/21.
 * 推荐Fragment
 */

public class RecommentFragment extends BaseFragment {

    private final String TAG = "RecommentFragment";

    @InjectView(R.id.fragment_recomment_viewpager)
    ChoiceScrollViewPager viewPager;

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
    public void initEvent() {

    }
}
