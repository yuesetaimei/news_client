package com.tlkg.news.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.tlkg.news.app.R;
import com.tlkg.news.app.base.BaseFragment;

/**
 * Created by wuxiaoqi on 2017/9/24.
 * 我的Fragment
 */

public class MyFragment extends BaseFragment {

    private static final String TAG = "MyFragment";

    private static MyFragment instance = null;

    public static Fragment getInstance() {
        if (instance == null) instance = new MyFragment();
        return instance;
    }


    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    public int getContentView() {
        return R.layout.fragment_my;
    }

    @Override
    public void initEvent() {

    }
}
