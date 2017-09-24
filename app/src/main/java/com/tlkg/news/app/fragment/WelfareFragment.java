package com.tlkg.news.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.tlkg.news.app.R;
import com.tlkg.news.app.base.BaseFragment;

/**
 * Created by wuxiaoqi on 2017/9/24.
 * 福利Fragment
 */

public class WelfareFragment extends BaseFragment {

    private static final String TAG = "WelfareFragment";

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
    public void initEvent() {

    }
}
