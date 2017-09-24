package com.tlkg.news.app.fragment;

import android.os.Bundle;

import com.tlkg.news.app.R;
import com.tlkg.news.app.base.BaseFragment;

/**
 * Created by wuxiaoqi on 2017/9/24.
 * 定制Fragment
 */

public class CustomizedFragment extends BaseFragment {
    private static final String TAG = "CustomizedFragment";

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    public int getContentView() {
        return R.layout.fragment_customized;
    }

    @Override
    public void initEvent() {

    }
}
