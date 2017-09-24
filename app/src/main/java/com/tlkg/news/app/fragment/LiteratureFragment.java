package com.tlkg.news.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.tlkg.news.app.R;
import com.tlkg.news.app.base.BaseFragment;

/**
 * Created by wuxiaoqi on 2017/9/24.
 * 文学Fragment
 */

public class LiteratureFragment extends BaseFragment {
    private static final String TAG = "LiteratureFragment";

    public static Fragment getInstance() {
        return new LiteratureFragment();
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    public int getContentView() {
        return R.layout.fragment_literature;
    }

    @Override
    public void initEvent() {

    }
}
