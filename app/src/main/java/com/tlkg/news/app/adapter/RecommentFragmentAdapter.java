package com.tlkg.news.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tlkg.news.app.base.BaseFragment;

import java.util.List;

/**
 * Created by wuxiaoqi on 2017/9/24.
 */

public class RecommentFragmentAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> mFragment;
    private List<String> mTitleList;

    public RecommentFragmentAdapter(FragmentManager fm, List<BaseFragment> fragment) {
        this(fm, fragment, null);
    }

    public RecommentFragmentAdapter(FragmentManager fm, List<BaseFragment> fragment, List<String> titleList) {
        super(fm);
        if (fragment == null) {
            throw new IllegalArgumentException("fragment cannot is null");
        }
        this.mFragment = fragment;
        this.mTitleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragment.get(position);
    }

    @Override
    public int getCount() {
        return mFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitleList == null) return "";
        return mTitleList.get(position % mTitleList.size());
    }

    public void addFragmentList(List<BaseFragment> fragment) {
        this.mFragment.clear();
        this.mFragment.addAll(fragment);
        notifyDataSetChanged();
    }
}
