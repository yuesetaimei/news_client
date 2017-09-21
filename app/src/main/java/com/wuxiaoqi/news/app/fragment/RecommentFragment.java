package com.wuxiaoqi.news.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.wuxiaoqi.news.app.R;
import com.wuxiaoqi.news.app.base.BaseFragment;

/**
 * Created by wuxiaoqi on 2017/9/21.
 * 推荐Fragment
 */

public class RecommentFragment extends BaseFragment {

    private final String TAG = "RecommentFragment";

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
